package com.example.bluapp.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.example.bluapp.*

class HomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Saving Button
        view.findViewById<CardView>(R.id.cardSaving).setOnClickListener {
            startActivity(Intent(requireContext(), SavingActivity::class.java))
        }

        // Bluinvest Button
        view.findViewById<CardView>(R.id.cardBluinvest).setOnClickListener {
            startActivity(Intent(requireContext(), BluinvestActivity::class.java))
        }

        // Insurance Button
        view.findViewById<CardView>(R.id.cardInsurance).setOnClickListener {
            startActivity(Intent(requireContext(), InsuranceActivity::class.java))
        }

        // Keuangan Button
        view.findViewById<CardView>(R.id.cardKeuangan).setOnClickListener {
            startActivity(Intent(requireContext(), KeuanganActivity::class.java))
        }

        // Transaksi Button
        view.findViewById<CardView>(R.id.cardTransaksi).setOnClickListener {
            startActivity(Intent(requireContext(), TransaksiActivity::class.java))
        }

        // Metode Pembayaran Button
        view.findViewById<CardView>(R.id.cardMetodePembayaran).setOnClickListener {
            startActivity(Intent(requireContext(), MetodePembayaranActivity::class.java))
        }

        return view
    }
}
