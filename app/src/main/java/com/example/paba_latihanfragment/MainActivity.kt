package com.example.paba_latihanfragment

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
class MainActivity : AppCompatActivity() {

    private lateinit var etAngka1: EditText
    private lateinit var etAngka2: EditText

    private val historyTambah = ArrayList<String>()
    private val historyKurang = ArrayList<String>()
    private val historyKali = ArrayList<String>()
    private val historyBagi = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        etAngka1 = findViewById(R.id.etAngka1)
        etAngka2 = findViewById(R.id.etAngka2)

        val btnKali = findViewById<Button>(R.id.btnKali)
        val btnBagi = findViewById<Button>(R.id.btnBagi)
        val btnTambah = findViewById<Button>(R.id.btnTambah)
        val btnKurang = findViewById<Button>(R.id.btnKurang)

        btnKali.setOnClickListener { hitung("*") }
        btnBagi.setOnClickListener { hitung("/") }
        btnTambah.setOnClickListener { hitung("+") }
        btnKurang.setOnClickListener { hitung("-") }

        if (savedInstanceState == null) {
            val fragment = tambah().apply {
                arguments = Bundle().apply {
                    putStringArrayList("history", historyTambah)
                    putBoolean("negatif", false)
                }
            }
            replaceFragment(fragment)
        }
    }

    private fun hitung(simbol: String) {
        val angka1 = etAngka1.text.toString().trim().replace(',', '.').toDoubleOrNull()
        val angka2 = etAngka2.text.toString().trim().replace(',', '.').toDoubleOrNull()

        if (angka1 == null) {
            etAngka1.error = "Masukkan angka pertama"
            return
        }

        if (angka2 == null) {
            etAngka2.error = "Masukkan angka kedua"
            return
        }

        if (simbol == "/" && angka2 == 0.0) {
            etAngka2.error = "Pembagi tidak boleh nol"
            return
        }

        val hasil = when (simbol) {
            "*" -> angka1 * angka2
            "/" -> angka1 / angka2
            "+" -> angka1 + angka2
            "-" -> angka1 - angka2
            else -> return
        }

        val teksHasil = "${formatAngka(angka1)} $simbol " +
                "${formatAngka(angka2)} = ${formatAngka(hasil)}"

        val historyList = when (simbol) {
            "*" -> { historyKali.add(teksHasil); historyKali }
            "/" -> { historyBagi.add(teksHasil); historyBagi }
            "+" -> { historyTambah.add(teksHasil); historyTambah }
            "-" -> { historyKurang.add(teksHasil); historyKurang }
            else -> return
        }

        val fragment = when (simbol) {
            "*" -> kali()
            "/" -> bagi()
            "+" -> tambah()
            "-" -> kurang()
            else -> return
        }

        val dataKirim = Bundle().apply {
            putStringArrayList("history", historyList)
            putBoolean("negatif", hasil < 0)
        }

        fragment.arguments = dataKirim
        replaceFragment(fragment)
    }

    private fun formatAngka(angka: Double): String {
        return angka.toString().removeSuffix(".0")
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.commit()
    }
}