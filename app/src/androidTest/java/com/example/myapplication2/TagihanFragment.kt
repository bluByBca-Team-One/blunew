package com.example.bluapp.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.bluapp.MetodePembayaranActivity
import com.example.bluapp.R

class TagihanFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_tagihan, container, false)

        // Button Bayar Tagihan
        view.findViewById<Button>(R.id.btnBayarTagihan).setOnClickListener {
            startActivity(Intent(requireContext(), MetodePembayaranActivity::class.java))
        }

        return view
    }
}
