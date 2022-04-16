package com.example.androidcalculator

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidcalculator.databinding.ItemExpressionBinding


class HistoryAdapter(private val supportFragmentManager: FragmentManager,
                    //private val onOperationClick: (String) -> Unit,
                     private var items: List<OperationUi> = listOf()) :
    RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {
    class HistoryViewHolder(val binding: ItemExpressionBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        return HistoryViewHolder(
            ItemExpressionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.itemView.setOnClickListener{
            //onOperationClick(items[position].toString())
            NavigationManager.goToOperationDetail(supportFragmentManager,items[position])
        }
        //val parts = items[position]?.split("=")
        //holder.binding.textExpression.text = parts?.get(0)
        //holder.binding.textResult.text = parts?.get(1)

        holder.binding.textExpression.text = items[position].expression
        holder.binding.textResult.text = items[position].result
    }

    override fun getItemCount() = items.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(items: List<OperationUi>) {
        this.items = items
        notifyDataSetChanged()
    }
}