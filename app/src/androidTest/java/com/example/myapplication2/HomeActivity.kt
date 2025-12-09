package com.example.myapplication2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mainactivity)

        supportActionBar?.hide()


    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
}