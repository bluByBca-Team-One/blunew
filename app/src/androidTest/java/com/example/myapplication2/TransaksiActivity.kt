package com.example.myapplication2
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.widget.LinearLayout
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * TransaksiActivity - Halaman untuk mengelola transaksi
 * Menggunakan EXPLICIT INTENT untuk navigasi ke activity lain
 */
class TransaksiActivity : AppCompatActivity() {

    // Views
    private lateinit var navTransaksi: LinearLayout
    private lateinit var navKeuangan: LinearLayout
    private lateinit var navRiwayat: LinearLayout
    private lateinit var navProfil: LinearLayout
    private lateinit var fab: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.transaksiactivity)

        // Setup ActionBar
        supportActionBar?.title = "Transaksi"

        // Initialize views
        initializeViews()

        // Setup navigation listeners
        setupNavigationListeners()

        // Highlight current menu
        highlightCurrentMenu()
    }

    /**
     * Initialize all views
     */
    private fun initializeViews() {
        navTransaksi = findViewById(R.id.navTransaksi)
        navKeuangan = findViewById(R.id.navKeuangan)
        navRiwayat = findViewById(R.id.navRiwayat)
        navProfil = findViewById(R.id.navProfil)
        fab = findViewById(R.id.fab)
    }

    /**
     * Setup click listeners untuk semua navigation items
     */
    private fun setupNavigationListeners() {
        // Transaksi - Already in this activity
        navTransaksi.setOnClickListener {
            Toast.makeText(this, "Anda sudah di halaman Transaksi", Toast.LENGTH_SHORT).show()
        }


        // Riwayat - EXPLICIT INTENT
        navRiwayat.setOnClickListener {
            navigateToRiwayat()
        }

        // Profil - EXPLICIT INTENT
        navProfil.setOnClickListener {
            navigateToProfil()
        }

        // FAB - QR Scanner
        fab.setOnClickListener {
            onFabClicked()
        }
    }

    /**
     * Highlight menu yang sedang aktif (Transaksi)
     */
    private fun highlightCurrentMenu() {
        navTransaksi.alpha = 1.0f
        navKeuangan.alpha = 0.7f
        navRiwayat.alpha = 0.7f
        navProfil.alpha = 0.7f
    }

    /**
     * EXPLICIT INTENT: Navigate to KeuanganActivity
     */

    /**
     * EXPLICIT INTENT: Navigate to RiwayatActivity
     */
    private fun navigateToRiwayat() {
        val intent = Intent(this, RiwayatActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        }
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        finish()
    }

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
     * FAB click handler
     */
    private fun onFabClicked() {
        Toast.makeText(this, "Buka QR Scanner untuk transaksi cepat", Toast.LENGTH_SHORT).show()
        // Uncomment when QRScannerActivity is ready
        // val intent = Intent(this, QRScannerActivity::class.java)
        // startActivity(intent)
    }

    /**
     * Handle back button
     */
    override fun onBackPressed() {
        // Optional: Navigate to home or exit
        super.onBackPressed()
    }
}