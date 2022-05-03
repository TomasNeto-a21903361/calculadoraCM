package com.example.androidcalculator

import android.content.ContentValues.TAG
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder

class CalculatorModel {
    var display: String = "0"
    private val history = mutableListOf<Operation>()

    fun insertSymbol(symbol: String) : String {
        Log.i(TAG, "click no botão $symbol")
        if (display == "0") {
            display = symbol
        } else {
            display = "$display$symbol"
        }
        return display
    }

    fun performOperation(): Double {
        val expressionBuilder = ExpressionBuilder(display).build()
        val result = expressionBuilder.evaluate()
        val expression = display
        CoroutineScope(Dispatchers.IO).launch {
            addHistory(expression,result)
        }
        display = result.toString()
        return result
    }

    suspend fun addHistory(expression: String, result: Double) {
        Thread.sleep(30 * 1000)
        Log.wtf(TAG,expression + " " + result)
        history.add(Operation(expression,result))
    }

    fun getAllOperations(callback: (List<Operation>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            Thread.sleep(30 * 1000)
            callback(history.toList())
        }
    }



}