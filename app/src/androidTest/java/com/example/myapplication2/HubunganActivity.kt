
package com.example.bluapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton

class HubunganActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyStateContainer: LinearLayout
    private lateinit var btnKembali: MaterialButton
    private lateinit var adapter: HubunganAdapter

    // Sample data
    private val hubunganList = mutableListOf<Hubungan>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.hubunganactivity)

        // Setup ActionBar
        supportActionBar?.apply {
            title = "Hubungan"
            setDisplayHomeAsUpEnabled(true)
        }

        // Back button handler
        findViewById<android.widget.ImageButton>(R.id.btnBack)?.setOnClickListener {
            onBackPressed()
        }

        initViews()
        setupRecyclerView()
        loadData()
    }

    private fun initViews() {
        recyclerView = findViewById(R.id.recyclerViewHubungan)
        emptyStateContainer = findViewById(R.id.emptyStateContainer)
        btnKembali = findViewById(R.id.btnKembali)

        // Button Kembali click listener
        btnKembali.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setupRecyclerView() {
        adapter = HubunganAdapter()

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@HubunganActivity)
            adapter = this@HubunganActivity.adapter
            setHasFixedSize(true)
        }

        // Set item click listener
        adapter.setOnItemClickListener { hubungan ->
            navigateToDetailHubungan(hubungan)
        }
    }

    private fun loadData() {
        // Load data dari database/API
        // Untuk demo, kita gunakan sample data
        hubunganList.clear()

        // Uncomment untuk menampilkan sample data
        /*
        hubunganList.addAll(getSampleData())
        */

        // Update UI based on data
        updateUI()
    }

    private fun updateUI() {
        if (hubunganList.isEmpty()) {
            // Show empty state
            recyclerView.visibility = View.GONE
            emptyStateContainer.visibility = View.VISIBLE
        } else {
            // Show RecyclerView
            recyclerView.visibility = View.VISIBLE
            emptyStateContainer.visibility = View.GONE

            // Submit data ke adapter
            adapter.submitList(hubunganList.toList())
        }
    }

    /**
     * Sample data untuk testing
     */
    private fun getSampleData(): List<Hubungan> {
        return listOf(
            Hubungan(
                id = "1",
                nama = "Ahmad Zaki",
                relasi = "Keluarga",
                phone = "081234567890",
                email = "ahmad@example.com",
                alamat = "Jakarta"
            ),
            Hubungan(
                id = "2",
                nama = "Budi Santoso",
                relasi = "Teman",
                phone = "082345678901",
                email = "budi@example.com",
                alamat = "Bandung"
            ),
            Hubungan(
                id = "3",
                nama = "Citra Dewi",
                relasi = "Rekan Kerja",
                phone = "083456789012",
                email = "citra@example.com",
                alamat = "Surabaya"
            ),
            Hubungan(
                id = "4",
                nama = "Dedi Pratama",
                relasi = "Keluarga",
                phone = "084567890123",
                email = "dedi@example.com",
                alamat = "Medan"
            ),
            Hubungan(
                id = "5",
                nama = "Eka Putri",
                relasi = "Teman",
                phone = "085678901234",
                email = "eka@example.com",
                alamat = "Semarang"
            )
        )
    }

    /**
     * Navigate to DetailHubunganActivity (Explicit Intent)
     */
    private fun navigateToDetailHubungan(hubungan: Hubungan) {
        val intent = Intent(this, DetailHubunganActivity::class.java).apply {
            putExtra(EXTRA_HUBUNGAN, hubungan)
        }
        startActivity(intent)
    }

    /**
     * Public function to add new hubungan
     */
    fun addHubungan(hubungan: Hubungan) {
        hubunganList.add(hubungan)
        updateUI()
    }

    /**
     * Public function to remove hubungan
     */
    fun removeHubungan(hubunganId: String) {
        hubunganList.removeAll { it.id == hubunganId }
        updateUI()
    }

    /**
     * Public function to update hubungan
     */
    fun updateHubungan(hubungan: Hubungan) {
        val index = hubunganList.indexOfFirst { it.id == hubungan.id }
        if (index != -1) {
            hubunganList[index] = hubungan
            updateUI()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        const val EXTRA_HUBUNGAN = "extra_hubungan"
    }
}