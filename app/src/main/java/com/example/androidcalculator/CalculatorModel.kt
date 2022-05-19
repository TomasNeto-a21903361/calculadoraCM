package com.example.androidcalculator

import android.content.ContentValues.TAG
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder
import java.util.*

abstract class CalculatorModel {
    var display: String = "0"
    //private set
    //private val history = mutableListOf<Operation>()

    fun insertSymbol(symbol: String): String {
        display = if(display == "0") symbol else "$display$symbol"
        return display
    }

    fun clear(): String {
        display = "0"
        return display
    }

    open fun performOperation(onFinished: () -> Unit) {
        val expressionBuilder = ExpressionBuilder(display).build()
        val result = expressionBuilder.evaluate()
        display = result.toString()
        onFinished()
    }

    abstract fun insertOperations(operations: List<OperationUi>, onFinished: (List<OperationUi>) -> Unit)
    abstract fun getLastOperation(onFinished: (String) -> Unit)
    abstract fun deleteOperation(uuid: String, onFinished: () -> Unit)
    abstract fun deleteAllOperations(onFinished: () -> Unit)
    abstract fun getHistory(onFinished: (List<OperationUi>) -> Unit)



}