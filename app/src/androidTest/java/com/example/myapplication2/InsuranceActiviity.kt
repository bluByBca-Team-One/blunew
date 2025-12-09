package com.example.myapplication2

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class InsuranceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.insuranceactivity)

        // Setup ActionBar
        supportActionBar?.apply {
            title = "bluInsurance"
            setDisplayHomeAsUpEnabled(true)
        }

        // Back button handler
        findViewById<android.widget.ImageButton>(R.id.btnBack)?.setOnClickListener {
            onBackPressed()
        }

        setupButtonListeners()
    }

    private fun setupButtonListeners() {
        // Button Beli Asuransi - Open website (Implicit Intent)
        findViewById<MaterialButton>(R.id.insurance)?.setOnClickListener {
            openWebsite("https://www.prudential.co.id/id/pulse-id/products/insurance/")
        }

        // Detail bluLife Guard - Share via WhatsApp
        findViewById<TextView>(R.id.btnDetailJiwa)?.setOnClickListener {
            shareViaWhatsApp(
                "Informasi Polis bluLife Guard\n" +
                        "No. Polis: POL-2024-001\n" +
                        "Premi: Rp 150.000/bulan\n" +
                        "Tagihan Berikut: 20 Nov 2025"
            )
        }

        // Detail bluHealth - Send Email
        findViewById<TextView>(R.id.btnDetailKesehatan)?.setOnClickListener {
            sendEmail(
                to = "support@bluapp.com",
                subject = "Pertanyaan tentang bluHealth - POL-2023-889",
                body = "Saya ingin mengetahui detail lebih lanjut tentang polis bluHealth saya."
            )
        }

        // Detail Gadget Protection - Call customer service
        findViewById<TextView>(R.id.btnDetailLayar)?.setOnClickListener {
            makePhoneCall("081234567890")
        }
    }

    // ==================== IMPLICIT INTENTS ====================

    /**
     * Open website menggunakan browser
     */
    private fun openWebsite(url: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(url)
            }
            startActivity(intent)
        } catch (e: Exception) {
            showToast("Browser tidak tersedia")
        }
    }

    /**
     * Share text via WhatsApp
     */
    private fun shareViaWhatsApp(message: String) {
        try {
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                setPackage("com.whatsapp")
                putExtra(Intent.EXTRA_TEXT, message)
            }
            startActivity(intent)
        } catch (e: Exception) {
            // Fallback: Jika WhatsApp tidak terinstall, gunakan share generic
            shareText(message)
        }
    }

    /**
     * Share text generic (pilih aplikasi)
     */
    private fun shareText(message: String) {
        try {
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, message)
            }
            val chooser = Intent.createChooser(intent, "Bagikan via:")
            startActivity(chooser)
        } catch (e: Exception) {
            showToast("Tidak ada aplikasi untuk berbagi")
        }
    }

    /**
     * Send email
     */
    private fun sendEmail(to: String, subject: String, body: String) {
        try {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:$to")
                putExtra(Intent.EXTRA_SUBJECT, subject)
                putExtra(Intent.EXTRA_TEXT, body)
            }
            startActivity(intent)
        } catch (e: Exception) {
            showToast("Aplikasi email tidak tersedia")
        }
    }

    /**
     * Make phone call
     */
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

    /**
     * Open Google Maps with location
     */
    private fun openMaps(latitude: Double, longitude: Double, label: String) {
        try {
            val uri = "geo:$latitude,$longitude?q=$latitude,$longitude($label)"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
            intent.setPackage("com.google.android.apps.maps")
            startActivity(intent)
        } catch (e: Exception) {
            showToast("Google Maps tidak tersedia")
        }
    }

    /**
     * Take photo with camera
     */
    private fun openCamera() {
        try {
            val intent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, REQUEST_CAMERA)
        } catch (e: Exception) {
            showToast("Kamera tidak tersedia")
        }
    }

    /**
     * Pick image from gallery
     */
    private fun openGallery() {
        try {
            val intent = Intent(Intent.ACTION_PICK).apply {
                type = "image/*"
            }
            startActivityForResult(intent, REQUEST_GALLERY)
        } catch (e: Exception) {
            showToast("Galeri tidak tersedia")
        }
    }

    /**
     * Send SMS
     */
    private fun sendSMS(phoneNumber: String, message: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("sms:$phoneNumber")
                putExtra("sms_body", message)
            }
            startActivity(intent)
        } catch (e: Exception) {
            showToast("Aplikasi SMS tidak tersedia")
        }
    }

    /**
     * Open PDF document
     */
    private fun openPDF(pdfUri: Uri) {
        try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(pdfUri, "application/pdf")
                flags = Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_GRANT_READ_URI_PERMISSION
            }
            startActivity(intent)
        } catch (e: Exception) {
            showToast("Tidak ada aplikasi untuk membuka PDF")
        }
    }

    // ==================== HELPER FUNCTIONS ====================

    private fun showToast(message: String) {
        android.widget.Toast.makeText(this, message, android.widget.Toast.LENGTH_SHORT).show()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            REQUEST_CAMERA -> {
                if (resultCode == RESULT_OK) {
                    // Handle captured photo
                    val photo = data?.extras?.get("data")
                    showToast("Foto berhasil diambil")
                }
            }

            REQUEST_GALLERY -> {
                if (resultCode == RESULT_OK) {
                    // Handle selected image
                    val imageUri = data?.data
                    showToast("Gambar dipilih: $imageUri")
                }
            }
        }
    }

    companion object {
        private const val REQUEST_CAMERA = 100
        private const val REQUEST_GALLERY = 101
    }
}