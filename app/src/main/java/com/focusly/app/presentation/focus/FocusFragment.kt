package com.focusly.app.presentation.focus

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.focusly.app.R
import com.focusly.app.databinding.BottomSheetAmbientSoundsBinding
import com.focusly.app.databinding.BottomSheetSessionGoalsBinding
import com.focusly.app.databinding.DialogFocusDurationBinding
import com.focusly.app.databinding.FragmentFocusBinding
import com.focusly.app.utils.ViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.launch

class FocusFragment : Fragment() {

    private var _binding: FragmentFocusBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FocusViewModel by viewModels {
        ViewModelFactory(requireContext().applicationContext)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFocusBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
        observeUiState()
    }

    private fun setupListeners() {
        binding.btnPlayCenter.setOnClickListener {
            viewModel.startTimer()
        }

        binding.btnPauseResume.setOnClickListener {
            viewModel.togglePlayPause()
        }

        binding.btnDiscard.setOnClickListener {
            viewModel.pauseTimer()
            findNavController().popBackStack()
        }

        binding.btnFinishActive.setOnClickListener {
            viewModel.finishSession()
            findNavController().popBackStack()
        }

        binding.btnSoundToggle.setOnClickListener {
            showAmbientSoundsBottomSheet()
        }

        binding.cardSoundTag.setOnClickListener {
            showAmbientSoundsBottomSheet()
        }

        binding.btnSetsToggle.setOnClickListener {
            showSessionGoalsBottomSheet()
        }

        binding.cardFocusDuration.setOnClickListener {
            showFocusDurationDialog()
        }

        binding.cardBreakDuration.setOnClickListener {
            showBreakDurationDialog()
        }
    }

    private fun observeUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    val min = state.remainingSeconds / 60
                    val sec = state.remainingSeconds % 60
                    binding.tvTimerDisplay.text = String.format("%02d:%02d", min, sec)

                    val progressRatio = if (state.totalSeconds > 0) {
                        state.remainingSeconds.toFloat() / state.totalSeconds.toFloat()
                    } else 1f
                    binding.progressRing.setProgress(progressRatio)

                    binding.tvFocusDurationValue.text = "${state.focusMinutes}"
                    binding.tvBreakDurationValue.text = "${state.breakMinutes}"
                    binding.tvActivityName.text = state.activityName

                    val isActive = state.isRunning || state.isPaused

                    if (isActive) {
                        // ACTIVE / RUNNING / PAUSED VIEW STATE
                        binding.layoutSetupCards.visibility = View.GONE
                        binding.layoutSetupActions.visibility = View.GONE

                        binding.cardActiveSessionProgress.visibility = View.VISIBLE
                        binding.layoutActiveActions.visibility = View.VISIBLE

                        binding.tvIdlePrompt.visibility = View.GONE
                        binding.layoutTimerDisplay.visibility = View.VISIBLE

                        binding.tvStatusBadge.text = if (state.isRunning) "● Active" else "● Paused"
                        binding.tvStatusBadge.setTextColor(requireContext().getColor(R.color.primary_green))
                        binding.cardStatusTag.setBackgroundResource(R.drawable.bg_card_selected)

                        binding.imgActivePauseIcon.setImageResource(
                            if (state.isRunning) R.drawable.ic_pause else R.drawable.ic_play_arrow
                        )
                        binding.tvActivePauseLabel.text = if (state.isRunning) "Pause" else "Resume"

                    } else {
                        // IDLE / SETUP VIEW STATE
                        binding.layoutSetupCards.visibility = View.VISIBLE
                        binding.layoutSetupActions.visibility = View.VISIBLE

                        binding.cardActiveSessionProgress.visibility = View.GONE
                        binding.layoutActiveActions.visibility = View.GONE

                        binding.tvIdlePrompt.visibility = View.VISIBLE
                        binding.layoutTimerDisplay.visibility = View.GONE

                        binding.tvStatusBadge.text = "● Idle"
                        binding.tvStatusBadge.setTextColor(requireContext().getColor(R.color.muted_text))
                        binding.cardStatusTag.setBackgroundResource(R.drawable.bg_card_white_unselected)
                    }

