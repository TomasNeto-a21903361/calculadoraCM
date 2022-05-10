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

class CalculatorModel(private val dao: OperationDao) {
    var display: String = "0"
    private set
    //private val history = mutableListOf<Operation>()

    fun insertSymbol(symbol: String): String {
        display = if(display == "0") symbol else "$display$symbol"
        return display
    }

    fun clear(): String {
        display = "0"
        return display
    }

    fun performOperation(onFinished: () -> Unit) {
        val expressionBuilder = ExpressionBuilder(display).build()
        val result = expressionBuilder.evaluate()
        val operation = OperationRoom(expression = display, result = result, timestamp = Date().time)
        display = result.toString()
        CoroutineScope(Dispatchers.IO).launch {
            dao.insert(operation)
            onFinished()
        }
    }

    fun getAllOperations(onFinished: (List<OperationUi>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val operations = dao.getAll()
            onFinished(operations.map {
                OperationUi(it.uuid,it.expression,it.result,it.timestamp)
            })
        }
    }

    fun deleteOperation(uuid: String, onSuccess: () -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            dao.deleteByUuid(uuid)
            onSuccess()
        }
    }



}