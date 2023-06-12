package com.gptale.gptale.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.gptale.gptale.R
import com.gptale.gptale.databinding.FragmentFullStoryBinding
import com.gptale.gptale.viewmodels.FullStoryViewModel

class FullStoryFragment : Fragment(), OnClickListener {

    private var _binding: FragmentFullStoryBinding? = null
    private lateinit var viewModel: FullStoryViewModel

    private val args by navArgs<FullStoryFragmentArgs>()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this).get(FullStoryViewModel::class.java)
        _binding = FragmentFullStoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.createHistoryButton.setOnClickListener(this)
        binding.fullHistoryProgressBar.visibility = View.VISIBLE

        val idStory = args.idStory

        viewModel.requestFullStory(idStory)

        observe()

    }

    private fun observe() {
        viewModel.storyRequest.observe(viewLifecycleOwner) {
            if (it.status()) {
                binding.historyTitle.text = viewModel.story?.title
                binding.historyGender.text = viewModel.story?.gender
                binding.fullHistory.text = viewModel.story!!.fullStory
                binding.fullHistoryContainer.visibility = View.VISIBLE
                binding.fullHistoryProgressBar.visibility = View.GONE
                binding.copyHistoryButton.setOnClickListener(this)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View) {
        if (v.id == binding.copyHistoryButton.id) {
            viewModel.copyStory(requireContext())
        }
        if (v.id == R.id.create_history_button) {
            findNavController().navigate(R.id.action_FullStoryFragment_to_StartFragment)
        }
    }
}