package com.example.bluapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.zxing.ResultPoint
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.DecoratedBarcodeView

/**
 * SIMPLE QR Scanner Activity
 * Langsung scan QR tanpa kompleksitas berlebihan
 */
class SimpleQRScannerActivity : AppCompatActivity() {

    private lateinit var scannerView: DecoratedBarcodeView

    companion object {
        const val EXTRA_RESULT = "qr_result"
        private const val PERMISSION_CODE = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_qr_scanner)

        scannerView = findViewById(R.id.scannerView)

        // Check permission
        if (checkPermission()) {
            startScanning()
        } else {
            requestPermission()
        }
    }

    private fun checkPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this, Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this, arrayOf(Manifest.permission.CAMERA), PERMISSION_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_CODE) {
            if (grantResults.isNotEmpty() &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startScanning()
            } else {
                Toast.makeText(this, "Camera permission needed", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun startScanning() {
        scannerView.decodeContinuous(object : BarcodeCallback {
            override fun barcodeResult(result: BarcodeResult?) {
                result?.let {
                    handleResult(it.text)
                }
            }
            override fun possibleResultPoints(resultPoints: List<ResultPoint>?) {}
        })
    }

    private fun handleResult(qrContent: String) {
        // Return result
        intent.putExtra(EXTRA_RESULT, qrContent)
        setResult(RESULT_OK, intent)
        finish()
    }

    override fun onResume() {
        super.onResume()
        scannerView.resume()
    }

    override fun onPause() {
        super.onPause()
        scannerView.pause()
    }
}