package com.example.bluapp

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class ProfilActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profil)

        findViewById<ImageView>(R.id.btnBack).setOnClickListener {
            finish()
        }

        // Menu Pengaturan Umum
        findViewById<LinearLayout>(R.id.menuPengaturanUmum).setOnClickListener {
            loadFragment(PengaturanUmumFragment())
        }

        // Menu Ubah Password
        findViewById<LinearLayout>(R.id.menuUbahPassword).setOnClickListener {
            loadFragment(UbahPasswordFragment())
        }

        // Menu Ubah PIN
        findViewById<LinearLayout>(R.id.menuUbahPin).setOnClickListener {
            loadFragment(UbahPinFragment())
        }

        // Menu Security
        findViewById<LinearLayout>(R.id.menuSecurity).setOnClickListener {
            startActivity(Intent(this, SecurityActivity::class.java))
        }

        // Menu Notifikasi
        findViewById<LinearLayout>(R.id.menuNotifikasi).setOnClickListener {
            loadFragment(NotifikasiFragment())
        }

        // Menu Bantuan
        findViewById<LinearLayout>(R.id.menuBantuan).setOnClickListener {
            loadFragment(BantuanFragment())
        }
    }

    private fun loadFragment(fragment: androidx.fragment.app.Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerProfil, fragment)
            .addToBackStack(null)
            .commit()
    }
}
