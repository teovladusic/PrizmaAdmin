package com.prizma_distribucija.prizmaadmin.feature_verify_route.data.repository

import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.CheckRouteRepository

class CheckRouteRepositoryFakeImpl: CheckRouteRepository {

    override suspend fun setRouteSeen(routeId: String) {
        return
    }
}