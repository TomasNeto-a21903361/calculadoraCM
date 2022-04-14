package com.example.androidcalculator

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidcalculator.databinding.ActivityMainBinding
import net.objecthunter.exp4j.ExpressionBuilder
import java.sql.Date
import java.text.SimpleDateFormat

class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName
    private lateinit var binding: ActivityMainBinding
    private val operations = mutableListOf<OperationUi>()
    private val adapter = HistoryAdapter(::onOperationClick)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onDestroy() {
        Log.i(TAG, "o método onDestroy foi invocado")
        super.onDestroy()
    }

    override fun onStart() {
        super.onStart()

        binding.button0.setOnClickListener{onClickSymbol("0")}
        binding.button1.setOnClickListener{onClickSymbol("1")}
        binding.button2.setOnClickListener{onClickSymbol("2")}
        binding.button3.setOnClickListener{onClickSymbol("3")}
        binding.button4.setOnClickListener{onClickSymbol("4")}
        binding.button5.setOnClickListener{onClickSymbol("5")}
        binding.button6.setOnClickListener{onClickSymbol("6")}
        binding.button7.setOnClickListener{onClickSymbol("7")}
        binding.button8.setOnClickListener{onClickSymbol("8")}
        binding.button9.setOnClickListener{onClickSymbol("9")}
        binding.buttonAddition.setOnClickListener{onClickSymbol("+")}
        binding.buttonSubtraction.setOnClickListener{onClickSymbol("-")}
        binding.buttonMultiplication.setOnClickListener{onClickSymbol("*")}
        binding.buttonDivision.setOnClickListener{onClickSymbol("/")}
        binding.buttonExponent.setOnClickListener{onClickSymbol("^")}
        binding.buttonRest.setOnClickListener{onClickSymbol("%")}
        binding.buttonDot.setOnClickListener{onClickSymbol(".")}
        binding.buttonClear.setOnClickListener{onClickClear()}
        binding.buttonEquals.setOnClickListener{onClickEquals()}

        binding.rvHistoric?.layoutManager = LinearLayoutManager(this)
        binding.rvHistoric?.adapter = adapter


    }

    private fun onClickSymbol(symbol: String) {
        Log.i(TAG,"click no botão $symbol")
        if (binding.textVisor.text.toString() == "0") {
            binding.textVisor.text = symbol
        }
        else {
            binding.textVisor.append(symbol)
        }
    }

    private fun onClickEquals() {
        Log.i(TAG,"click no botao =")

        val expression = ExpressionBuilder(
            binding.textVisor.text.toString()
        ).build()

        val expressao = binding.textVisor.text.toString()

        binding.textVisor.text = expression.evaluate().toString()

        val resultado = "=" + binding.textVisor.text.toString()

        val card = OperationUi(expressao,resultado,System.currentTimeMillis())

        operations.add(card)

        Log.i(TAG,operations.toString())

        adapter.updateItems(operations)
    }

    private fun onClickClear() {
        Log.i(TAG,"click no botao clear")
        binding.textVisor.text = "0"
    }

    @SuppressLint("SimpleDateFormat")
    private fun onOperationClick (operation: String) {
        val content = operation.split(" ")
        val formatter = SimpleDateFormat("dd/MM/yyyy - hh:mm:ss")
        val date = Date(content[1].toLong())


        Toast.makeText(this, formatter.format(date) , Toast.LENGTH_LONG).show()
    }
}