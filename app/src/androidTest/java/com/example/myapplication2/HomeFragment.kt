package com.example.bluapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.google.android.material.card.MaterialCardView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Initialize all views
        initializeViews(view)

        return view
    }

    private fun initializeViews(view: View) {


        // Tab Switch (Akun / Kartu)
        setupTabSwitch(view)

        // Account Action Buttons
        setupAccountActions(view)

        // Service Cards (Grid Menu)
        setupServiceCards(view)

        // Bottom Navigation
        setupBottomNavigation(view)

        // FAB
        setupFAB(view)
    }


    // ==================== Tab Switch (Akun / Kartu) ====================
    private fun setupTabSwitch(view: View) {
        val tabSwitch = view.findViewById<LinearLayout>(R.id.tabSwitch)

        // Akun Tab (first TextView)
        val btnAkun = tabSwitch.getChildAt(0) as? TextView
        btnAkun?.setOnClickListener {
            openActivity(AkunActivity::class.java)
        }

        // Kartu Tab (second TextView)
        val btnKartu = tabSwitch.getChildAt(1) as? TextView
        btnKartu?.setOnClickListener {
            openActivity(KartuActivity::class.java)
        }
    }

    // ==================== Account Action Buttons ====================
    private fun setupAccountActions(view: View) {
        // Find the ConstraintLayout that contains the buttons
        val accountInner = view.findViewById<androidx.constraintlayout.widget.ConstraintLayout>(
            R.id.accountInner
        )

        if (accountInner != null) {
            // Get the LinearLayout containing the buttons
            val buttonContainer = accountInner.getChildAt(3) as? LinearLayout

            // Pindah Dana Button
            val btnPindahDana = buttonContainer?.getChildAt(0) as? AppCompatButton
            btnPindahDana?.setOnClickListener {
                openActivity(PindahDanaActivity::class.java)
            }

            // QRIS Button
            val btnQRIS = buttonContainer?.getChildAt(1) as? AppCompatButton
            btnQRIS?.setOnClickListener {
                openActivity(QRISActivity::class.java)
            }
        }
    }

    // ==================== Service Cards (Grid Menu) ====================
    private fun setupServiceCards(view: View) {
        // Saving
        view.findViewById<MaterialCardView>(R.id.cardSaving)?.setOnClickListener {
            openActivity(SavingActivity::class.java)
        }

        // bluInvest
        view.findViewById<MaterialCardView>(R.id.cardBluinvest)?.setOnClickListener {
            openActivity(BluinvestActivity::class.java)
        }

        // Insurance
        view.findViewById<MaterialCardView>(R.id.cardInsurance)?.setOnClickListener {
            openActivity(InsuranceActivity::class.java)
        }

        // Keuangan
        view.findViewById<MaterialCardView>(R.id.cardKeuangan)?.setOnClickListener {
            openActivity(TagihanActivity::class.java)
        }

        // Transaksi
        view.findViewById<MaterialCardView>(R.id.cardTransaksi)?.setOnClickListener {
            openActivity(HubunganActivity::class.java)
        }

        // Metode Pembayaran
        view.findViewById<MaterialCardView>(R.id.cardMetodePembayaran)?.setOnClickListener {
            openActivity(MetodePembayaranActivity::class.java)
        }
    }

    // ==================== Bottom Navigation ====================
    private fun setupBottomNavigation(view: View) {
        val bottomAppBar = view.findViewById<com.google.android.material.bottomappbar.BottomAppBar>(
            R.id.bottomAppBar
        )

        if (bottomAppBar != null) {
            val bottomNavLayout = bottomAppBar.getChildAt(0) as? LinearLayout

            // Transaksi (first item)
            bottomNavLayout?.getChildAt(0)?.setOnClickListener {
                openActivity(TransaksiActivity::class.java)
            }

            // Keuangan (second item)
            bottomNavLayout?.getChildAt(1)?.setOnClickListener {
                openActivity(KeuanganActivity::class.java)
            }

            // Skip index 2 (FAB space)

            // Riwayat (fourth item - index 3)
            bottomNavLayout?.getChildAt(3)?.setOnClickListener {
                openActivity(RiwayatActivity::class.java)
            }

            // Profil (fifth item - index 4)
            bottomNavLayout?.getChildAt(4)?.setOnClickListener {
                openActivity(ProfilActivity::class.java)
            }
        }
    }

    // ==================== FAB (Floating Action Button) ====================
    private fun setupFAB(view: View) {
        view.findViewById<FloatingActionButton>(R.id.fab)?.setOnClickListener {
            // FAB biasanya untuk aksi utama, misal QR Scanner atau Home
            openActivity(HomeActivity::class.java)
        }
    }

    // ==================== Helper Function ====================
    private fun openActivity(activityClass: Class<*>) {
        val intent = Intent(requireContext(), activityClass)
        startActivity(intent)
    }
}