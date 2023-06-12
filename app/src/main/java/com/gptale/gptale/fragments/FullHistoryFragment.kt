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
import com.gptale.gptale.databinding.FragmentFullHistoryBinding
import com.gptale.gptale.viewmodels.FullHistoryViewModel

class FullHistoryFragment : Fragment(), OnClickListener {

    private var _binding: FragmentFullHistoryBinding? = null
    private lateinit var viewModel: FullHistoryViewModel

    private val args by navArgs<FullHistoryFragmentArgs>()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this).get(FullHistoryViewModel::class.java)
        _binding = FragmentFullHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.createHistoryButton.setOnClickListener(this)
        binding.fullHistoryProgressBar.visibility = View.VISIBLE

        val idHistory = args.idHistory

        viewModel.requestFullHistory(idHistory)

        observe()

    }

    private fun observe() {
        viewModel.historyRequest.observe(viewLifecycleOwner) {
            if (it.status()) {
                binding.historyTitle.text = viewModel.history?.title
                binding.historyGender.text = viewModel.history?.gender
                binding.fullHistory.text = viewModel.history!!.fullHistory
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
            viewModel.copyHistory(requireContext())
        }
        if (v.id == R.id.create_history_button) {
            findNavController().navigate(R.id.action_FullHistoryFragment_to_StartFragment)
        }
    }
}