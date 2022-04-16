package com.example.androidcalculator

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.androidcalculator.databinding.FragmentOperationDetailBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_OPERATION = "param1"


/**
 * A simple [Fragment] subclass.
 * Use the [OperationDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OperationDetailFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var operationUi: OperationUi? = null
    private lateinit var binding: FragmentOperationDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            operationUi = it.getParcelable(ARG_OPERATION)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (requireActivity() as AppCompatActivity).supportActionBar?.title = getString(R.string.details)
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_operation_detail, container, false)
        binding = FragmentOperationDetailBinding.bind(view)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onStart() {
        super.onStart()
        binding.textExpression.text = operationUi?.expression
        binding.textResult.text = "=${operationUi?.result}"
        binding.textDatetime.text = operationUi?.getOperationDate()
    }

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onResume() {
        super.onResume()
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    override fun onPause() {
        super.onPause()
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment OperationDetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(operationUi: OperationUi) =
            OperationDetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_OPERATION, operationUi)
                }
            }
    }
}