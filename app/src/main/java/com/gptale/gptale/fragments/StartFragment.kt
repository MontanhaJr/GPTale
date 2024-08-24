package com.gptale.gptale.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.gptale.gptale.R
import com.gptale.gptale.database.DatabaseBuilder.getInstance
import com.gptale.gptale.databinding.FragmentStartBinding
import com.gptale.gptale.factory.StartViewModelFactory
import com.gptale.gptale.models.Story
import com.gptale.gptale.models.StoryModel
import com.gptale.gptale.repository.StartRepository
import com.gptale.gptale.viewmodels.StartViewModel

class StartFragment : Fragment(), OnClickListener {

    private lateinit var repository: StartRepository
    private var _binding: FragmentStartBinding? = null
    private val viewModel: StartViewModel by viewModels {
        StartViewModelFactory(repository)
    }

    private val binding get() = _binding!!

    private val database by lazy {
        getInstance(this.requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        repository = StartRepository(database.storyDao())
        _binding = FragmentStartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonStart.setOnClickListener(this)

        observe()
    }

    @SuppressLint("ResourceType")
    private fun observe() {
        viewModel.storyRequest.observe(viewLifecycleOwner) {
            if (it.status()) {
                binding.formContainer.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE

                val action =
                    StartFragmentDirections.actionStartFragmentToStoryFragment(viewModel.createdStory)
                action.arguments.putSerializable("startedStory", viewModel.createdStory)

                findNavController().navigate(action)
            } else {
                binding.formContainer.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
                Toast.makeText(context, it.message(), Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onClick(v: View) {
        if (v.id == R.id.button_start) {
            if (binding.inputTitle.text.toString() != "" && binding.inputGender.text.toString() != "") {
                binding.formContainer.visibility = View.GONE
                binding.progressBar.visibility = View.VISIBLE

                viewModel.createStory(
                    binding.inputTitle.text.toString(),
                    binding.inputGender.text.toString()
                )

            } else {
                Toast.makeText(context, getString(R.string.empty_input_warning), Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


