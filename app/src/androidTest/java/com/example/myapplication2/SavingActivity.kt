package com.example.myapplication2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class SavingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.savingactivity)

        supportActionBar?.apply {
            title = "bluSaving"
            setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
