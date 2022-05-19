package com.example.androidcalculator

import android.content.Context

class CalculatorRepository private constructor(private val context: Context,
        private val local: CalculatorModel, private val remote: CalculatorModel) {

    fun getExpression() = local.display

    fun insertSymbol(symbol: String): String {
        return remote.insertSymbol(symbol)
    }

    fun clear(): String {
        return remote.clear()
    }

    fun performOperation(onFinished: () -> Unit) {
        remote.performOperation {
            local.display = remote.display
            onFinished()
        }
    }

    fun getLastOperation(onFinished: (String) -> Unit) {
        if(ConnectivityUtil.isOnline(context)) {
            remote.getLastOperation(onFinished)
        } else {
            local.getLastOperation(onFinished)
        }
    }

    fun deleteOperation(uuid: String, onSuccess: () -> Unit) {
        remote.deleteOperation(uuid, onSuccess)
    }

    fun getHistory(onFinished: (List<OperationUi>) -> Unit) {
        if(ConnectivityUtil.isOnline(context)) {
            remote.getHistory { history ->
                local.deleteAllOperations {
                    local.insertOperations(history) {
                        onFinished(history)
                    }
                }
            }
        } else {
            local.getHistory(onFinished)
        }
    }


    companion object {

        private var instance: CalculatorRepository? = null

        fun init(context: Context, local: CalculatorRoom, remote: CalculatorRetrofit) {
            synchronized(this) {
                if(instance == null) {
                    instance = CalculatorRepository(context, local, remote)
                }
            }
        }

        fun getInstance(): CalculatorRepository {
            return instance ?: throw IllegalStateException("Repository not initialized")
        }

    }

}