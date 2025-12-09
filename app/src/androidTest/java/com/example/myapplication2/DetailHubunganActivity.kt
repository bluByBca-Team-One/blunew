package com.example.bluapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class DetailHubunganActivity : AppCompatActivity() {

    private lateinit var hubungan: Hubungan

    private lateinit var tvNama: TextView
    private lateinit var tvRelasi: TextView
    private lateinit var tvPhone: TextView
    private lateinit var tvEmail: TextView
    private lateinit var tvAlamat: TextView
    private lateinit var btnCall: MaterialButton
    private lateinit var btnMessage: MaterialButton
    private lateinit var btnEmail: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.hubunganactivity)

        // Get data from intent
        hubungan = intent.getParcelableExtra(HubunganActivity.EXTRA_HUBUNGAN)
            ?: run {
                showToast("Data tidak ditemukan")
                finish()
                return
            }

        supportActionBar?.apply {
            title = "Detail Hubungan"
            setDisplayHomeAsUpEnabled(true)
        }

        initViews()
        displayData()
        setupClickListeners()
    }

    private fun initViews() {
        tvNama = findViewById(R.id.tvNama)
        tvRelasi = findViewById(R.id.tvRelasi)
        tvPhone = findViewById(R.id.tvPhone)

        btnCall = findViewById(R.id.btnCall)
        btnMessage = findViewById(R.id.btnMessage)

    }

    private fun displayData() {
        tvNama.text = hubungan.nama
        tvRelasi.text = hubungan.relasi
        tvPhone.text = hubungan.getFormattedPhone()
        tvEmail.text = hubungan.email.ifEmpty { "-" }
        tvAlamat.text = hubungan.alamat.ifEmpty { "-" }
    }

    private fun setupClickListeners() {
        // Call button
        btnCall.setOnClickListener {
            makePhoneCall(hubungan.phone)
        }

        // Message button
        btnMessage.setOnClickListener {
            sendWhatsAppMessage(hubungan.phone)
        }

        // Email button
        btnEmail.setOnClickListener {
            if (hubungan.email.isNotEmpty()) {
                sendEmail(hubungan.email)
            } else {
                showToast("Email tidak tersedia")
            }
        }
    }

    private fun makePhoneCall(phoneNumber: String) {
        try {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:$phoneNumber")
            }
            startActivity(intent)
        } catch (e: Exception) {
            showToast("Tidak dapat membuka aplikasi telepon")
        }
    }

    private fun sendWhatsAppMessage(phoneNumber: String) {
        try {
            val waNumber = if (phoneNumber.startsWith("0")) {
                "62${phoneNumber.substring(1)}"
            } else {
                phoneNumber
            }

            val url = "https://wa.me/$waNumber"
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(url)
            }
            startActivity(intent)
        } catch (e: Exception) {
            showToast("Tidak dapat membuka WhatsApp")
        }
    }

    private fun sendEmail(email: String) {
        try {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:$email")
                putExtra(Intent.EXTRA_SUBJECT, "Halo dari bluApp")
            }
            startActivity(intent)
        } catch (e: Exception) {
            showToast("Tidak dapat membuka aplikasi email")
        }
    }

    private fun showToast(message: String) {
        android.widget.Toast.makeText(this, message, android.widget.Toast.LENGTH_SHORT).show()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
