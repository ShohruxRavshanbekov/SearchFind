package uz.futuresoft.searchfind.ui.screens.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import dagger.hilt.android.AndroidEntryPoint
import uz.futuresoft.searchfind.R
import uz.futuresoft.searchfind.databinding.ScreenOnboardingBinding
import uz.futuresoft.searchfind.ui.screens.onboarding.adapters.ViewPagerAdapterOnboarding
import uz.futuresoft.searchfind.ui.screens.onboarding.models.OnboardingModel

@AndroidEntryPoint
class OnboardingScreen : Fragment() {

    private val binding by lazy { ScreenOnboardingBinding.inflate(layoutInflater) }

    private val viewModel: OnboardingScreenViewModel by viewModels()

    private val viewPagerAdapter by lazy {
        ViewPagerAdapterOnboarding(
            contents = listOf(
                OnboardingModel(
                    picture = R.drawable.onboarding1,
                    title = getString(R.string.onboarding1)
                ),
                OnboardingModel(
                    picture = R.drawable.onboarding2,
                    title = getString(R.string.onboarding2)
                ),
                OnboardingModel(
                    picture = R.drawable.onboarding3,
                    title = getString(R.string.onboarding3)
                ),
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // disabling swipe
        binding.viewPagerOnboarding.isUserInputEnabled = false
        binding.viewPagerOnboarding.adapter = viewPagerAdapter
        binding.indicator.setViewPager(binding.viewPagerOnboarding)

        binding.viewPagerOnboarding.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                if (position == 2)
                    binding.next.text = getString(R.string.continue_)
                else
                    binding.next.text = getString(R.string.next)
            }
        })

        binding.next.setOnClickListener {
            var currentItem = binding.viewPagerOnboarding.currentItem

            if (currentItem != 2) {
                currentItem++
                binding.viewPagerOnboarding.currentItem = currentItem
            } else {
                findNavController().navigate(R.id.action_onboardingScreen_to_mainScreen)
                viewModel.setLaunchState(state = true)
            }
        }
    }
}