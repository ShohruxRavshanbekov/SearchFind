package uz.futuresoft.searchfind.ui.activities

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint
import uz.futuresoft.searchfind.R
import uz.futuresoft.searchfind.databinding.ActivityMainBinding
import uz.futuresoft.searchfind.utils.Constants
import uz.futuresoft.searchfind.utils.Constants.TAG
import uz.futuresoft.searchfind.utils.LocaleManager
import java.util.Locale

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val viewModel: MainActivityViewModel by viewModels()

    private val navHostFragment: NavHostFragment by lazy { supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment }
    private val navController: NavController by lazy { navHostFragment.navController }
    private val navGraph: NavGraph by lazy { navController.navInflater.inflate(R.navigation.nav_app) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (viewModel.firstLaunch())
            navGraph.setStartDestination(R.id.mainScreen)
        else
            navGraph.setStartDestination(R.id.onboardingScreen)

        navHostFragment.navController.graph = navGraph
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LocaleManager.setLocale(context = newBase!!))
    }
}