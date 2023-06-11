package com.gptale.gptale.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.gptale.gptale.HistoryAdapter
import com.gptale.gptale.R
import com.gptale.gptale.databinding.FragmentHistoryBinding
import com.gptale.gptale.models.HistoryModel
import com.gptale.gptale.viewmodels.HistoryViewModel

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class HistoryFragment : Fragment(), OnClickListener {

    private var _binding: FragmentHistoryBinding? = null
    private var adapter = HistoryAdapter()
    private lateinit var viewModel: HistoryViewModel

    private val args by navArgs<HistoryFragmentArgs>()
    private val paragraph: MutableList<HistoryModel> = mutableListOf()

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
        viewModel = ViewModelProvider(this).get(HistoryViewModel::class.java)

        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_HistoryFragment_to_StartFragment)
        }

        paragraph.add(args.startedHistory)

        binding.historyTitle.text = args.startedHistory.title
        binding.historyGender.text = args.startedHistory.gender

        binding.reyclerviewHistory.layoutManager = LinearLayoutManager(context)
        binding.reyclerviewHistory.adapter = adapter
        adapter.setData(paragraph)
        binding.continueButton.setOnClickListener(this)

        if (args.startedHistory.options.isEmpty()) {
            binding.continueButton.visibility = View.GONE
        }

        observe()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun observe() {
        viewModel.historyRequest.observe(viewLifecycleOwner) {
            if (it.status()) {
                binding.optionsProgressBar.visibility = View.GONE
                if (viewModel.history!!.options.isNotEmpty()) {
                    binding.continueButton.visibility = View.VISIBLE
                }

                paragraph.last().options = emptyList()
                paragraph.add(viewModel.history!!)


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
                    idHistory = args.startedHistory.id,
                    optionSelected = adapter.getSelectedOption()
                )
                binding.continueButton.visibility = View.GONE
                binding.optionsProgressBar.visibility = View.VISIBLE
            } else {
                Snackbar.make(v, "Selecione uma opção", Snackbar.LENGTH_LONG).show()
            }
        }
    }
}