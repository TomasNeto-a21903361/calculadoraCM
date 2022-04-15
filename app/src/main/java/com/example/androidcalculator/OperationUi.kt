package com.example.androidcalculator

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.sql.Date
import java.text.SimpleDateFormat

@Parcelize
class OperationUi(val expression: String, val result: String,
                  private val timestamp: Long = System.currentTimeMillis()) : Parcelable {


    override fun toString(): String {
        return "$expression=$result"
    }

    @SuppressLint("SimpleDateFormat")
    fun getOperationDate(): String {
        val formatter = SimpleDateFormat("dd/MM/yyyy - hh:mm:ss")
        return formatter.format(timestamp)
    }
}