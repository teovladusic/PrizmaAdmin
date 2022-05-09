package com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.use_cases

import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.CheckRouteRepository
import javax.inject.Inject

class SetRouteSeenUseCase @Inject constructor(
    private val checkRouteRepository: CheckRouteRepository
) {

    suspend operator fun invoke(routeId: String) {
        checkRouteRepository.setRouteSeen(routeId)
    }
}