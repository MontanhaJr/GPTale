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
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.gptale.gptale.StoryAdapter
import com.gptale.gptale.R
import com.gptale.gptale.database.DatabaseBuilder.getInstance
import com.gptale.gptale.databinding.FragmentStoryBinding
import com.gptale.gptale.factory.StoryViewModelFactory
import com.gptale.gptale.models.Story
import com.gptale.gptale.repository.StoryRepository
import com.gptale.gptale.viewmodels.StoryViewModel

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class StoryFragment : Fragment(), OnClickListener {

    private lateinit var repository: StoryRepository
    private val database by lazy {
        getInstance(this.requireContext())
    }

    private var _binding: FragmentStoryBinding? = null
    private var adapter = StoryAdapter()
    private val viewModel: StoryViewModel by viewModels {
        StoryViewModelFactory(repository)
    }
    private var storyFinished: Boolean = false

    private val args by navArgs<StoryFragmentArgs>()
    private lateinit var argumentos: Story
    private val paragraph: MutableList<Story> = mutableListOf()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        repository = StoryRepository(database.storyDao())

        argumentos = args.startedStory
        viewModel.processStory(argumentos)
        viewModel.story = argumentos
        _binding = FragmentStoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.createStoryButton.setOnClickListener(this)
        binding.continueButton.setOnClickListener(this)
        binding.saveStoryButton.setOnClickListener(this)

        paragraph.add(argumentos)

        binding.storyTitle.text = argumentos.title
        binding.storyGender.text = argumentos.gender

        binding.reyclerviewStory.layoutManager = LinearLayoutManager(context)
        binding.reyclerviewStory.adapter = adapter
        adapter.setData(paragraph)

        if (argumentos.options.isEmpty()) {
            binding.continueButton.visibility = View.GONE
            binding.saveStoryButton.visibility = View.VISIBLE
        }

        observe()

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            if (viewModel.story.options.isEmpty()) {
                findNavController().navigate(R.id.action_StoryFragment_to_StartFragment)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun observe() {
        viewModel.storyRequest.observe(viewLifecycleOwner) {
            if (it.status()) {
                binding.optionsProgressBar.visibility = View.GONE
                if (viewModel.story.options.isNotEmpty()) {
                    binding.continueButton.visibility = View.VISIBLE
                } else {
                    storyFinished = true
                    binding.continueButton.visibility = View.GONE
                    binding.saveStoryButton.visibility = View.VISIBLE
                }

                viewModel.story.let { story ->
                    adapter.addStory(story)
                    binding.reyclerviewStory.scrollToPosition(adapter.itemCount - 1)
                }


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
                viewModel.sendOption(adapter.getSelectedOption())
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
            val action =
                StoryFragmentDirections.actionStoryFragmentToFullStoryFragment(viewModel.story.uid)
            action.arguments.putSerializable("idStory", viewModel.story.uid)

            findNavController().navigate(action)
        }
    }
}