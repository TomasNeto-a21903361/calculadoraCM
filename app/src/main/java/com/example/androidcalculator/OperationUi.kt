package com.example.androidcalculator

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.sql.Date
import java.text.SimpleDateFormat

@Parcelize
data class OperationUi(
    val uuid: String, val expression: String, val result: Double, val timestamp: Long
) : Parcelable {


    override fun toString(): String {
        return "$expression=$result"
    }

    @SuppressLint("SimpleDateFormat")
    fun getOperationDate(): String {
        val formatter = SimpleDateFormat("dd/MM/yyyy - hh:mm:ss")
        return formatter.format(timestamp)
    }
}