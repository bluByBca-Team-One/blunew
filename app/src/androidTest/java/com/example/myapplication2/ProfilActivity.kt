package com.example.myapplication2
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * ProfilActivity - Menampilkan informasi profil pengguna
 * dengan informasi personal dan pengaturan
 */
class ProfilActivity : AppCompatActivity() {

    // Views - Profile Info
    private lateinit var btnBack: ImageButton
    private lateinit var tvInitials: TextView
    private lateinit var tvUserName: TextView
    private lateinit var tvCustomerId: TextView

    // Views - Personal Information
    private lateinit var itemTanggalLahir: LinearLayout
    private lateinit var tvTanggalLahir: TextView
    private lateinit var itemGender: LinearLayout
    private lateinit var tvGender: TextView
    private lateinit var itemNomorTelepon: LinearLayout
    private lateinit var tvNomorTelepon: TextView
    private lateinit var itemAlamat: LinearLayout
    private lateinit var tvAlamat: TextView
    private lateinit var itemTahunBergabung: LinearLayout
    private lateinit var tvTahunBergabung: TextView

    // Views - Settings
    private lateinit var btnKeamananPin: LinearLayout
    private lateinit var btnPengaturanNotifikasi: LinearLayout
    private lateinit var btnBantuanFaq: LinearLayout
    private lateinit var btnKeluarAkun: LinearLayout

    // Views - Navigation
    private lateinit var navTransaksi: LinearLayout
    private lateinit var navKeuangan: LinearLayout
    private lateinit var navRiwayat: LinearLayout
    private lateinit var navProfil: LinearLayout
    private lateinit var fab: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profilactivity)

        // Hide ActionBar
        supportActionBar?.hide()

        // Initialize views
        initializeViews()

        // Setup listeners
        setupListeners()

        // Load user data
        loadUserData()

        // Highlight current menu
        highlightCurrentMenu()
    }

    /**
     * Initialize all views
     */
    private fun initializeViews() {
        // Profile Info
        btnBack = findViewById(R.id.btnBack)
        tvInitials = findViewById(R.id.tvInitials)
        tvUserName = findViewById(R.id.tvUserName)
        tvCustomerId = findViewById(R.id.tvCustomerId)

        // Personal Information
        itemTanggalLahir = findViewById(R.id.itemTanggalLahir)
        tvTanggalLahir = findViewById(R.id.tvTanggalLahir)
        itemGender = findViewById(R.id.itemGender)
        tvGender = findViewById(R.id.tvGender)
        itemNomorTelepon = findViewById(R.id.itemNomorTelepon)
        tvNomorTelepon = findViewById(R.id.tvNomorTelepon)
        itemAlamat = findViewById(R.id.itemAlamat)
        tvAlamat = findViewById(R.id.tvAlamat)
        itemTahunBergabung = findViewById(R.id.itemTahunBergabung)
        tvTahunBergabung = findViewById(R.id.tvTahunBergabung)

        // Settings
        btnKeamananPin = findViewById(R.id.btnKeamananPin)
        btnPengaturanNotifikasi = findViewById(R.id.btnPengaturanNotifikasi)
        btnBantuanFaq = findViewById(R.id.btnBantuanFaq)
        btnKeluarAkun = findViewById(R.id.btnKeluarAkun)

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

        // Personal Information Items
        itemTanggalLahir.setOnClickListener {
            showEditDialog("Tanggal Lahir", tvTanggalLahir.text.toString())
        }

        itemGender.setOnClickListener {
            showGenderDialog()
        }

        itemNomorTelepon.setOnClickListener {
            showEditDialog("Nomor Telepon", tvNomorTelepon.text.toString())
        }

        itemAlamat.setOnClickListener {
            showEditDialog("Alamat", tvAlamat.text.toString())
        }

        itemTahunBergabung.setOnClickListener {
            Toast.makeText(this, "Tahun bergabung tidak dapat diubah", Toast.LENGTH_SHORT).show()
        }

        // Settings Items
        btnKeamananPin.setOnClickListener {
            Toast.makeText(this, "Keamanan & PIN", Toast.LENGTH_SHORT).show()
            // TODO: Navigate to Security Settings
        }

        btnPengaturanNotifikasi.setOnClickListener {
            Toast.makeText(this, "Pengaturan Notifikasi", Toast.LENGTH_SHORT).show()
            // TODO: Navigate to Notification Settings
        }

        btnBantuanFaq.setOnClickListener {
            Toast.makeText(this, "Bantuan & FAQ", Toast.LENGTH_SHORT).show()
            // TODO: Navigate to Help & FAQ
        }

        btnKeluarAkun.setOnClickListener {
            showLogoutDialog()
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




        navProfil.setOnClickListener {
            Toast.makeText(this, "Anda sudah di halaman Profil", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Load user data from database/API
     */
    private fun loadUserData() {
        // TODO: Load from database or API
        // For now, using dummy data
        tvInitials.text = "GH"
        tvUserName.text = "Gyvana Hafizhah Syan"
        tvCustomerId.text = "ID Pelanggan: 9876543210"
        tvTanggalLahir.text = "15 Januari 1998"
        tvGender.text = "Perempuan"
        tvNomorTelepon.text = "+62 812-3456-7890"
        tvAlamat.text = "Jl. Mt. Haryono No.16, Caruban"
        tvTahunBergabung.text = "2023"
    }

    /**
     * Highlight current menu (Profil)
     */
    private fun highlightCurrentMenu() {
        navTransaksi.alpha = 0.7f
        navKeuangan.alpha = 0.7f
        navRiwayat.alpha = 0.7f
        navProfil.alpha = 1.0f
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
     * EXPLICIT INTENT: Navigate to RiwayatActivity
     */


    /**
     * Show edit dialog for editable fields
     */
    private fun showEditDialog(title: String, currentValue: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Edit $title")
        builder.setMessage("Fungsi edit akan segera tersedia")
        builder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }
        builder.show()
    }

    /**
     * Show gender selection dialog
     */
    private fun showGenderDialog() {
        val genders = arrayOf("Laki-laki", "Perempuan")
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Pilih Jenis Kelamin")
        builder.setItems(genders) { dialog, which ->
            tvGender.text = genders[which]
            Toast.makeText(this, "Jenis kelamin diubah ke ${genders[which]}", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        builder.setNegativeButton("Batal") { dialog, _ ->
            dialog.dismiss()
        }
        builder.show()
    }

    /**
     * Show logout confirmation dialog
     */
    private fun showLogoutDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Keluar Akun")
        builder.setMessage("Apakah Anda yakin ingin keluar dari akun?")
        builder.setPositiveButton("Ya") { dialog, _ ->
            // TODO: Clear session and navigate to login
            Toast.makeText(this, "Berhasil keluar", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
            finish()
        }
        builder.setNegativeButton("Tidak") { dialog, _ ->
            dialog.dismiss()
        }
        builder.show()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
