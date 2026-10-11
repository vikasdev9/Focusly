package com.focusly.app.presentation.onboarding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.focusly.app.databinding.ItemOnboardingPageBinding

class OnboardingAdapter(
    private val pages: List<OnboardingPage>
) : RecyclerView.Adapter<OnboardingAdapter.PageViewHolder>() {

    inner class PageViewHolder(val binding: ItemOnboardingPageBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageViewHolder {
        val binding = ItemOnboardingPageBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PageViewHolder, position: Int) {
        val page = pages[position]
        with(holder.binding) {
            viewCircleBg.setBackgroundResource(page.circleBgRes)

            if (page.isPlantHero) {
                imgPlantHero.visibility = View.VISIBLE
                frameInnerBadge.visibility = View.GONE
            } else {
                imgPlantHero.visibility = View.GONE
                frameInnerBadge.visibility = View.VISIBLE
                page.iconRes?.let { imgBadgeIcon.setImageResource(it) }
            }

            tvOnboardingTag.setText(page.tagRes)
            tvOnboardingTitle.setText(page.titleRes)
            tvOnboardingSub.setText(page.subRes)
        }
    }

    override fun getItemCount(): Int = pages.size
}
