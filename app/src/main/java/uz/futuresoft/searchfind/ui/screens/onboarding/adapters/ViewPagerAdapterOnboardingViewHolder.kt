package uz.futuresoft.searchfind.ui.screens.onboarding.adapters

import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.futuresoft.searchfind.R
import uz.futuresoft.searchfind.databinding.ViewPagerItemOnboardingBinding
import uz.futuresoft.searchfind.ui.screens.onboarding.models.OnboardingModel

class ViewPagerAdapterOnboardingViewHolder(
    private val binding: ViewPagerItemOnboardingBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(contents: List<OnboardingModel>, position: Int) {
        val content = contents[position]
        binding.picture.loadImage(picture = content.picture)
        binding.text.text = content.title
    }

    private fun ImageView.loadImage(picture: Int) {
        Glide.with(binding.root.context).load(picture).into(this)
    }
}