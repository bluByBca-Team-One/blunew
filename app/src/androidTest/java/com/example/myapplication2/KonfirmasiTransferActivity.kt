package com.example.myapplication2

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class KonfirmasiTransferActivity : AppCompatActivity() {

    private lateinit var btnBack: ImageButton
    private lateinit var tvJumlahTransfer: TextView
    private lateinit var tvRekeningTujuan: TextView
    private lateinit var tvBiayaAdmin: TextView
    private lateinit var tvTotalTransfer: TextView
    private lateinit var btnKonfirmasi: MaterialButton
    private lateinit var btnBatal: MaterialButton

    private var jumlahTransfer: Int = 0
    private var rekeningTujuan: String = ""
    private val biayaAdmin = 2500

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activitykonfirmasipembayaran)

        // Get data from intent
        jumlahTransfer = intent.getIntExtra(MetodePembayaranActivity.EXTRA_JUMLAH, 0)
        rekeningTujuan = intent.getStringExtra(MetodePembayaranActivity.EXTRA_REKENING_TUJUAN) ?: ""

        if (jumlahTransfer == 0 || rekeningTujuan.isEmpty()) {
            showToast("Data tidak valid")
            finish()
            return
        }

        initViews()
        displayData()
        setupListeners()
    }

    private fun initViews() {
        btnBack = findViewById(R.id.btnBack)
        tvJumlahTransfer = findViewById(R.id.tvJumlahTransfer)
        tvRekeningTujuan = findViewById(R.id.tvRekeningTujuan)
        tvBiayaAdmin = findViewById(R.id.tvBiayaAdmin)
        tvTotalTransfer = findViewById(R.id.tvTotalTransfer)
        btnKonfirmasi = findViewById(R.id.btnKonfirmasi)
        btnBatal = findViewById(R.id.btnBatal)
    }

    private fun displayData() {
        tvJumlahTransfer.text = "Rp ${formatRupiah(jumlahTransfer)}"
        tvRekeningTujuan.text = rekeningTujuan
        tvBiayaAdmin.text = "Rp ${formatRupiah(biayaAdmin)}"

        val total = jumlahTransfer + biayaAdmin
        tvTotalTransfer.text = "Rp ${formatRupiah(total)}"
    }

    private fun setupListeners() {
        btnBack.setOnClickListener {
            onBackPressed()
        }

        btnBatal.setOnClickListener {
            finish()
        }

        btnKonfirmasi.setOnClickListener {
            processTransfer()
        }
    }

    private fun processTransfer() {
        // Simulate transfer process
        showToast("Memproses transfer...")

        // In real app, call API here
        // For now, we just return success
        val resultIntent = Intent().apply {
            putExtra("status", "success")
            putExtra("jumlah", jumlahTransfer)
        }
        setResult(RESULT_OK, resultIntent)
        finish()
    }

    private fun formatRupiah(amount: Int): String {
        return String.format("%,d", amount).replace(',', '.')
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}