package com.example.myapplication2

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
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
        initializeViews(view)
        return view
    }

    private fun initializeViews(view: View) {
        setupTabSwitch(view)
        setupServiceCards(view)
        setupBottomNavigation(view)

    }

    private fun setupTabSwitch(view: View) {
        val tabSwitch = view.findViewById<LinearLayout>(R.id.tabSwitch)
        val btnAkun = tabSwitch?.getChildAt(0) as? TextView
        btnAkun?.setOnClickListener {
            openActivity(ProfilActivity::class.java)
        }
    }

    private fun setupServiceCards(view: View) {
        view.findViewById<MaterialCardView>(R.id.cardSaving)?.setOnClickListener {
            openActivity(SavingActivity::class.java)
        }

        view.findViewById<MaterialCardView>(R.id.cardBluinvest)?.setOnClickListener {
            openActivity(BluinvestActivity::class.java)
        }

        view.findViewById<MaterialCardView>(R.id.cardInsurance)?.setOnClickListener {
            openActivity(InsuranceActivity::class.java)
        }

        view.findViewById<MaterialCardView>(R.id.cardKeuangan)?.setOnClickListener {
            openActivity(TagihanActivity::class.java)
        }

        view.findViewById<MaterialCardView>(R.id.cardTransaksi)?.setOnClickListener {
            openActivity(HubunganActivity::class.java)
        }

        view.findViewById<MaterialCardView>(R.id.cardMetodePembayaran)?.setOnClickListener {
            openActivity(MetodePembayaranActivity::class.java)
        }
    }

    private fun setupBottomNavigation(view: View) {
        val bottomAppBar = view.findViewById<com.google.android.material.bottomappbar.BottomAppBar>(
            R.id.bottomAppBar
        )

        if (bottomAppBar != null) {
            val bottomNavLayout = bottomAppBar.getChildAt(0) as? LinearLayout

            bottomNavLayout?.getChildAt(0)?.setOnClickListener {
                openActivity(TransaksiActivity::class.java)
            }

            bottomNavLayout?.getChildAt(1)?.setOnClickListener {
                openActivity(KeuanganActivity::class.java)
            }

            bottomNavLayout?.getChildAt(3)?.setOnClickListener {
                openActivity(RiwayatActivity::class.java)
            }

            bottomNavLayout?.getChildAt(4)?.setOnClickListener {
                openActivity(ProfilActivity::class.java)
            }
        }
    }



    private fun openActivity(activityClass: Class<*>) {
        val intent = Intent(requireContext(), activityClass)
        startActivity(intent)
    }
}