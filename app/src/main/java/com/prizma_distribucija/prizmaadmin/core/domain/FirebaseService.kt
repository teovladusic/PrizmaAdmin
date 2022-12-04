package com.prizma_distribucija.prizmaadmin.core.domain

import com.google.firebase.firestore.GeoPoint
import com.prizma_distribucija.prizmaadmin.feature_verify_route.data.remote.dto.EmployeeDto
import com.prizma_distribucija.prizmaadmin.feature_verify_route.data.remote.dto.PathPointDto
import com.prizma_distribucija.prizmaadmin.feature_verify_route.data.remote.dto.RouteDto

interface FirebaseService {

    suspend fun getAllEmployees(): List<EmployeeDto>

    suspend fun getEmployeeById(id: String): EmployeeDto?

    suspend fun getRoutesByEmployeeIdAndMonth(id: String, month: Int): List<RouteDto>

    suspend fun getUnseenRoutes(): List<RouteDto>

    suspend fun getUnseenRoutes(userId: String): List<RouteDto>

    suspend fun setRouteSeen(routeId: String)

    suspend fun getPathPointsFromIds(ids: List<String>): List<PathPointDto>
}