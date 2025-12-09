package com.example.bluapp

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class MetodePembayaranActivity : AppCompatActivity() {

    // Views
    private lateinit var btnBack: ImageButton
    private lateinit var tilJumlah: TextInputLayout
    private lateinit var etJumlah: TextInputEditText
    private lateinit var tilRekeningTujuan: TextInputLayout
    private lateinit var actvRekeningTujuan: AutoCompleteTextView
    private lateinit var btnLanjut: MaterialButton

    // Bottom Navigation
    private lateinit var navTransaksi: LinearLayout
    private lateinit var navKeuangan: LinearLayout
    private lateinit var navRiwayat: LinearLayout
    private lateinit var navProfil: LinearLayout

    // Data
    private var selectedRekening: String = ""
    private var jumlahTransfer: Int = 0
    private val minimalTransfer = 10000

    // Rekening list
    private val rekeningList = listOf(
        "BCA - 1234567890 (Ahmad)",
        "Mandiri - 0987654321 (Budi)",
        "BNI - 1122334455 (Citra)",
        "BRI - 5544332211 (Dedi)"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activitymodepembayaran)

        initViews()
        setupDropdown()
        setupListeners()
        setupBottomNavigation()
    }

    private fun initViews() {
        btnBack = findViewById(R.id.btnBack)
        tilJumlah = findViewById(R.id.tilJumlah)
        etJumlah = findViewById(R.id.etJumlah)
        tilRekeningTujuan = findViewById(R.id.tilRekeningTujuan)
        actvRekeningTujuan = findViewById(R.id.actvRekeningTujuan)
        btnLanjut = findViewById(R.id.btnLanjut)

        navTransaksi = findViewById(R.id.navTransaksi)
        navKeuangan = findViewById(R.id.navKeuangan)
        navRiwayat = findViewById(R.id.navRiwayat)
        navProfil = findViewById(R.id.navProfil)
    }

    private fun setupDropdown() {
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_dropdown_item_1line,
            rekeningList
        )
        actvRekeningTujuan.setAdapter(adapter)

        actvRekeningTujuan.setOnItemClickListener { _, _, position, _ ->
            selectedRekening = rekeningList[position]
            validateForm()
        }
    }

    private fun setupListeners() {
        // Back button
        btnBack.setOnClickListener {
            onBackPressed()
        }

        // Jumlah input listener
        etJumlah.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val input = s.toString()
                if (input.isNotEmpty()) {
                    try {
                        jumlahTransfer = input.toInt()

                        // Validate minimal amount
                        if (jumlahTransfer < minimalTransfer) {
                            tilJumlah.error = "Minimal transfer Rp ${formatRupiah(minimalTransfer)}"
                        } else {
                            tilJumlah.error = null
                        }
                    } catch (e: NumberFormatException) {
                        tilJumlah.error = "Jumlah tidak valid"
                    }
                } else {
                    tilJumlah.error = null
                    jumlahTransfer = 0
                }

                validateForm()
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        // Button Lanjut
        btnLanjut.setOnClickListener {
            if (validateForm()) {
                processTransfer()
            }
        }
    }

    private fun setupBottomNavigation() {
        navTransaksi.setOnClickListener {
            // Already on Transaksi page
            showToast("Anda sudah di halaman Transaksi")
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
    }

    private fun validateForm(): Boolean {
        val isJumlahValid = jumlahTransfer >= minimalTransfer
        val isRekeningSelected = selectedRekening.isNotEmpty()

        val isFormValid = isJumlahValid && isRekeningSelected

        // Enable/disable button and change color
        btnLanjut.isEnabled = isFormValid
        if (isFormValid) {
            btnLanjut.backgroundTintList = ContextCompat.getColorStateList(this, R.color.teal_primary)
        } else {
            btnLanjut.backgroundTintList = ContextCompat.getColorStateList(this, android.R.color.darker_gray)
        }

        return isFormValid
    }

    private fun processTransfer() {
        // Navigate to konfirmasi page
        val intent = Intent(this, KonfirmasiTransferActivity::class.java).apply {
            putExtra(EXTRA_JUMLAH, jumlahTransfer)
            putExtra(EXTRA_REKENING_TUJUAN, selectedRekening)
        }
        startActivityForResult(intent, REQUEST_TRANSFER)
    }

    private fun formatRupiah(amount: Int): String {
        return String.format("%,d", amount).replace(',', '.')
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    // Navigation methods
    private fun navigateToKeuangan() {
        val intent = Intent(this, KeuanganActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToRiwayat() {
        val intent = Intent(this, RiwayatActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToProfil() {
        val intent = Intent(this, ProfilActivity::class.java)
        startActivity(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_TRANSFER && resultCode == RESULT_OK) {
            // Transfer success
            showToast("Transfer berhasil!")

            // Clear form
            etJumlah.text?.clear()
            actvRekeningTujuan.text?.clear()
            selectedRekening = ""
            jumlahTransfer = 0
            validateForm()

            // Optional: Navigate back or to success page
            finish()
        }
    }

    companion object {
        const val EXTRA_JUMLAH = "extra_jumlah"
        const val EXTRA_REKENING_TUJUAN = "extra_rekening_tujuan"
        const val REQUEST_TRANSFER = 200
    }
}