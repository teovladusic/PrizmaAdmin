package com.prizma_distribucija.prizmaadmin.feature_verify_route.data.remote.dto

import com.google.firebase.firestore.GeoPoint

data class PathPointDto(
    val id: String = "",
    val index: Int = 0,
    val points: List<GeoPoint> = emptyList()
)