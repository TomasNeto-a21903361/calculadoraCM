package com.example.androidcalculator

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CalculatorViewModel : ViewModel() {

    private val model = CalculatorModel()

    fun getDisplayValue() = model.display

    fun onClickSymbol(symbol: String): String {
        return model.insertSymbol(symbol)
    }

    fun onClickEquals(): String {
        val result = model.performOperation()
        return result.toString()
    }

    fun onClickClear() : String {
        model.display = "0"
        return model.display
    }

    fun getHistory(callback: (List<OperationUi>) -> Unit) {
        model.getAllOperations{
            operations ->
            val history = operations.map {
                OperationUi(it.expression, it.result.toString(), it.timestamp)
            }
            CoroutineScope(Dispatchers.Main).launch { callback(history) }
        }
    }
}