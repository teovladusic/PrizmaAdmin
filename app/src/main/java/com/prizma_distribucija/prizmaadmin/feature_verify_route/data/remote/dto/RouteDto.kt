package com.prizma_distribucija.prizmaadmin.feature_verify_route.data.remote.dto

import com.google.firebase.firestore.GeoPoint

data class RouteDto(
    val routeId: String,
    val avgSpeed: String,
    val day: Int,
    val distanceTravelled: String,
    val month: Int,
    val pathPoints: List<GeoPoint>,
    val timeFinished: String,
    val timeStarted: String,
    val userId: String,
    val year: Int,
    val seen: Boolean
) {
    constructor() : this(
        "id",
        "0.0 km/h",
        1,
        "0.00 km",
        1,
        emptyList(),
        "15:22",
        "15:22",
        "userId",
        2022,
        false
    )
}
