package com.focusly.app.presentation.home

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
import com.focusly.app.databinding.FragmentHomeBinding
import com.focusly.app.domain.model.PaceType
import com.focusly.app.utils.ViewModelFactory
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels {
        ViewModelFactory(requireContext().applicationContext)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
        observeUiState()
    }

    private fun setupListeners() {
        binding.btnStartFocus.setOnClickListener {
            findNavController().navigate(R.id.nav_focus)
        }

        binding.cardPaceLow.setOnClickListener {
            viewModel.selectFeelingPace(PaceType.LOW_ENERGY)
        }

        binding.cardPaceSteady.setOnClickListener {
            viewModel.selectFeelingPace(PaceType.STEADY)
        }

        binding.cardPaceBright.setOnClickListener {
            viewModel.selectFeelingPace(PaceType.FEELING_BRIGHT)
        }

        binding.btnMinusMinutes.setOnClickListener {
            viewModel.adjustCalmMinutes(-5)
        }

        binding.btnPlusMinutes.setOnClickListener {
            viewModel.adjustCalmMinutes(5)
        }

        binding.plantCedar.setOnClickListener {
            viewModel.selectPlant("cedar")
        }

        binding.plantPlumeria.setOnClickListener {
            viewModel.selectPlant("plumeria")
        }

        binding.plantChili.setOnClickListener {
            viewModel.selectPlant("chili")
        }

        binding.plantDusty.setOnClickListener {
            viewModel.selectPlant("dusty")
        }

        binding.cardBlockApps.setOnClickListener {
            findNavController().navigate(R.id.nav_blocking)
        }

        binding.cardMyPlants.setOnClickListener {
            findNavController().navigate(R.id.nav_world)
        }

        binding.tvViewAllPlants.setOnClickListener {
            findNavController().navigate(R.id.nav_world)
        }
    }

    private fun observeUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    binding.tvGreeting.text = "Good morning,\n${state.userName}"
                    binding.tvEnergyCount.text = "${state.focusEnergy}"
                    binding.tvCalmMinutesValue.text = "${state.calmMinutes} min"
                    binding.tvMyPlantsGrown.text = "${state.totalGrownPlants} grown"

                    // Update Pace Selection Cards
                    val steadyPace = state.feelingPaces.find { it.type == PaceType.STEADY }
                    val isSteadySelected = steadyPace?.isSelected == true
                    binding.cardPaceSteady.setBackgroundResource(
                        if (isSteadySelected) R.drawable.bg_card_selected else R.drawable.bg_card_white_unselected
                    )

                    val lowPace = state.feelingPaces.find { it.type == PaceType.LOW_ENERGY }
                    val isLowSelected = lowPace?.isSelected == true
                    binding.cardPaceLow.setBackgroundResource(
                        if (isLowSelected) R.drawable.bg_card_selected else R.drawable.bg_card_white_unselected
                    )

                    val brightPace = state.feelingPaces.find { it.type == PaceType.FEELING_BRIGHT }
                    val isBrightSelected = brightPace?.isSelected == true
                    binding.cardPaceBright.setBackgroundResource(
                        if (isBrightSelected) R.drawable.bg_card_selected else R.drawable.bg_card_white_unselected
                    )

                    // Update Plant Selection Cards
                    val selectedPlantId = state.selectedPlant?.id ?: "cedar"
                    binding.plantCedar.setBackgroundResource(
                        if (selectedPlantId == "cedar") R.drawable.bg_card_selected else R.drawable.bg_card_unselected
                    )
                    binding.plantPlumeria.setBackgroundResource(
                        if (selectedPlantId == "plumeria") R.drawable.bg_card_selected else R.drawable.bg_card_unselected
                    )
                    binding.plantChili.setBackgroundResource(
                        if (selectedPlantId == "chili") R.drawable.bg_card_selected else R.drawable.bg_card_unselected
                    )
                    binding.plantDusty.setBackgroundResource(
                        if (selectedPlantId == "dusty") R.drawable.bg_card_selected else R.drawable.bg_card_unselected
                    )

                    // Hero Card Subtitle & Title
                    state.selectedPlant?.let { plant ->
                        binding.tvHeroTitle.text = "Grow your ${plant.name}"
                        binding.tvHeroSub.text = "${state.calmMinutes} calm minutes • +${state.calmMinutes} energy"
                    }

                    // Update Weekly Chart
                    if (state.weeklyStats.isNotEmpty()) {
                        val ratios = state.weeklyStats.map { it.normalizedRatio }.toFloatArray()
                        binding.weeklyChartView.setWeeklyData(ratios)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
