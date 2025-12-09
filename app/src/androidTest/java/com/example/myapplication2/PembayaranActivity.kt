package com.example.bluapp

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class PembayaranActivity : AppCompatActivity() {

    private lateinit var tvJenisBill: TextView
    private lateinit var tvBillId: TextView
    private lateinit var tvAmount: TextView
    private lateinit var btnKonfirmasi: MaterialButton

    private var jenisBill: String = ""
    private var billId: String = ""
    private var amount: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tagihanactivity)

        supportActionBar?.apply {
            title = "Pembayaran"
            setDisplayHomeAsUpEnabled(true)
        }

        // Ambil data dari intent
        jenisBill = intent.getStringExtra(TagihanActivity.EXTRA_JENIS_BILL) ?: ""
        billId = intent.getStringExtra(TagihanActivity.EXTRA_BILL_ID) ?: ""
        amount = intent.getIntExtra(TagihanActivity.EXTRA_AMOUNT, 0)

        displayData()
        setupClickListeners()
    }

    private fun displayData() {
        tvJenisBill.text = jenisBill
        tvBillId.text = "ID: $billId"
        tvAmount.text = formatRupiah(amount)
    }

    private fun setupClickListeners() {
        btnKonfirmasi.setOnClickListener {
            // Simulasi pembayaran berhasil
            val resultIntent = Intent().apply {
                putExtra(TagihanActivity.EXTRA_AMOUNT, amount)
                putExtra("payment_method", "BCA Virtual Account")
                putExtra("transaction_id", "TRX${System.currentTimeMillis()}")
            }
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }

    private fun formatRupiah(amount: Int): String {
        val formatter = java.text.NumberFormat.getNumberInstance(java.util.Locale("id", "ID"))
        return "Rp ${formatter.format(amount)}"
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}