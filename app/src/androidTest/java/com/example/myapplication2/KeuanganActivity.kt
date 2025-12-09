package com.example.bluapp

import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.text.NumberFormat
import java.util.*

class KeuanganActivity : AppCompatActivity() {

    private lateinit var pieChart: PieChart
    private lateinit var legendUangMasuk: TextView
    private lateinit var legendUangKeluar: TextView
    private lateinit var legendHutang: TextView

    // Data keuangan (dalam Rupiah)
    private var uangMasuk: Float = 7000000f
    private var uangKeluar: Float = 2500000f
    private var hutang: Float = 500000f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.keuanganactivity)

        supportActionBar?.apply {
            title = "Keuangan"
            setDisplayHomeAsUpEnabled(true)
        }

        initViews()
        setupPieChart()
        loadChartData()
        setupClickListeners()
    }

    private fun initViews() {
        pieChart = findViewById(R.id.pieChart)
        legendUangMasuk = findViewById(R.id.tvLegendUangMasuk)
        legendUangKeluar = findViewById(R.id.tvLegendUangKeluar)
        legendHutang = findViewById(R.id.tvLegendHutang)
    }

    private fun setupPieChart() {
        pieChart.apply {
            setUsePercentValues(true)
            description.isEnabled = false
            setExtraOffsets(5f, 10f, 5f, 5f)

            dragDecelerationFrictionCoef = 0.95f
            isDrawHoleEnabled = true
            setHoleColor(Color.WHITE)

            holeRadius = 65f
            transparentCircleRadius = 70f
            setDrawCenterText(true)
            centerText = "100%\nTotal"
            setCenterTextSize(20f)
            setCenterTextColor(ContextCompat.getColor(this@KeuanganActivity, R.color.teal_primary))

            animateY(1400)
            legend.isEnabled = false
            setEntryLabelColor(Color.WHITE)
            setEntryLabelTextSize(12f)
            setDrawEntryLabels(false)

            // Touch interaction
            setTouchEnabled(true)
            setOnChartValueSelectedListener(object : com.github.mikephil.charting.listener.OnChartValueSelectedListener {
                override fun onValueSelected(e: com.github.mikephil.charting.data.Entry?, h: com.github.mikephil.charting.highlight.Highlight?) {
                    e?.let {
                        val pieEntry = it as PieEntry
                        showToast("${pieEntry.label}: ${formatRupiah(pieEntry.value)}")
                    }
                }

                override fun onNothingSelected() {
                    // Do nothing
                }
            })
        }
    }

    private fun loadChartData() {
        val entries = ArrayList<PieEntry>()
        val total = uangMasuk + uangKeluar + hutang

        entries.add(PieEntry(uangMasuk, "Uang Masuk"))
        entries.add(PieEntry(uangKeluar, "Uang Keluar"))
        entries.add(PieEntry(hutang, "Hutang"))

        val colors = ArrayList<Int>()
        colors.add(ContextCompat.getColor(this, R.color.teal_primary))
        colors.add(Color.rgb(255, 138, 101))
        colors.add(Color.rgb(255, 213, 79))

        val dataSet = PieDataSet(entries, "Keuangan")
        dataSet.apply {
            this.colors = colors
            sliceSpace = 3f
            selectionShift = 5f
            valueTextColor = Color.WHITE
            valueTextSize = 14f
            valueFormatter = PercentFormatter(pieChart)
        }

        val data = PieData(dataSet)
        pieChart.data = data
        pieChart.invalidate()

        updateLegendText(total)
    }

    private fun updateLegendText(total: Float) {
        val percentUangMasuk = (uangMasuk / total * 100).toInt()
        val percentUangKeluar = (uangKeluar / total * 100).toInt()
        val percentHutang = (hutang / total * 100).toInt()

        legendUangMasuk.text = "Uang Masuk ($percentUangMasuk%) - ${formatRupiah(uangMasuk)}"
        legendUangKeluar.text = "Uang Keluar ($percentUangKeluar%) - ${formatRupiah(uangKeluar)}"
        legendHutang.text = "Hutang ($percentHutang%) - ${formatRupiah(hutang)}"
    }

    private fun setupClickListeners() {
        // Long click untuk input custom value
        findViewById<android.widget.LinearLayout>(R.id.legendUangMasuk)?.apply {
            setOnClickListener {
                uangMasuk += 1000000f
                loadChartData()
                showToast("Uang Masuk +Rp 1.000.000")
            }
            setOnLongClickListener {
                showInputDialog("Uang Masuk", uangMasuk) { newValue ->
                    uangMasuk = newValue
                    loadChartData()
                }
                true
            }
        }

        findViewById<android.widget.LinearLayout>(R.id.legendUangKeluar)?.apply {
            setOnClickListener {
                uangKeluar += 500000f
                loadChartData()
                showToast("Uang Keluar +Rp 500.000")
            }
            setOnLongClickListener {
                showInputDialog("Uang Keluar", uangKeluar) { newValue ->
                    uangKeluar = newValue
                    loadChartData()
                }
                true
            }
        }

        findViewById<android.widget.LinearLayout>(R.id.legendHutang)?.apply {
            setOnClickListener {
                if (hutang >= 100000f) {
                    hutang -= 100000f
                    loadChartData()
                    showToast("Hutang -Rp 100.000")
                }
            }
            setOnLongClickListener {
                showInputDialog("Hutang", hutang) { newValue ->
                    hutang = newValue
                    loadChartData()
                }
                true
            }
        }
    }

    private fun showInputDialog(title: String, currentValue: Float, onValueSet: (Float) -> Unit) {
        val dialogView = layoutInflater.inflate(android.R.layout.simple_list_item_1, null)
        val inputLayout = TextInputLayout(this).apply {
            hint = "Masukkan nominal (Rp)"
        }
        val inputEditText = TextInputEditText(this).apply {
            inputType = android.text.InputType.TYPE_CLASS_NUMBER
            setText(currentValue.toInt().toString())
        }
        inputLayout.addView(inputEditText)

        val padding = resources.getDimensionPixelSize(android.R.dimen.app_icon_size) / 2
        val container = android.widget.FrameLayout(this).apply {
            setPadding(padding, padding / 2, padding, 0)
            addView(inputLayout)
        }

        AlertDialog.Builder(this)
            .setTitle("Update $title")
            .setView(container)
            .setPositiveButton("Set") { _, _ ->
                val value = inputEditText.text.toString().toFloatOrNull() ?: currentValue
                onValueSet(value)
                showToast("$title diupdate: ${formatRupiah(value)}")
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    private fun formatRupiah(value: Float): String {
        val formatter = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
        return formatter.format(value.toDouble())
    }

    fun updateKeuanganData(masuk: Float, keluar: Float, debt: Float) {
        uangMasuk = masuk
        uangKeluar = keluar
        hutang = debt
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