package com.prizma_distribucija.prizmaadmin.feature_verify_route.domain

import com.google.firebase.firestore.GeoPoint

interface CheckRouteRepository {

    suspend fun setRouteSeen(routeId: String)
}