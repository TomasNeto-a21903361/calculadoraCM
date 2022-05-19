package com.example.androidcalculator

import android.app.Application
import android.app.SharedElementCallback
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CalculatorViewModel : ViewModel() {

    private val model = CalculatorRepository.getInstance()

    fun getDisplayValue() = model.getExpression()

    fun onClickSymbol(symbol: String): String {
        return model.insertSymbol(symbol)
    }

    fun onClickEquals(onFinished: () -> Unit) {
        model.performOperation(onFinished)
    }

    fun onClickClear() : String {
       return model.clear()
    }

    fun onGetHistory(onFinished: (List<OperationUi>) -> Unit) {
        model.getHistory(onFinished)
    }

    fun onDeleteOperation(uuid: String, onSuccess: () -> Unit) {
        model.deleteOperation(uuid, onSuccess)
    }

    
}