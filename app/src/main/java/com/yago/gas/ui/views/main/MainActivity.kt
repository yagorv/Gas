package com.yago.gas.ui.views.main

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.yago.gas.R
import com.yago.gas.databinding.ActivityMainBinding
import com.yago.gas.domain.utils.LocationUtils
import com.yago.gas.ui.views.shared.base.BindingActivity
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class MainActivity : BindingActivity<ActivityMainBinding>(), HasSupportFragmentInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var mainViewModel: MainViewModel

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun supportFragmentInjector() = dispatchingAndroidInjector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]

        checkPermissions()

        setSupportActionBar(binding.toolbar)

        mainViewModel.onViewCreated()

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    private fun checkPermissions() {
        if (LocationUtils.isLocationEnabled(this)) {
            LocationUtils.requestLocationPermission(this, LOCATION_PERMISSION_CODE)
        } else {
            showLocationDisabledDialog()
        }
    }

    private fun showLocationDisabledDialog() {

        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.allow_location_title))
        builder.setMessage(getString(R.string.allow_location_description))

        builder.setPositiveButton(R.string.yes) { _, _ ->
            val gpsOptionsIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivity(gpsOptionsIntent)
        }
        builder.setNegativeButton(R.string.no) { _, _ ->
            finishAndRemoveTask()
        }
        builder.show()
    }

    override fun onSupportNavigateUp(): Boolean {
        mainViewModel.onNavigationBackStack()
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.popBackStack()
    }

    override fun createDataBinding(): ActivityMainBinding =
        DataBindingUtil.setContentView(this, R.layout.activity_main)

    companion object {
        const val LOCATION_PERMISSION_CODE = 1000
    }

}