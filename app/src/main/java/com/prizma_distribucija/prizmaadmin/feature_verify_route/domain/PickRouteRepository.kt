package com.prizma_distribucija.prizmaadmin.feature_verify_route.domain

import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.model.RepositoryResult
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.model.Route

interface PickRouteRepository {

    suspend fun getRouteByEmployeeIdAndMonth(id: String, month: Int) : RepositoryResult<List<Route>>
}