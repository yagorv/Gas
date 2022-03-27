package com.yago.gas.ui.views.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.yago.gas.R
import com.yago.gas.databinding.FragmentDetailBinding
import com.yago.gas.ui.views.shared.base.BindingFragment
import javax.inject.Inject

class DetailFragment : BindingFragment<FragmentDetailBinding>(), OnMapReadyCallback {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var mainViewModel: MainViewModel

    private val args: DetailFragmentArgs by navArgs()

    private lateinit var googleMap: GoogleMap

    override fun createDataBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDetailBinding =
        DataBindingUtil.inflate(
            inflater, R.layout.fragment_detail, container,
            false, dataBindingComponent
        )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.let {
            mainViewModel = ViewModelProvider(it, viewModelFactory)[MainViewModel::class.java]
            mainViewModel.onViewCreated()
        }

        binding.apply {
            store = args.store
            progress.isVisible = false
        }

        initMapAsync()
        initializeViewObservers()
        initListeners()
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        args.store.location.apply {
            googleMap.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(latitude, longitude),
                    DEFAULT_ZOOM
                )
            )
        }
    }

    private fun initMapAsync() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun initListeners() {
        binding.storeLocationLink.setOnClickListener {
            val gMapStringLink = getString(
                R.string.google_maps_link,
                args.store.location.latitude.toString(),
                args.store.location.longitude.toString()
            )
            val gmmIntentUri =
                Uri.parse(gMapStringLink)
            Intent(Intent.ACTION_VIEW, gmmIntentUri).apply {
                setPackage(MAPS_PACKAGE)
                startActivity(this)
            }
        }
    }

    private fun initializeViewObservers() {
        mainViewModel.isLoading.observe(viewLifecycleOwner) {
            binding.progress.isVisible = it
        }
    }

    companion object {
        const val MAPS_PACKAGE = "com.google.android.apps.maps"
        const val DEFAULT_ZOOM = 14F
    }

}