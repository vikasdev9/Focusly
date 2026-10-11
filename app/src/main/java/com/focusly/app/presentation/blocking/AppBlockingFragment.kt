package com.focusly.app.presentation.blocking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.focusly.app.databinding.FragmentAppBlockingBinding
import com.focusly.app.utils.PermissionManager
import com.focusly.app.utils.ViewModelFactory

class AppBlockingFragment : Fragment() {

    private var _binding: FragmentAppBlockingBinding? = null
    private val binding get() = _binding!!

    private val viewModel: BlockingViewModel by viewModels {
        ViewModelFactory(requireContext().applicationContext)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAppBlockingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val permManager = PermissionManager(requireContext())
        if (permManager.hasUsageStatsPermission()) {
            binding.cardPermissionBanner.visibility = View.GONE
        } else {
            binding.cardPermissionBanner.visibility = View.VISIBLE
            binding.btnGrantUsage.setOnClickListener {
                permManager.openUsageStatsSettings()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
