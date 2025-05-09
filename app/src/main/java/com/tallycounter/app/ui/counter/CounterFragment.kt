package com.tallycounter.app.ui.counter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.tallycounter.app.databinding.FragmentCounterBinding

class CounterFragment : Fragment() {
    private var _binding: FragmentCounterBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CounterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCounterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        observeViewModel()
    }

    private fun setupUI() {
        binding.apply {
            incrementButton.setOnClickListener {
                viewModel.incrementCount()
            }
            
            decrementButton.setOnClickListener {
                viewModel.decrementCount()
            }
            
            startGroupSessionButton.setOnClickListener {
                viewModel.startGroupSession()
            }
            
            joinGroupSessionButton.setOnClickListener {
                viewModel.joinGroupSession()
            }
            
            saveCountButton.setOnClickListener {
                viewModel.saveCurrentCount()
            }
        }
    }

    private fun observeViewModel() {
        viewModel.currentCount.observe(viewLifecycleOwner) { count ->
            binding.countTextView.text = count.toString()
        }

        viewModel.isGroupSession.observe(viewLifecycleOwner) { isGroup ->
            binding.groupSessionStatus.text = if (isGroup) "Group Session Active" else "Solo Session"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 