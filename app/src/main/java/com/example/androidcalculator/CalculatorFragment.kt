package com.example.androidcalculator

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidcalculator.databinding.ActivityMainBinding
import com.example.androidcalculator.databinding.FragmentCalculatorBinding
import net.objecthunter.exp4j.ExpressionBuilder
import java.sql.Date
import java.text.SimpleDateFormat

class CalculatorFragment : Fragment() {

    private val TAG = MainActivity::class.java.simpleName
    //private lateinit var binding: ActivityMainBinding


    private lateinit var binding: FragmentCalculatorBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (requireActivity() as AppCompatActivity).supportActionBar?.title = getString(R.string.calculator)
        val view = inflater.inflate(
            R.layout.fragment_calculator, container, false
        )
        binding = FragmentCalculatorBinding.bind(view)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        binding.button0.setOnClickListener { onClickSymbol("0") }
        binding.button1.setOnClickListener { onClickSymbol("1") }
        binding.button2.setOnClickListener { onClickSymbol("2") }
        binding.button3.setOnClickListener { onClickSymbol("3") }
        binding.button4.setOnClickListener { onClickSymbol("4") }
        binding.button5.setOnClickListener { onClickSymbol("5") }
        binding.button6.setOnClickListener { onClickSymbol("6") }
        binding.button7.setOnClickListener { onClickSymbol("7") }
        binding.button8.setOnClickListener { onClickSymbol("8") }
        binding.button9.setOnClickListener { onClickSymbol("9") }
        binding.buttonAddition.setOnClickListener { onClickSymbol("+") }
        binding.buttonSubtraction.setOnClickListener { onClickSymbol("-") }
        binding.buttonMultiplication.setOnClickListener { onClickSymbol("*") }
        binding.buttonDivision.setOnClickListener { onClickSymbol("/") }
        binding.buttonExponent.setOnClickListener { onClickSymbol("^") }
        binding.buttonRest.setOnClickListener { onClickSymbol("%") }
        binding.buttonDot.setOnClickListener { onClickSymbol(".") }
        binding.buttonClear.setOnClickListener { onClickClear() }
        binding.buttonEquals.setOnClickListener { onClickEquals() }


    }

    private fun onClickSymbol(symbol: String) {
        Log.i(TAG, "click no botão $symbol")
        if (binding.textVisor.text.toString() == "0") {
            binding.textVisor.text = symbol
        } else {
            binding.textVisor.append(symbol)
        }
    }

    private fun onClickEquals() {
        Log.i(TAG, "click no botao =")

        val expression = binding.textVisor.text.toString()

        val expressionBuilder = ExpressionBuilder(
            expression
        ).build()

        val result = expressionBuilder.evaluate().toString()

        binding.textVisor.text = result


        (activity as MainActivity).addOperation(
            OperationUi(
                expression = expression,
                result = result
            )
        )


        Log.i(TAG, (activity as MainActivity).getOperations().toString())


    }

    private fun onClickClear() {
        Log.i(TAG, "click no botao clear")
        binding.textVisor.text = "0"
    }


    private fun onOperationClick(operation: String) {
        Toast.makeText(activity as Context, operation, Toast.LENGTH_LONG).show()
    }
}