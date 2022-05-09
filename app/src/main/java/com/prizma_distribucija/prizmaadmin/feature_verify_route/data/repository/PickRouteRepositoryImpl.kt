package com.prizma_distribucija.prizmaadmin.feature_verify_route.data.repository

import com.prizma_distribucija.prizmaadmin.core.domain.FirebaseService
import com.prizma_distribucija.prizmaadmin.core.util.Constants
import com.prizma_distribucija.prizmaadmin.core.util.DispatcherProvider
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.PickRouteRepository
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.model.RepositoryResult
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.model.Route
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.model.RouteMapper
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PickRouteRepositoryImpl @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val firebaseService: FirebaseService,
    private val routeMapper: RouteMapper
) : PickRouteRepository {

    override suspend fun getRouteByEmployeeIdAndMonth(
        id: String,
        month: Int
    ): RepositoryResult<List<Route>> = withContext(dispatcherProvider.io) {
        val routes = firebaseService.getRoutesByEmployeeIdAndMonth(id, month)

        val isSuccessful = routes.isNotEmpty()

        val data = if (isSuccessful) routes.map { routeMapper.mapFromDto(it) } else null

        val message = if (isSuccessful) null else Constants.ROUTES_EMPTY_ERROR

        return@withContext RepositoryResult(isSuccessful, data, message)
    }
}