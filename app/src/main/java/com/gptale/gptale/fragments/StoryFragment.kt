package com.gptale.gptale.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.gptale.gptale.StoryAdapter
import com.gptale.gptale.R
import com.gptale.gptale.databinding.FragmentStoryBinding
import com.gptale.gptale.models.StoryModel
import com.gptale.gptale.viewmodels.StoryViewModel

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class StoryFragment : Fragment(), OnClickListener {

    private var _binding: FragmentStoryBinding? = null
    private var adapter = StoryAdapter()
    private lateinit var viewModel: StoryViewModel
    private var storyFinished: Boolean = false

    private val args by navArgs<StoryFragmentArgs>()
    private val paragraph: MutableList<StoryModel> = mutableListOf()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentStoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(StoryViewModel::class.java)

        binding.createStoryButton.setOnClickListener(this)
        binding.continueButton.setOnClickListener(this)
        binding.saveStoryButton.setOnClickListener(this)

        paragraph.add(args.startedStory)

        binding.storyTitle.text = args.startedStory.title
        binding.storyGender.text = args.startedStory.gender

        binding.reyclerviewStory.layoutManager = LinearLayoutManager(context)
        binding.reyclerviewStory.adapter = adapter
        adapter.setData(paragraph)

        if (args.startedStory.options.isEmpty()) {
            binding.continueButton.visibility = View.GONE
            binding.saveStoryButton.visibility = View.VISIBLE
        }

        observe()

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            if (viewModel.story!!.options.isEmpty()){
                findNavController().navigate(R.id.action_StoryFragment_to_StartFragment)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun observe() {
        viewModel.storyRequest.observe(viewLifecycleOwner) {
            if (it.status()) {
                binding.optionsProgressBar.visibility = View.GONE
                if (viewModel.story!!.options.isNotEmpty()) {
                    binding.continueButton.visibility = View.VISIBLE
                }
                else {
                    storyFinished = true
                    binding.continueButton.visibility = View.GONE
                    binding.saveStoryButton.visibility = View.VISIBLE
                }

                paragraph.last().options = emptyList()
                paragraph.add(viewModel.story!!)


                adapter.notifyDataSetChanged()
            } else {
                Toast.makeText(context, it.message(), Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View) {
        if (v.id == R.id.continue_button) {
            if (adapter.getSelectedOption() > 0) {
                viewModel.sendOption(
                    idStory = args.startedStory.id,
                    optionSelected = adapter.getSelectedOption()
                )
                binding.continueButton.visibility = View.GONE
                binding.optionsProgressBar.visibility = View.VISIBLE
            } else {
                Snackbar.make(v, "Selecione uma opção", Snackbar.LENGTH_LONG).show()
            }
        }

        if (v.id == R.id.create_story_button) {
            findNavController().navigate(R.id.action_StoryFragment_to_StartFragment)
        }

        if (v.id == R.id.save_story_button) {
            val action = StoryFragmentDirections.actionStoryFragmentToFullStoryFragment(viewModel.story!!.id)
            action.arguments.putSerializable("idStory", viewModel.story!!.id)

            findNavController().navigate(action)
        }
    }
}