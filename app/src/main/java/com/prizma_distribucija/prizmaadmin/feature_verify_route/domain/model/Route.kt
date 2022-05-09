package com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.model

import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import kotlinx.parcelize.Parcelize

@Parcelize
data class Route(
    val routeId: String,
    val avgSpeed: String,
    val day: Int,
    val distanceTravelled: String,
    val month: Int,
    val pathPoints: List<LatLng>,
    val timeFinished: String,
    val timeStarted: String,
    val userId: String,
    val year: Int,
    var seen: Boolean
) : Parcelable