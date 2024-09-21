package uz.futuresoft.searchfind.ui.screens.onboarding.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.futuresoft.searchfind.databinding.ViewPagerItemOnboardingBinding
import uz.futuresoft.searchfind.ui.screens.onboarding.models.OnboardingModel

class ViewPagerAdapterOnboarding(
    private val contents: List<OnboardingModel>,
) : RecyclerView.Adapter<ViewPagerAdapterOnboardingViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewPagerAdapterOnboardingViewHolder {
        val binding = ViewPagerItemOnboardingBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewPagerAdapterOnboardingViewHolder(binding = binding)
    }

    override fun onBindViewHolder(holder: ViewPagerAdapterOnboardingViewHolder, position: Int) {
        holder.bind(contents = contents, position = position)
    }

    override fun getItemCount(): Int = 3
}