                    if (state.isCompleted) {
                        findNavController().popBackStack()
                    }
                }
            }
        }
    }

    private fun showAmbientSoundsBottomSheet() {
        val bottomSheet = BottomSheetDialog(requireContext())
        val sheetBinding = BottomSheetAmbientSoundsBinding.inflate(layoutInflater)
        bottomSheet.setContentView(sheetBinding.root)

        var selectedSound = "Library"

        fun updateSelectedSound(sound: String) {
            selectedSound = sound
            sheetBinding.soundForest.setBackgroundResource(if (sound == "Forest") R.drawable.bg_card_selected else R.drawable.bg_card_unselected)
            sheetBinding.soundRain.setBackgroundResource(if (sound == "Rain") R.drawable.bg_card_selected else R.drawable.bg_card_unselected)
            sheetBinding.soundNoise.setBackgroundResource(if (sound == "Brown Noise") R.drawable.bg_card_selected else R.drawable.bg_card_unselected)
            sheetBinding.soundFire.setBackgroundResource(if (sound == "Fireplace") R.drawable.bg_card_selected else R.drawable.bg_card_unselected)
            sheetBinding.soundLibrary.setBackgroundResource(if (sound == "Library") R.drawable.bg_card_selected else R.drawable.bg_card_unselected)
            sheetBinding.soundRiver.setBackgroundResource(if (sound == "Riverside") R.drawable.bg_card_selected else R.drawable.bg_card_unselected)
        }

        sheetBinding.soundForest.setOnClickListener { updateSelectedSound("Forest") }
        sheetBinding.soundRain.setOnClickListener { updateSelectedSound("Rain") }
        sheetBinding.soundNoise.setOnClickListener { updateSelectedSound("Brown Noise") }
        sheetBinding.soundFire.setOnClickListener { updateSelectedSound("Fireplace") }
        sheetBinding.soundLibrary.setOnClickListener { updateSelectedSound("Library") }
        sheetBinding.soundRiver.setOnClickListener { updateSelectedSound("Riverside") }

        sheetBinding.btnSaveSounds.setOnClickListener {
            binding.tvSoundBadge.text = "🔊 $selectedSound"
            bottomSheet.dismiss()
        }

        bottomSheet.show()
    }

    private fun showSessionGoalsBottomSheet() {
        val bottomSheet = BottomSheetDialog(requireContext())
        val sheetBinding = BottomSheetSessionGoalsBinding.inflate(layoutInflater)
        bottomSheet.setContentView(sheetBinding.root)

        var sets = 4
        var longBreakMin = 30
        var interval = 3

        sheetBinding.seekBarTargetSets.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                sets = progress + 1
                sheetBinding.tvTargetSetsValue.text = "$sets"
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        sheetBinding.seekBarLongBreakDuration.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                longBreakMin = progress + 5
                sheetBinding.tvLongBreakDurationValue.text = "${longBreakMin}m"
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        sheetBinding.btnMinusInterval.setOnClickListener {
            if (interval > 1) {
                interval--
                sheetBinding.tvIntervalValue.text = "every $interval sets"
                sheetBinding.tvIntervalDesc.text = "every $interval sets"
            }
        }

        sheetBinding.btnPlusInterval.setOnClickListener {
            if (interval < 10) {
                interval++
                sheetBinding.tvIntervalValue.text = "every $interval sets"
                sheetBinding.tvIntervalDesc.text = "every $interval sets"
            }
        }

        sheetBinding.btnSaveGoals.setOnClickListener {
            binding.tvActiveLongBreakBadge.text = "⏰ ${longBreakMin}m Long Break"
            binding.tvCurrentSetFraction.text = "1/$sets"
            binding.tvSetsToGo.text = "$sets sets to go"
            bottomSheet.dismiss()
        }

        bottomSheet.show()
    }

    private fun showFocusDurationDialog() {
        val dialog = Dialog(requireContext())
        val dialogBinding = DialogFocusDurationBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val currentFocusMin = viewModel.uiState.value.focusMinutes
        dialogBinding.seekBarDuration.progress = (currentFocusMin - 5).coerceIn(0, 115)
        dialogBinding.tvDurationDisplay.text = "$currentFocusMin min"

        dialogBinding.seekBarDuration.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val selectedMin = progress + 5
                dialogBinding.tvDurationDisplay.text = "$selectedMin min"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        dialogBinding.btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialogBinding.btnSetTime.setOnClickListener {
            val chosenMin = dialogBinding.seekBarDuration.progress + 5
            viewModel.setFocusMinutes(chosenMin)
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun showBreakDurationDialog() {
        val dialog = Dialog(requireContext())
        val dialogBinding = DialogFocusDurationBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialogBinding.seekBarDuration.max = 25
        val currentBreakMin = viewModel.uiState.value.breakMinutes
        dialogBinding.seekBarDuration.progress = (currentBreakMin - 5).coerceIn(0, 25)
        dialogBinding.tvDurationDisplay.text = "$currentBreakMin min"

        dialogBinding.seekBarDuration.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val selectedMin = progress + 5
                dialogBinding.tvDurationDisplay.text = "$selectedMin min"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        dialogBinding.btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialogBinding.btnSetTime.setOnClickListener {
            val chosenMin = dialogBinding.seekBarDuration.progress + 5
            viewModel.setBreakMinutes(chosenMin)
            dialog.dismiss()
        }

        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
