package com.prizma_distribucija.prizmaadmin.feature_verify_route.data.repository

import com.google.android.gms.maps.model.LatLng
import com.prizma_distribucija.prizmaadmin.core.domain.FirebaseService
import com.prizma_distribucija.prizmaadmin.core.util.Constants
import com.prizma_distribucija.prizmaadmin.core.util.DispatcherProvider
import com.prizma_distribucija.prizmaadmin.feature_verify_route.data.remote.dto.PathPointDto
import com.prizma_distribucija.prizmaadmin.feature_verify_route.data.remote.dto.RouteDto
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.PickRouteRepository
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.model.RepositoryResult
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.model.Route
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.model.RouteMapper
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PickRouteRepositoryImpl @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val firebaseService: FirebaseService,
) : PickRouteRepository {

    override suspend fun getRouteByEmployeeIdAndMonth(
        id: String,
        month: Int
    ): RepositoryResult<List<Route>> = withContext(dispatcherProvider.io) {
        val routesDto = firebaseService.getRoutesByEmployeeIdAndMonth(id, month)

        val isSuccessful = routesDto.isNotEmpty()

        val routes = routesDto.map {
            val pathPoints = firebaseService.getPathPointsFromIds(it.pathPointIds)
            it.getRoute(pathPoints)
        }

        val data = if (isSuccessful) routes else null

        val message = if (isSuccessful) null else Constants.ROUTES_EMPTY_ERROR

        return@withContext RepositoryResult(isSuccessful, data, message)
    }

    private fun RouteDto.getRoute(pathPoints: List<PathPointDto>): Route {
        val points = mutableListOf<LatLng>()

        pathPoints.sortedBy { it.index }.forEach {
            val pointsToAdd = it.points.map { LatLng(it.latitude, it.longitude) }
            points.addAll(pointsToAdd)
        }

        return Route(
            routeId,
            avgSpeed,
            day,
            distanceTravelled,
            month,
            points,
            timeFinished, timeStarted, userId, year, seen
        )
    }
}