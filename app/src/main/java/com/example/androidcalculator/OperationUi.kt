package com.example.androidcalculator

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class OperationUi(val expression: String, val result: String,
                  val timestamp: Long) : Parcelable {


    override fun toString(): String {
        return expression + result + " " + timestamp
    }
}