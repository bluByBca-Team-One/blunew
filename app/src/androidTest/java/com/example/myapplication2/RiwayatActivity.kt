package com.example.myapplication2

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * RiwayatActivity - Menampilkan riwayat transaksi pengguna
 * dengan filter dan detail transaksi
 */
class RiwayatActivity : AppCompatActivity() {

    // Views - Header
    private lateinit var btnBack: ImageButton
    private lateinit var btnRefresh: ImageButton

    // Views - Transaction Items
    private lateinit var transaction1: LinearLayout
    private lateinit var transaction2: LinearLayout
    private lateinit var transaction3: LinearLayout
    private lateinit var transaction4: LinearLayout

    // Views - Navigation
    private lateinit var navTransaksi: LinearLayout
    private lateinit var navKeuangan: LinearLayout
    private lateinit var navRiwayat: LinearLayout
    private lateinit var navProfil: LinearLayout
    private lateinit var fab: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.riwayatactivity)

        // Hide ActionBar
        supportActionBar?.hide()

        // Initialize views
        initializeViews()

        // Setup listeners
        setupListeners()

        // Highlight current menu
        highlightCurrentMenu()
    }

    /**
     * Initialize all views
     */
    private fun initializeViews() {
        // Header
        btnBack = findViewById(R.id.btnBack)
        btnRefresh = findViewById(R.id.btnRefresh)

        // Transaction items
        transaction1 = findViewById(R.id.transaction1)
        transaction2 = findViewById(R.id.transaction2)
        transaction3 = findViewById(R.id.transaction3)
        transaction4 = findViewById(R.id.transaction4)

        // Navigation
        navTransaksi = findViewById(R.id.navTransaksi)
        navKeuangan = findViewById(R.id.navKeuangan)
        navRiwayat = findViewById(R.id.navRiwayat)
        navProfil = findViewById(R.id.navProfil)
        fab = findViewById(R.id.fab)
    }

    /**
     * Setup all click listeners
     */
    private fun setupListeners() {
        // Back button
        btnBack.setOnClickListener {
            finish()
        }

        // Refresh button
        btnRefresh.setOnClickListener {
            refreshTransactions()
        }

        // Transaction items
        transaction1.setOnClickListener {
            showTransactionDetail("Top Up Gopay", "Rp 50.000", "Hari Ini", "debit")
        }

        transaction2.setOnClickListener {
            showTransactionDetail("Gaji Bulanan", "Rp 8.000.000", "Hari Ini", "kredit")
        }

        transaction3.setOnClickListener {
            showTransactionDetail("Bayar Tagihan Listrik", "Rp 250.000", "19 Nov 2025", "debit")
        }

        transaction4.setOnClickListener {
            showTransactionDetail("Transfer ke Ayah", "Rp 1.500.000", "15 Nov 2025", "debit")
        }

        // Bottom Navigation
        setupNavigationListeners()

        // FAB
        fab.setOnClickListener {
            Toast.makeText(this, "QR Scanner", Toast.LENGTH_SHORT).show()
            // TODO: Open QR Scanner
        }
    }

    /**
     * Setup bottom navigation listeners
     */
    private fun setupNavigationListeners() {
        navTransaksi.setOnClickListener {
            navigateToTransaksi()
        }


        navRiwayat.setOnClickListener {
            Toast.makeText(this, "Anda sudah di halaman Riwayat", Toast.LENGTH_SHORT).show()
        }

        navProfil.setOnClickListener {
            navigateToProfil()
        }
    }

    /**
     * Highlight current menu (Riwayat)
     */
    private fun highlightCurrentMenu() {
        navTransaksi.alpha = 0.7f
        navKeuangan.alpha = 0.7f
        navRiwayat.alpha = 1.0f
        navProfil.alpha = 0.7f
    }

    /**
     * EXPLICIT INTENT: Navigate to TransaksiActivity
     */
    private fun navigateToTransaksi() {
        val intent = Intent(this, TransaksiActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        }
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        finish()
    }

    /**
     * EXPLICIT INTENT: Navigate to KeuanganActivity
     */


    /**
     * EXPLICIT INTENT: Navigate to ProfilActivity
     */
    private fun navigateToProfil() {
        val intent = Intent(this, ProfilActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        }
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        finish()
    }

    /**
     * Refresh transactions list
     */
    private fun refreshTransactions() {
        Toast.makeText(this, "Memperbarui riwayat transaksi...", Toast.LENGTH_SHORT).show()
        // TODO: Implement refresh logic
        // - Fetch from API
        // - Update UI with new data
    }

    /**
     * Show transaction detail
     */
    private fun showTransactionDetail(title: String, amount: String, date: String, type: String) {
        val typeText = if (type == "kredit") "Kredit" else "Debit"
        val message = """
            Transaksi: $title
            Jumlah: $amount
            Tanggal: $date
            Tipe: $typeText
        """.trimIndent()

        Toast.makeText(this, message, Toast.LENGTH_LONG).show()

        // TODO: Open detail activity
        // val intent = Intent(this, TransactionDetailActivity::class.java).apply {
        //     putExtra("TRANSACTION_TITLE", title)
        //     putExtra("TRANSACTION_AMOUNT", amount)
        //     putExtra("TRANSACTION_DATE", date)
        //     putExtra("TRANSACTION_TYPE", type)
        // }
        // startActivity(intent)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}