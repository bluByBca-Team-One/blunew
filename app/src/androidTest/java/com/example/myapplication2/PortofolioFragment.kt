package com.example.bluapp.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.example.bluapp.BluinvestActivity
import com.example.bluapp.InsuranceActivity
import com.example.bluapp.R

class PortfolioFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_portfolio, container, false)

        // Card Investasi -> BluinvestActivity
        view.findViewById<CardView>(R.id.cardInvestasi).setOnClickListener {
            startActivity(Intent(requireContext(), BluinvestActivity::class.java))
        }

        // Card Insurance Portfolio
        view.findViewById<CardView>(R.id.cardInsurancePortfolio).setOnClickListener {
            startActivity(Intent(requireContext(), InsuranceActivity::class.java))
        }

        return view
    }
}
