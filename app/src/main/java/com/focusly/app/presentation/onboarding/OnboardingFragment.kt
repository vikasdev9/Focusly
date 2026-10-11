package com.focusly.app.presentation.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.focusly.app.R
import com.focusly.app.databinding.FragmentOnboardingBinding
import com.focusly.app.utils.ViewModelFactory

class OnboardingFragment : Fragment() {

    private var _binding: FragmentOnboardingBinding? = null
    private val binding get() = _binding!!

    private val viewModel: OnboardingViewModel by viewModels {
        ViewModelFactory(requireContext().applicationContext)
    }

    private val pages = listOf(
        OnboardingPage(
            id = 0,
            circleBgRes = R.drawable.bg_onboarding_circle_green,
            iconRes = null,
            isPlantHero = true,
            tagRes = R.string.onboarding_1_tag,
            titleRes = R.string.onboarding_1_title,
            subRes = R.string.onboarding_1_sub,
            buttonTextRes = R.string.btn_continue
        ),
        OnboardingPage(
            id = 1,
            circleBgRes = R.drawable.bg_onboarding_circle_yellow,
            iconRes = R.drawable.ic_timer,
            isPlantHero = false,
            tagRes = R.string.onboarding_2_tag,
            titleRes = R.string.onboarding_2_title,
            subRes = R.string.onboarding_2_sub,
            buttonTextRes = R.string.btn_continue
        ),
        OnboardingPage(
            id = 2,
            circleBgRes = R.drawable.bg_onboarding_circle_peach,
            iconRes = R.drawable.ic_shield,
            isPlantHero = false,
            tagRes = R.string.onboarding_3_tag,
            titleRes = R.string.onboarding_3_title,
            subRes = R.string.onboarding_3_sub,
            buttonTextRes = R.string.btn_begin_focus
        )
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnboardingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = OnboardingAdapter(pages)
        binding.viewPagerOnboarding.adapter = adapter

        setupIndicators(0)

        binding.viewPagerOnboarding.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setupIndicators(position)
                binding.tvBtnText.setText(pages[position].buttonTextRes)
            }
        })

        binding.btnContinue.setOnClickListener {
            val current = binding.viewPagerOnboarding.currentItem
            if (current < pages.size - 1) {
                binding.viewPagerOnboarding.currentItem = current + 1
            } else {
                finishOnboarding()
            }
        }

        binding.btnSkip.setOnClickListener {
            finishOnboarding()
        }
    }

    private fun setupIndicators(activePosition: Int) {
        binding.layoutIndicators.removeAllViews()
        val density = resources.displayMetrics.density

        for (i in pages.indices) {
            val indicatorView = View(requireContext())
            val params = if (i == activePosition) {
                LinearLayout.LayoutParams(
                    (24 * density).toInt(),
                    (6 * density).toInt()
                ).apply {
                    setMargins(0, 0, (6 * density).toInt(), 0)
                }
            } else {
                LinearLayout.LayoutParams(
                    (6 * density).toInt(),
                    (6 * density).toInt()
                ).apply {
                    setMargins(0, 0, (6 * density).toInt(), 0)
                }
            }

            indicatorView.layoutParams = params

            val shapeDrawable = android.graphics.drawable.GradientDrawable().apply {
                shape = android.graphics.drawable.GradientDrawable.RECTANGLE
                cornerRadius = 3 * density
                setColor(
                    if (i == activePosition) ContextCompat.getColor(requireContext(), R.color.forest_dark)
                    else ContextCompat.getColor(requireContext(), R.color.light_muted_text)
                )
            }

            indicatorView.background = shapeDrawable
            binding.layoutIndicators.addView(indicatorView)
        }
    }

    private fun finishOnboarding() {
        viewModel.completeOnboarding {
            findNavController().navigate(R.id.action_onboarding_to_home)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
