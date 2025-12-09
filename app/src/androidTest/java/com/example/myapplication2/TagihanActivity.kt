package com.example.myapplication2

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView

class TagihanActivity : AppCompatActivity() {

    private lateinit var tvTotalTagihan: TextView
    private lateinit var tvTagihanBelumDibayar: TextView

    // Data tagihan
    private var totalTagihan: Int = 920000
    private var jumlahBelumDibayar: Int = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tagihanactivity)

        // Setup ActionBar
        supportActionBar?.apply {
            title = "Tagihan"
            setDisplayHomeAsUpEnabled(true)
        }

        // Back button handler
        findViewById<android.widget.ImageButton>(R.id.btnBack)?.setOnClickListener {
            onBackPressed()
        }

        initViews()
        loadData()
        setupClickListeners()
    }

    private fun initViews() {
        tvTotalTagihan = findViewById(R.id.tvTotalTagihan)
        tvTagihanBelumDibayar = findViewById(R.id.tvTagihanBelumDibayar)
    }

    private fun loadData() {
        updateTotalTagihan()
    }

    private fun updateTotalTagihan() {
        tvTotalTagihan.text = formatRupiah(totalTagihan)
        tvTagihanBelumDibayar.text = "$jumlahBelumDibayar Tagihan Belum Dibayar"
    }

    private fun setupClickListeners() {
        // Button Bayar PLN
        findViewById<MaterialButton>(R.id.btnBayarPLN)?.setOnClickListener {
            navigateToPembayaran(
                jenisBill = "PLN Pascabayar",
                billId = "543210987654",
                amount = 350000
            )
        }

        // Card PLN - Click untuk detail
        findViewById<MaterialCardView>(R.id.cardPLN)?.setOnClickListener {
            navigateToDetailTagihan(
                jenisBill = "PLN Pascabayar",
                billId = "543210987654",
                amount = 350000
            )
        }

        // Button Bayar BPJS
        findViewById<MaterialButton>(R.id.btnBayarBPJS)?.setOnClickListener {
            navigateToPembayaran(
                jenisBill = "BPJS Kesehatan",
                billId = "000123456789",
                amount = 150000
            )
        }

        // Card BPJS
        findViewById<MaterialCardView>(R.id.cardBPJS)?.setOnClickListener {
            navigateToDetailTagihan(
                jenisBill = "BPJS Kesehatan",
                billId = "000123456789",
                amount = 150000
            )
        }

        // Button Bayar Indihome
        findViewById<MaterialButton>(R.id.btnBayarIndihome)?.setOnClickListener {
            navigateToPembayaran(
                jenisBill = "Indihome",
                billId = "122333444555",
                amount = 420000
            )
        }

        // Card Indihome
        findViewById<MaterialCardView>(R.id.cardIndihome)?.setOnClickListener {
            navigateToDetailTagihan(
                jenisBill = "Indihome",
                billId = "122333444555",
                amount = 420000
            )
        }
    }

    // ==================== EXPLICIT INTENTS ====================

    /**
     * Navigate ke PembayaranActivity dengan data tagihan
     */
    private fun navigateToPembayaran(jenisBill: String, billId: String, amount: Int) {
        val intent = Intent(this, PembayaranActivity::class.java).apply {
            putExtra(EXTRA_JENIS_BILL, jenisBill)
            putExtra(EXTRA_BILL_ID, billId)
            putExtra(EXTRA_AMOUNT, amount)
            putExtra(EXTRA_ACTION_TYPE, "PAYMENT")
        }
        startActivityForResult(intent, REQUEST_PEMBAYARAN)
    }

    /**
     * Navigate ke DetailTagihanActivity
     */
    private fun navigateToDetailTagihan(jenisBill: String, billId: String, amount: Int) {
        val intent = Intent(this, DetailTagihanActivity::class.java).apply {
            putExtra(EXTRA_JENIS_BILL, jenisBill)
            putExtra(EXTRA_BILL_ID, billId)
            putExtra(EXTRA_AMOUNT, amount)
        }
        startActivity(intent)
    }

    /**
     * Navigate ke HistoryActivity
     */
    

    /**
     * Navigate ke MainActivity
     */
    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        }
        startActivity(intent)
    }

    /**
     * Navigate ke ProfilActivity
     */
    private fun navigateToProfil() {
        val intent = Intent(this, ProfilActivity::class.java)
        startActivity(intent)
    }

    // ==================== ACTIVITY RESULT ====================

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            REQUEST_PEMBAYARAN -> {
                if (resultCode == RESULT_OK) {
                    // Pembayaran berhasil
                    val paidAmount = data?.getIntExtra(EXTRA_AMOUNT, 0) ?: 0
                    val paymentMethod = data?.getStringExtra("payment_method") ?: "Unknown"

                    // Update total tagihan
                    totalTagihan -= paidAmount
                    jumlahBelumDibayar--
                    updateTotalTagihan()

                    showToast("Pembayaran berhasil via $paymentMethod")
                }
            }
        }
    }

    // ==================== HELPER FUNCTIONS ====================

    private fun formatRupiah(amount: Int): String {
        val formatter = java.text.NumberFormat.getNumberInstance(java.util.Locale("id", "ID"))
        return "Rp ${formatter.format(amount)}"
    }

    private fun showToast(message: String) {
        android.widget.Toast.makeText(this, message, android.widget.Toast.LENGTH_SHORT).show()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        // Request codes
        const val REQUEST_PEMBAYARAN = 100

        // Intent extras keys
        const val EXTRA_JENIS_BILL = "jenis_bill"
        const val EXTRA_BILL_ID = "bill_id"
        const val EXTRA_AMOUNT = "amount"
        const val EXTRA_ACTION_TYPE = "action_type"
    }
}