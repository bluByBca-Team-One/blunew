package com.example.bluapp

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class RiwayatActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_riwayat)

        findViewById<ImageView>(R.id.btnBack).setOnClickListener {
            finish()
        }

        // Ambil data tracker jika ada
        val trackerId = intent.getIntExtra("TRACKER_ID", -1)
        // Load riwayat berdasarkan trackerId
    }
}
