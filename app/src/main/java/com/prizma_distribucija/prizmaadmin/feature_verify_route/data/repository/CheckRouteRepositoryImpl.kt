package com.prizma_distribucija.prizmaadmin.feature_verify_route.data.repository

import com.google.firebase.firestore.GeoPoint
import com.prizma_distribucija.prizmaadmin.core.domain.FirebaseService
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.CheckRouteRepository
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.model.RouteMapper
import javax.inject.Inject

class CheckRouteRepositoryImpl @Inject constructor(
    private val firebaseService: FirebaseService
) : CheckRouteRepository {

    override suspend fun setRouteSeen(routeId: String) {
        firebaseService.setRouteSeen(routeId)
    }
}