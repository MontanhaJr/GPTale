package com.gptale.gptale.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.gptale.gptale.R
import com.gptale.gptale.databinding.FragmentStartBinding
import com.gptale.gptale.viewmodels.StartViewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class StartFragment : Fragment(), OnClickListener {

    private var _binding: FragmentStartBinding? = null
    private lateinit var viewModel: StartViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(StartViewModel::class.java)
        _binding = FragmentStartBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonStart.setOnClickListener(this)

        observe()
    }

    private fun observe() {
        viewModel.historyRequest.observe(viewLifecycleOwner) {
            if (it.status()) {
                binding.formContainer.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE

                val action = StartFragmentDirections.actionStartFragmentToHistoryFragment(viewModel.history!!)
                action.arguments.putSerializable("startedHistory", viewModel.history)

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
            binding.formContainer.visibility = View.GONE
            binding.progressBar.visibility = View.VISIBLE

            viewModel.createHistory(
                binding.inputTitle.text.toString(),
                binding.inputGender.text.toString()
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}