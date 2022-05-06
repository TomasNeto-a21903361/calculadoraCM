package com.example.androidcalculator

import android.content.ContentValues.TAG
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder

object CalculatorModel {
    var display: String = "0"
    private set
    private val history = mutableListOf<Operation>()

    fun insertSymbol(symbol: String): String {
        display = if(display == "0") symbol else "$display$symbol"
        return display
    }

    fun clear(): String {
        display = "0"
        return display
    }

    fun performOperation(onSaved: () -> Unit) {
        val expressionBuilder = ExpressionBuilder(display).build()
        val result = expressionBuilder.evaluate()
        val operation = Operation(expression = display, result = result)
        display = result.toString()
        CoroutineScope(Dispatchers.IO).launch {
            addHistory(operation)
            onSaved()
        }
    }

    private fun addHistory(operation: Operation) {
        Thread.sleep(1 * 1000)
        //Log.wtf(TAG,expression + " " + result)
        history.add(operation)
    }

    fun getAllOperations(callback: (List<Operation>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            Thread.sleep(1 * 1000)
            callback(history.toList())
        }
    }

    fun deleteOperation(uuid: String, onSuccess: () -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            Thread.sleep(1 * 1000)
            val operation = history.find { it.uuid == uuid }
            history.remove(operation)
            onSuccess()
        }
    }



}