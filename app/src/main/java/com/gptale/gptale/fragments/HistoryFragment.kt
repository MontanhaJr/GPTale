package com.gptale.gptale.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.gptale.gptale.HistoryAdapter
import com.gptale.gptale.R
import com.gptale.gptale.databinding.FragmentHistoryBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private var adapter= HistoryAdapter()

    private val args by navArgs<HistoryFragmentArgs>()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_HistoryFragment_to_StartFragment)
        }

        val history = args.startedHistory
        binding.historyTitle.text = history.title
        binding.historyGender.text = history.gender

        binding.reyclerviewHistory.layoutManager = LinearLayoutManager(context)
        binding.reyclerviewHistory.adapter = adapter
        adapter.setData(listOf(history))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}