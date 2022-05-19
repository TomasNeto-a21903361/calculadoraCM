package com.example.androidcalculator

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidcalculator.databinding.FragmentHistoryBinding
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_OPERATIONS = "param1"


/**
 * A simple [Fragment] subclass.
 * Use the [HistoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HistoryFragment : Fragment() {
    private lateinit var model: CalculatorModel
    private lateinit var binding: FragmentHistoryBinding
    //private val adapter = history?.let { HistoryAdapter(parentFragmentManager, it) }
    private var adapter = HistoryAdapter(onClick = ::onOperationClick, onLongClick = ::onOperationLongClick)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        model = CalculatorRoom(CalculatorDatabase.getInstance(requireContext()).operationDao())
        (requireActivity() as AppCompatActivity).supportActionBar?.title = getString(R.string.history)
        val view = inflater.inflate(R.layout.fragment_history, container, false)
        binding = FragmentHistoryBinding.bind(view)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.rvHistoric.layoutManager = LinearLayoutManager(context)
        binding.rvHistoric.adapter = adapter
        model.getHistory { updateHistory(it) }
    }

    /*
    @SuppressLint("SourceLockedOrientationActivity")
    override fun onResume() {
        super.onResume()
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    override fun onPause() {
        super.onPause()
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR
    }
     */

    private fun onOperationClick(operation: OperationUi) {
        NavigationManager.goToOperationDetail(parentFragmentManager, operation)
    }

    private fun onOperationLongClick(operation: OperationUi): Boolean {
        Toast.makeText(requireContext(), getString(R.string.delete), Toast.LENGTH_SHORT).show()
        model.deleteOperation(operation.uuid) { model.getHistory { updateHistory(it) } }
        return false
    }

    private fun updateHistory(operations: List<OperationUi>) {
        val history = operations.map { OperationUi(it.uuid, it.expression, it.result, it.timestamp) }
        CoroutineScope(Dispatchers.Main).launch {
            showHistory(history.isNotEmpty())
            adapter.updateItems(history)
        }
    }

    private fun showHistory(show: Boolean) {
        if (show) {
            binding.rvHistoric.visibility = View.VISIBLE
        } else {
            binding.rvHistoric.visibility = View.GONE
        }
    }

    /*companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HistoryFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(history: ArrayList<OperationUi>) =
             HistoryFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(ARG_OPERATIONS, history)
                }
        }
    }
*/

    private fun getAllOperationsWs(callback: (List<OperationUi>) -> Unit) {
        data class GetAllOperationsResponse(val uuid: String, val expression:
        String, val result: Double, val timestamp: Long)

        CoroutineScope(Dispatchers.IO).launch {
            val request: Request = Request.Builder()
                .url("https://cm-calculadora.herokuapp.com/api/operations")
                .addHeader("apikey","8270435acfead39ccb03e8aafbf37c49359dfbbcac4ef4769ae82c9531da0e17")
                .build()

            val response = OkHttpClient().newCall(request).execute().body
            if (response!=null) {
                val responseObj = Gson().fromJson(response.toString(),
                Array<GetAllOperationsResponse>::class.java).toList()
                callback(responseObj.map {
                    OperationUi(uuid = it.uuid, expression = it.expression, result = it.result, timestamp = it.timestamp)
                })
            }
        }
    }

}