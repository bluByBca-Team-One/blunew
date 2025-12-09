package com.example.bluapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * BaseActivity - Parent class untuk semua activity dengan bottom navigation
 * Extend class ini untuk activity yang membutuhkan bottom navigation bar
 */
abstract class BaseActivity : AppCompatActivity() {

    // Bottom Navigation Views
    private lateinit var bottomNavContainer: CoordinatorLayout
    private lateinit var navTransaksi: LinearLayout
    private lateinit var navKeuangan: LinearLayout
    private lateinit var navRiwayat: LinearLayout
    private lateinit var navProfil: LinearLayout
    private lateinit var fab: FloatingActionButton

    // Track active menu for highlighting
    enum class ActiveMenu {
        TRANSAKSI, KEUANGAN, RIWAYAT, PROFIL, NONE
    }

    // Override this to set which menu is active
    protected open fun getActiveMenu(): ActiveMenu = ActiveMenu.NONE

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        setupBottomNavigation()
    }

    override fun setContentView(view: android.view.View?) {
        super.setContentView(view)
        setupBottomNavigation()
    }

    override fun setContentView(view: android.view.View?, params: ViewGroup.LayoutParams?) {
        super.setContentView(view, params)
        setupBottomNavigation()
    }

    /**
     * Setup bottom navigation bar
     * Automatically called after setContentView
     */
    private fun setupBottomNavigation() {
        // Find bottom navigation container in the layout
        bottomNavContainer = findViewById(R.id.bottomNavContainer) ?: return

        // Initialize views
        navTransaksi = findViewById(R.id.navTransaksi)
        navKeuangan = findViewById(R.id.navKeuangan)
        navRiwayat = findViewById(R.id.navRiwayat)
        navProfil = findViewById(R.id.navProfil)
        fab = findViewById(R.id.fab)

        // Setup click listeners
        setupNavigationListeners()

        // Highlight active menu
        highlightActiveMenu()
    }

    /**
     * Setup click listeners for all navigation items
     */
    private fun setupNavigationListeners() {
        navTransaksi.setOnClickListener {
            navigateToTransaksi()
        }

        navKeuangan.setOnClickListener {
            navigateToKeuangan()
        }

        navRiwayat.setOnClickListener {
            navigateToRiwayat()
        }

        navProfil.setOnClickListener {
            navigateToProfil()
        }

        fab.setOnClickListener {
            onFabClicked()
        }
    }

    /**
     * Highlight the active menu item
     */
    private fun highlightActiveMenu() {
        // Reset all to default state
        resetMenuStates()

        // Highlight active menu
        when (getActiveMenu()) {
            ActiveMenu.TRANSAKSI -> highlightMenu(navTransaksi)
            ActiveMenu.KEUANGAN -> highlightMenu(navKeuangan)
            ActiveMenu.RIWAYAT -> highlightMenu(navRiwayat)
            ActiveMenu.PROFIL -> highlightMenu(navProfil)
            ActiveMenu.NONE -> {} // No highlighting
        }
    }

    /**
     * Reset all menu items to default state
     */
    private fun resetMenuStates() {
        val defaultAlpha = 0.7f
        navTransaksi.alpha = defaultAlpha
        navKeuangan.alpha = defaultAlpha
        navRiwayat.alpha = defaultAlpha
        navProfil.alpha = defaultAlpha
    }

    /**
     * Highlight specific menu item
     */
    private fun highlightMenu(menu: LinearLayout) {
        menu.alpha = 1.0f
    }

    /**
     * EXPLICIT INTENT: Navigate to TransaksiActivity
     */
    protected fun navigateToTransaksi() {
        if (getActiveMenu() == ActiveMenu.TRANSAKSI) {
            showToast("Anda sudah di halaman Transaksi")
            return
        }

        val intent = Intent(this, TransaksiActivity::class.java).apply {
            // Clear back stack if needed
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        }
        startActivity(intent)

        // Smooth transition
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    /**
     * EXPLICIT INTENT: Navigate to KeuanganActivity
     */
    protected fun navigateToKeuangan() {
        if (getActiveMenu() == ActiveMenu.KEUANGAN) {
            showToast("Anda sudah di halaman Keuangan")
            return
        }

        val intent = Intent(this, KeuanganActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        }
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    /**
     * EXPLICIT INTENT: Navigate to RiwayatActivity
     */
    protected fun navigateToRiwayat() {
        if (getActiveMenu() == ActiveMenu.RIWAYAT) {
            showToast("Anda sudah di halaman Riwayat")
            return
        }

        val intent = Intent(this, RiwayatActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        }
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    /**
     * EXPLICIT INTENT: Navigate to ProfilActivity
     */
    protected fun navigateToProfil() {
        if (getActiveMenu() == ActiveMenu.PROFIL) {
            showToast("Anda sudah di halaman Profil")
            return
        }

        val intent = Intent(this, ProfilActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        }
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    /**
     * FAB click handler - Override this in child activity if needed
     */
    protected open fun onFabClicked() {
        // Default: Open QR Scanner or Main menu
        val intent = Intent(this, QRScannerActivity::class.java)
        startActivity(intent)
    }

    /**
     * Helper function to show toast
     */
    protected fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    /**
     * Prevent activity recreation when navigating to itself
     */
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
    }
}