package com.example.bluapp

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetailTagihanActivity : AppCompatActivity() {

    private lateinit var tvJenisBill: TextView
    private lateinit var tvBillId: TextView
    private lateinit var tvAmount: TextView
    private lateinit var tvDueDate: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tagihanactivity)

        supportActionBar?.apply {
            title = "Detail Tagihan"
            setDisplayHomeAsUpEnabled(true)
        }


        val jenisBill = intent.getStringExtra(TagihanActivity.EXTRA_JENIS_BILL) ?: ""
        val billId = intent.getStringExtra(TagihanActivity.EXTRA_BILL_ID) ?: ""
        val amount = intent.getIntExtra(TagihanActivity.EXTRA_AMOUNT, 0)


        displayData(jenisBill, billId, amount)
    }


    private fun displayData(jenisBill: String, billId: String, amount: Int) {
        tvJenisBill.text = jenisBill
        tvBillId.text = "ID: $billId"
        tvAmount.text = formatRupiah(amount)
        tvDueDate.text = "Jatuh Tempo: 25 Des 2025"
    }

    private fun formatRupiah(amount: Int): String {
        val formatter = java.text.NumberFormat.getNumberInstance(java.util.Locale("id", "ID"))
        return "Rp ${formatter.format(amount)}"
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}