package com.prizma_distribucija.prizmaadmin.core.data.services

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import com.prizma_distribucija.prizmaadmin.core.domain.FirebaseService
import com.prizma_distribucija.prizmaadmin.core.util.Constants
import com.prizma_distribucija.prizmaadmin.feature_verify_route.data.remote.dto.EmployeeDto
import com.prizma_distribucija.prizmaadmin.feature_verify_route.data.remote.dto.PathPointDto
import com.prizma_distribucija.prizmaadmin.feature_verify_route.data.remote.dto.RouteDto
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseServiceImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore
) : FirebaseService {

    override suspend fun getAllEmployees(): List<EmployeeDto> {
        return firebaseFirestore.collection(Constants.EMPLOYEES_COLLECTION)
            .get().await().toObjects(EmployeeDto::class.java)
    }

    override suspend fun getEmployeeById(id: String): EmployeeDto? {
        return firebaseFirestore.collection(Constants.EMPLOYEES_COLLECTION)
            .whereEqualTo("userId", id).get().await().toObjects(EmployeeDto::class.java)[0] ?: null
    }

    override suspend fun getRoutesByEmployeeIdAndMonth(id: String, month: Int): List<RouteDto> {
        return firebaseFirestore.collection(Constants.ROUTES_COLLECTION)
            .whereEqualTo("userId", id).whereEqualTo("month", month).get().await()
            .toObjects(RouteDto::class.java)
    }

    override suspend fun getUnseenRoutes(): List<RouteDto> {
        return firebaseFirestore.collection(Constants.ROUTES_COLLECTION)
            .whereEqualTo("seen", false).get().await()
            .toObjects(RouteDto::class.java)
    }

    override suspend fun getUnseenRoutes(userId: String): List<RouteDto> {
        return firebaseFirestore.collection(Constants.ROUTES_COLLECTION)
            .whereEqualTo("userId", userId).whereEqualTo("seen", false)
            .get().await().toObjects(RouteDto::class.java)
    }

    override suspend fun setRouteSeen(routeId: String) {
        firebaseFirestore.collection(Constants.ROUTES_COLLECTION).document(routeId)
            .update("seen", true)
    }

    override suspend fun getPathPointsFromIds(ids: List<String>): List<PathPointDto> {
        return firebaseFirestore.collection(Constants.PATH_POINTS_COLLECTION)
            .whereIn("id", ids)
            .get().await().toObjects(PathPointDto::class.java)
    }
}