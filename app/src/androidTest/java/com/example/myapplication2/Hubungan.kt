package com.example.myapplication2

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Hubungan(
    val id: String,
    val nama: String,
    val relasi: String,
    val phone: String,
    val email: String = "",
    val alamat: String = "",
    val tanggalLahir: String = ""
) : Parcelable {

    /**
     * Get initial dari nama (first letter)
     */
    fun getInitial(): String {
        return if (nama.isNotEmpty()) {
            nama.first().toString().uppercase()
        } else {
            "?"
        }
    }

    /**
     * Format phone number untuk display
     */
    fun getFormattedPhone(): String {
        return if (phone.startsWith("0")) {
            phone
        } else if (phone.startsWith("62")) {
            "0${phone.substring(2)}"
        } else {
            phone
        }
    }
}