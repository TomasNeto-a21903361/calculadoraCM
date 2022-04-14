package com.example.androidcalculator

class OperationUi(val expression: String, val result: String,
                  val timestamp: Long) {


    override fun toString(): String {
        return expression + result + " " + timestamp
    }
}