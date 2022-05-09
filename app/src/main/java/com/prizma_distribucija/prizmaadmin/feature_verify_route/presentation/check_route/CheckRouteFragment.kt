package com.prizma_distribucija.prizmaadmin.feature_verify_route.presentation.check_route

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.transition.Transition
import android.transition.Transition.TransitionListener
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.transition.Slide
import androidx.transition.TransitionListenerAdapter
import androidx.transition.TransitionManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.prizma_distribucija.prizmaadmin.R
import com.prizma_distribucija.prizmaadmin.core.util.Constants
import com.prizma_distribucija.prizmaadmin.databinding.FragmentCheckRouteBinding
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.model.Employee
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.model.Route
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CheckRouteFragment : Fragment(R.layout.fragment_check_route) {

    private var _binding: FragmentCheckRouteBinding? = null
    private val binding get() = _binding!!

    private val polylineOptions = PolylineOptions()
        .color(Constants.POLYLINE_COLOR)
        .width(Constants.POLYLINE_WIDTH)

    private val markerOptions = MarkerOptions().visible(true).anchor(0.5f, 0.5f)

    private val viewModel: CheckRouteViewModel by viewModels()

    private var map: GoogleMap? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCheckRouteBinding.bind(view)


        viewModel.employee?.let {
            setEmployeeUi(it)
        }

        viewModel.route?.let {
            setRouteUi(it)
            setUpGoogleMapAndDrawPolyLinesBetweenPathPoints(it.pathPoints)
        }

        binding.cardViewStats.setOnClickListener {
            viewModel.changeStatsVisibility()
        }

        binding.constraintLayoutStats.setOnClickListener {
            viewModel.changeStatsVisibility()
        }

        binding.constraintLayoutSwiper.setOnClickListener {
            viewModel.changeStatsVisibility()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.areStatsVisible.collect {
                    changeStatsVisibility(it)
                }
            }
        }
    }


    private fun changeStatsVisibility(isVisible: Boolean) {
        val visibility =
            if (isVisible) View.VISIBLE else View.GONE

        binding.constraintLayoutStats.visibility = visibility
    }

    @SuppressLint("SetTextI18n")
    private fun setEmployeeUi(employee: Employee) {
        binding.apply {
            tvFolderTitle.text = "${employee.name} ${employee.lastName}"
        }
    }

    private fun setUpGoogleMapAndDrawPolyLinesBetweenPathPoints(pathPoints: List<LatLng>) {
        val supportMapFragment =
            childFragmentManager.findFragmentById(R.id.google_map) as SupportMapFragment?
        supportMapFragment?.getMapAsync { googleMap ->
            map = googleMap
            map?.isIndoorEnabled = true
            map?.isBuildingsEnabled = true
            drawPolyLinesBetweenAllPathPoints(pathPoints)
        }
    }

    private fun drawPolyLinesBetweenAllPathPoints(pathPoints: List<LatLng>) {
        if (pathPoints.isEmpty()) return
        map?.addPolyline(polylineOptions.addAll(pathPoints))

        val position = CameraPosition.Builder()
            .target(pathPoints.first())
            .zoom(13f)

        map?.animateCamera(CameraUpdateFactory.newCameraPosition(position.build()))

        addStartMarker(pathPoints.first())
        addEndMarker(pathPoints.last())
    }

    private fun addStartMarker(position: LatLng) {
        map?.addMarker(markerOptions.position(position).title("START"))
    }

    private fun addEndMarker(position: LatLng) {
        map?.addMarker(
            markerOptions
                .position(position)
                .title("END")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
        )
    }

    @SuppressLint("SetTextI18n")
    private fun setRouteUi(route: Route) {
        binding.apply {
            tvDistance.text = route.distanceTravelled
            tvAvgSpeed.text = route.avgSpeed
            tvWorkTime.text = "${route.timeStarted} - ${route.timeFinished}"
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}