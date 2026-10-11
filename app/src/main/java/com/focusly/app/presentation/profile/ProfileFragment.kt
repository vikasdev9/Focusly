package com.focusly.app.presentation.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.focusly.app.R
import com.focusly.app.databinding.FragmentProfileBinding
import com.focusly.app.utils.ViewModelFactory
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProfileViewModel by viewModels {
        ViewModelFactory(requireContext().applicationContext)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
        observeUiState()
    }

    private fun setupListeners() {
        binding.menuRoutines.setOnClickListener {
            findNavController().navigate(R.id.nav_routines)
        }

        binding.menuHabits.setOnClickListener {
            findNavController().navigate(R.id.nav_habits)
        }

        binding.menuAppBlocking.setOnClickListener {
            findNavController().navigate(R.id.nav_blocking)
        }

        binding.menuSubscription.setOnClickListener {
            findNavController().navigate(R.id.nav_subscription)
        }
    }

    private fun observeUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    binding.tvProfileName.text = state.userName
                    binding.tvProfileStreak.text = "${state.streakDays} Days"
                    val hrs = state.totalFocusMinutes / 60
                    val mins = state.totalFocusMinutes % 60
                    binding.tvProfileFocusMinutes.text = "${hrs}H ${mins}M"
                    binding.tvProfileEnergy.text = "${state.focusEnergy}"
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
