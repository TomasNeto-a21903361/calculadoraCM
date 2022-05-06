package com.example.androidcalculator

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CalculatorViewModel : ViewModel() {

    private val model = CalculatorModel

    fun getDisplayValue() = model.display

    fun onClickSymbol(symbol: String): String {
        return model.insertSymbol(symbol)
    }

    fun onClickEquals(onSaved: () -> Unit): String {
        model.performOperation(onSaved)
        val result = getDisplayValue().toDouble()
        return if(result % 1 == 0.0) result.toLong().toString() else result.toString()
    }

    fun onClickClear() : String {
       return model.clear()
    }

    fun onGetHistory(onFinished: (List<Operation>) -> Unit) {
        model.getAllOperations(onFinished)
    }

    fun onDeleteOperation(uuid: String, onSuccess: () -> Unit) {
        model.deleteOperation(uuid, onSuccess)
    }

    
}