package com.example.bluapp

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class SavingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saving)

        // Back button dari header
        findViewById<ImageView>(R.id.btnBack).setOnClickListener {
            finish()
        }
    }
}
