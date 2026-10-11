package com.focusly.app

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.focusly.app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val windowInsetsController = WindowInsetsControllerCompat(window, window.decorView)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { _, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())

            val isBottomNavVisible = binding.bottomNavContainer.visibility == View.VISIBLE

            binding.main.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                if (isBottomNavVisible) 0 else systemBars.bottom
            )

            binding.bottomNavContainer.setPadding(
                0, 0, 0, systemBars.bottom
            )
            insets
        }

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController = navHostFragment.navController

        binding.bottomNavView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.nav_splash -> {
                    binding.bottomNavContainer.visibility = View.GONE
                    val forestDark = ContextCompat.getColor(this, R.color.forest_dark)
                    binding.main.setBackgroundColor(forestDark)
                    window.statusBarColor = forestDark
                    windowInsetsController.isAppearanceLightStatusBars = false
                }
                R.id.nav_onboarding -> {
                    binding.bottomNavContainer.visibility = View.GONE
                    val bgWarm = ContextCompat.getColor(this, R.color.bg_warm)
                    binding.main.setBackgroundColor(bgWarm)
                    window.statusBarColor = bgWarm
                    windowInsetsController.isAppearanceLightStatusBars = true
                }
                else -> {
                    binding.bottomNavContainer.visibility = View.VISIBLE
                    val bgWarm = ContextCompat.getColor(this, R.color.bg_warm)
                    binding.main.setBackgroundColor(bgWarm)
                    window.statusBarColor = bgWarm
                    windowInsetsController.isAppearanceLightStatusBars = true
                }
            }
            ViewCompat.requestApplyInsets(binding.main)
        }

        binding.btnCenterFocus.setOnClickListener {
            navController.navigate(R.id.nav_focus)
        }
    }
}
