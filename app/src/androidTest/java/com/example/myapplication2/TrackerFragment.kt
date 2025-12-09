package com.example.bluapp.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.example.bluapp.R
import com.example.bluapp.RiwayatActivity

class TrackerFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_tracker, container, false)

        // Button Lihat Riwayat
        view.findViewById<Button>(R.id.btnLihatRiwayat).setOnClickListener {
            startActivity(Intent(requireContext(), RiwayatActivity::class.java))
        }

        // CardView tracker items juga bisa ke detail
        view.findViewById<CardView>(R.id.cardTrackerItem1)?.setOnClickListener {
            val intent = Intent(requireContext(), RiwayatActivity::class.java)
            intent.putExtra("TRACKER_ID", 1)
            startActivity(intent)
        }

        return view
    }
}
