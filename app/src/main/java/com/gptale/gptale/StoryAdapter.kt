package com.gptale.gptale

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.RadioGroup.OnCheckedChangeListener
import androidx.recyclerview.widget.RecyclerView
import com.gptale.gptale.databinding.RowStoryBinding
import com.gptale.gptale.models.Story

class StoryAdapter : RecyclerView.Adapter<StoryAdapter.StoryViewHolder>() {

    private var storyList = mutableListOf<Story>()
    private var selectedOption: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val item =
            RowStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoryViewHolder(item)
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        holder.bind(storyList[position])
    }

    override fun getItemCount(): Int = storyList.size

    fun getSelectedOption(): Int = selectedOption

    inner class StoryViewHolder(private val bind: RowStoryBinding) :
        RecyclerView.ViewHolder(bind.root), OnCheckedChangeListener {
        fun bind(story: Story) {
            bind.paragraph.text = story.paragraph
            selectedOption = 0
            bind.optionsRadioGroup.clearCheck()
            bind.optionsRadioGroup.setOnCheckedChangeListener(null)

            if (story.options.isNotEmpty()) {
                bind.optionsRadioGroup.visibility = View.VISIBLE
                bind.option1.text = story.options[0]
                bind.option2.text = story.options[1]
                bind.option3.text = story.options[2]
                bind.option4.text = story.options[3]
            } else {
                bind.optionsRadioGroup.visibility = View.GONE
            }

            bind.optionsRadioGroup.setOnCheckedChangeListener(this)
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
    fun setData(stories: List<Story>) {
        this.storyList.clear()
        this.storyList.addAll(stories)
        notifyDataSetChanged()
    }

    fun addStory(story: Story) {
        if (storyList.isNotEmpty()) {
            storyList.last().options = emptyList()
            notifyItemChanged(storyList.size - 1)
        }

        // Adicionar nova história
        this.storyList.add(story)
        notifyItemInserted(storyList.size - 1)
    }
}
