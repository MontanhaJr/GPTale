package com.gptale.gptale

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.RadioGroup.OnCheckedChangeListener
import androidx.recyclerview.widget.RecyclerView
import com.gptale.gptale.databinding.RowStoryBinding
import com.gptale.gptale.models.StoryModel

class StoryAdapter : RecyclerView.Adapter<StoryAdapter.HistoryViewHolder>() {

    private var historyList = emptyList<StoryModel>()
    private var selectedOption: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val item =
            RowStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(item)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(historyList[position])
    }

    override fun getItemCount(): Int = historyList.size

    fun getSelectedOption(): Int = selectedOption

    inner class HistoryViewHolder(private val bind: RowStoryBinding) : RecyclerView.ViewHolder(bind.root), OnCheckedChangeListener {
        fun bind(history: StoryModel) {
            bind.paragraph.text = history.paragraph
            selectedOption = 0
            bind.optionsRadioGroup.clearCheck()
            bind.optionsRadioGroup.setOnCheckedChangeListener(this)

            if (history.options.isNotEmpty()) {
                bind.option1.text = history.options[0]
                bind.option2.text = history.options[1]
                bind.option3.text = history.options[2]
                bind.option4.text = history.options[3]

            } else {
                bind.optionsRadioGroup.visibility = ViewGroup.GONE
            }
        }

        override fun onCheckedChanged(radioGroup: RadioGroup?, checkedId: Int) {
            selectedOption = when (checkedId) {
                bind.option1.id -> 1
                bind.option2.id -> 2
                bind.option3.id -> 3
                bind.option4.id -> 4
                else -> 0
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(history: List<StoryModel>) {
        this.historyList = history
        notifyDataSetChanged()
    }
}

