package com.example.bluapp

import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import java.text.NumberFormat
import java.util.*

class BluinvestActivity : AppCompatActivity() {

    private lateinit var pieChart: PieChart
    private lateinit var tvPercentSaham: TextView
    private lateinit var tvPercentEmas: TextView
    private lateinit var tvPercentCrypto: TextView
    private lateinit var tvPercentFiat: TextView

    // Data portofolio dalam juta Rupiah
    private var saham: Float = 45f       // 45jt = 45%
    private var emas: Float = 25f        // 25jt = 25%
    private var crypto: Float = 20f      // 20jt = 20%
    private var fiat: Float = 10f        // 10jt = 10%

    private val totalAset: Float
        get() = saham + emas + crypto + fiat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bluinvestactivity)

        // Setup ActionBar
        supportActionBar?.apply {
            title = "bluInvest"
            setDisplayHomeAsUpEnabled(true)
        }

        // Back button handler
        findViewById<android.widget.ImageButton>(R.id.btnBack)?.setOnClickListener {
            onBackPressed()
        }

        initViews()
        setupPieChart()
        loadChartData()
        setupClickListeners()
    }

    private fun initViews() {
        pieChart = findViewById(R.id.pieChartPortofolio)
        tvPercentSaham = findViewById(R.id.tvPercentSaham)
        tvPercentEmas = findViewById(R.id.tvPercentEmas)
        tvPercentCrypto = findViewById(R.id.tvPercentCrypto)
        tvPercentFiat = findViewById(R.id.tvPercentFiat)
    }

    private fun setupPieChart() {
        pieChart.apply {
            setUsePercentValues(true)
            description.isEnabled = false
            setExtraOffsets(5f, 10f, 5f, 5f)

            // Drag dan rotation
            dragDecelerationFrictionCoef = 0.95f
            isDrawHoleEnabled = true
            setHoleColor(Color.WHITE)

            // Hole (bagian tengah donut)
            holeRadius = 65f
            transparentCircleRadius = 70f
            setDrawCenterText(true)

            // Update center text dengan format
            updateCenterText()
            setCenterTextSize(16f)
            setCenterTextColor(Color.DKGRAY)

            // Animasi
            animateY(1400)

            // Legend disabled (pakai custom legend)
            legend.isEnabled = false

            // Entry label
            setEntryLabelColor(Color.WHITE)
            setEntryLabelTextSize(12f)
            setDrawEntryLabels(false)

            // Touch interaction
            setTouchEnabled(true)
        }
    }

    private fun updateCenterText() {
        val formatter = NumberFormat.getNumberInstance(Locale("id", "ID"))
        pieChart.centerText = "Total Aset\nRp ${formatter.format(totalAset.toInt())}jt"
    }

    private fun loadChartData() {
        val entries = ArrayList<PieEntry>()

        // Tambahkan data ke chart
        entries.add(PieEntry(saham, "Saham"))
        entries.add(PieEntry(emas, "Emas"))
        entries.add(PieEntry(crypto, "Crypto"))
        entries.add(PieEntry(fiat, "Fiat"))

        // Warna untuk setiap segment (sesuai gambar)
        val colors = ArrayList<Int>()
        colors.add(ContextCompat.getColor(this, R.color.teal_primary))  // Saham - Teal
        colors.add(Color.rgb(255, 213, 79))    // Emas - Yellow
        colors.add(Color.rgb(255, 138, 101))   // Crypto - Orange
        colors.add(Color.rgb(176, 190, 197))   // Fiat - Gray

        // Dataset
        val dataSet = PieDataSet(entries, "Portofolio")
        dataSet.apply {
            this.colors = colors
            sliceSpace = 3f
            selectionShift = 5f
            valueTextColor = Color.WHITE
            valueTextSize = 14f
            valueFormatter = PercentFormatter(pieChart)
        }

        // Set data ke chart
        val data = PieData(dataSet)
        pieChart.data = data
        pieChart.invalidate() // Refresh chart

        // Update center text
        updateCenterText()

        // Update legend text dengan persentase
        updateLegendPercentages()
    }

    private fun updateLegendPercentages() {
        val total = totalAset

        val percentSaham = (saham / total * 100).toInt()
        val percentEmas = (emas / total * 100).toInt()
        val percentCrypto = (crypto / total * 100).toInt()
        val percentFiat = (fiat / total * 100).toInt()

        tvPercentSaham.text = "$percentSaham%"
        tvPercentEmas.text = "$percentEmas%"
        tvPercentCrypto.text = "$percentCrypto%"
        tvPercentFiat.text = "$percentFiat%"
    }

    private fun setupClickListeners() {
        // Click listeners untuk simulasi perubahan portofolio
        findViewById<android.widget.LinearLayout>(R.id.legendSaham)?.setOnClickListener {
            saham += 5f
            loadChartData()
            showToast("Saham +Rp 5jt")
        }

        findViewById<android.widget.LinearLayout>(R.id.legendEmas)?.setOnClickListener {
            emas += 5f
            loadChartData()
            showToast("Emas +Rp 5jt")
        }

        findViewById<android.widget.LinearLayout>(R.id.legendCrypto)?.setOnClickListener {
            crypto += 5f
            loadChartData()
            showToast("Crypto +Rp 5jt")
        }

        findViewById<android.widget.LinearLayout>(R.id.legendFiat)?.setOnClickListener {
            fiat += 5f
            loadChartData()
            showToast("Fiat +Rp 5jt")
        }
    }

    // Fungsi publik untuk update data portofolio dari luar
    fun updatePortofolio(sahamValue: Float, emasValue: Float, cryptoValue: Float, fiatValue: Float) {
        saham = sahamValue
        emas = emasValue
        crypto = cryptoValue
        fiat = fiatValue
        loadChartData()
    }

    private fun showToast(message: String) {
        android.widget.Toast.makeText(this, message, android.widget.Toast.LENGTH_SHORT).show()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}