package com.example.paba_latihanfragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class kurang : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_kurang, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvHasil = view.findViewById<TextView>(R.id.tvHasil)
        val historyList = arguments?.getStringArrayList("history")

        if (historyList.isNullOrEmpty()) {
            tvHasil.text = "Belum ada perhitungan."
        } else {
            tvHasil.text = historyList.joinToString("\n")
        }

        val negatif = arguments?.getBoolean("negatif") ?: false
        view.setBackgroundColor(if (negatif) Color.RED else Color.WHITE)
    }
}