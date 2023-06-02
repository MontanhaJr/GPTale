package com.gptale.gptale

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gptale.gptale.databinding.RowHistoryBinding
import com.gptale.gptale.models.HistoryModel

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    private var historyList = emptyList<HistoryModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val item =
            RowHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(item)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(historyList[position])
    }

    override fun getItemCount(): Int {
        return historyList.size
    }

    inner class HistoryViewHolder(private val bind: RowHistoryBinding) : RecyclerView.ViewHolder(bind.root) {
        fun bind(history: HistoryModel) {
            bind.paragraph.text = history.paragraph
            bind.option1.text = history.options[0]
            bind.option2.text = history.options[1]
            bind.option3.text = history.options[2]
            bind.option4.text = history.options[3]
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(history: List<HistoryModel>) {
        this.historyList = history
        notifyDataSetChanged()
    }
}

