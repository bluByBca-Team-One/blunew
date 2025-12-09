package com.example.bluapp

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class HubunganAdapter : ListAdapter<Hubungan, HubunganAdapter.HubunganViewHolder>(HubunganDiffCallback()) {

    // Listener untuk item click
    private var onItemClickListener: ((Hubungan) -> Unit)? = null

    fun setOnItemClickListener(listener: (Hubungan) -> Unit) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HubunganViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_hubungan, parent, false)
        return HubunganViewHolder(view)
    }

    override fun onBindViewHolder(holder: HubunganViewHolder, position: Int) {
        val hubungan = getItem(position)
        holder.bind(hubungan)
    }

    inner class HubunganViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvInitial: TextView = itemView.findViewById(R.id.tvInitial)
        private val tvNama: TextView = itemView.findViewById(R.id.tvNama)
        private val tvRelasi: TextView = itemView.findViewById(R.id.tvRelasi)
        private val tvPhone: TextView = itemView.findViewById(R.id.tvPhone)
        private val btnCall: ImageButton = itemView.findViewById(R.id.btnCall)
        private val btnMessage: ImageButton = itemView.findViewById(R.id.btnMessage)

        fun bind(hubungan: Hubungan) {
            // Set data
            tvInitial.text = hubungan.getInitial()
            tvNama.text = hubungan.nama
            tvRelasi.text = hubungan.relasi
            tvPhone.text = hubungan.getFormattedPhone()

            // Item click listener
            itemView.setOnClickListener {
                onItemClickListener?.invoke(hubungan)
            }

            // Call button - Implicit Intent
            btnCall.setOnClickListener {
                makePhoneCall(hubungan.phone)
            }

            // Message button - Implicit Intent
            btnMessage.setOnClickListener {
                sendWhatsAppMessage(hubungan.phone)
            }
        }

        /**
         * Implicit Intent: Make phone call
         */
        private fun makePhoneCall(phoneNumber: String) {
            try {
                val intent = Intent(Intent.ACTION_DIAL).apply {
                    data = Uri.parse("tel:$phoneNumber")
                }
                itemView.context.startActivity(intent)
            } catch (e: Exception) {
                showToast("Tidak dapat membuka aplikasi telepon")
            }
        }

        /**
         * Implicit Intent: Send WhatsApp message
         */
        private fun sendWhatsAppMessage(phoneNumber: String) {
            try {
                // Format number untuk WhatsApp (remove leading 0, add 62)
                val waNumber = if (phoneNumber.startsWith("0")) {
                    "62${phoneNumber.substring(1)}"
                } else {
                    phoneNumber
                }

                val url = "https://wa.me/$waNumber"
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse(url)
                }
                itemView.context.startActivity(intent)
            } catch (e: Exception) {
                // Fallback: Send SMS
                sendSMS(phoneNumber)
            }
        }

        /**
         * Fallback: Send SMS
         */
        private fun sendSMS(phoneNumber: String) {
            try {
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse("sms:$phoneNumber")
                }
                itemView.context.startActivity(intent)
            } catch (e: Exception) {
                showToast("Tidak dapat membuka aplikasi pesan")
            }
        }

        private fun showToast(message: String) {
            android.widget.Toast.makeText(itemView.context, message, android.widget.Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * DiffUtil untuk performa optimal
     */
    class HubunganDiffCallback : DiffUtil.ItemCallback<Hubungan>() {
        override fun areItemsTheSame(oldItem: Hubungan, newItem: Hubungan): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Hubungan, newItem: Hubungan): Boolean {
            return oldItem == newItem
        }
    }
}