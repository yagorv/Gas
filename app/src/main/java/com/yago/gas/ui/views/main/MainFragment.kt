package com.yago.gas.ui.views.main

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.yago.gas.R
import com.yago.gas.data.utils.Status
import com.yago.gas.databinding.FragmentMainBinding
import com.yago.gas.domain.customdata.Store
import com.yago.gas.domain.customdata.StoreCategory
import com.yago.gas.domain.utils.LocationUtils
import com.yago.gas.ui.views.main.adapter.categories.CategoriesAdapter
import com.yago.gas.ui.views.main.adapter.stores.StoresAdapter
import com.yago.gas.ui.views.shared.base.BindingFragment
import javax.inject.Inject

class MainFragment : BindingFragment<FragmentMainBinding>() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var mainViewModel: MainViewModel

    private lateinit var storesAdapter: StoresAdapter

    private lateinit var categoriesAdapter: CategoriesAdapter

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private lateinit var stores: List<Store>

    override fun createDataBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMainBinding =
        DataBindingUtil.inflate(
            inflater, R.layout.fragment_main, container,
            false, dataBindingComponent
        )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        manageLocationPermission()

        mainViewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]

        storesAdapter = StoresAdapter(activity)
        categoriesAdapter = CategoriesAdapter(activity)

        initializeViewObservers()
        initializeListeners()
        initializeBindings()
    }

    private fun initializeBindings() {
        binding.apply {
            progress.isVisible = true
            recycler.layoutManager = LinearLayoutManager(activity)
            recycler.adapter = storesAdapter

            recyclerCategories.layoutManager =
                LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            recyclerCategories.adapter = categoriesAdapter
        }
    }

    private fun initializeListeners() {
        storesAdapter.listener = object : StoresAdapter.Listener {
            override fun onclick(store: Store) {
                hideMainContent()
                val action = MainFragmentDirections.actionMainFragmentToDetailFragment(store)
                findNavController().navigate(action)
                storesAdapter.submitList(emptyList())
            }
        }

        categoriesAdapter.listener = object : CategoriesAdapter.Listener {
            override fun onclick(category: StoreCategory) {
                storesAdapter.submitList(stores.filter { it.category == category })
            }
        }
    }

    private fun manageLocationPermission() {
        activity?.let {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(it)

            if (ActivityCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                LocationUtils.requestLocationPermission(it, MainActivity.LOCATION_PERMISSION_CODE)
                return
            }
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                location?.let { loc ->
                    mainViewModel.onCreateMainScreen(loc)
                }
            }
        }
    }

    private fun initializeViewObservers() {
        mainViewModel.stores.observe(viewLifecycleOwner) { stores ->
            if (stores.status == Status.SUCCESS) {
                stores?.data?.let { storesList ->
                    this.stores = storesList
                    storesAdapter.submitList(storesList)
                    categoriesAdapter.submitList(storesList.map { it.category }.toSet().toList())
                    showMainContent(storesList)
                }
            } else if (stores.status == Status.LOADING) {
                binding.progress.isVisible = true
            }
        }
    }

    private fun hideMainContent() {
        binding.apply {
            progress.isVisible = true
            summary.isVisible = false
            recyclerCategories.isVisible = false
            recycler.isVisible = false
        }
    }

    private fun showMainContent(stores: List<Store>) {
        binding.apply {
            summary.isVisible = true
            recyclerCategories.isVisible = true
            progress.isVisible = false
            storesNumber.text = "${stores.size}"
            nearStores.text = "${stores.filter { it.distance < 1000 }.size}"
        }
    }

}