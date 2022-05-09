package com.prizma_distribucija.prizmaadmin.feature_verify_route.data.repository

import com.google.android.gms.maps.model.LatLng
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.PickRouteRepository
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.model.RepositoryResult
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.model.Route
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.delay

class PickRouteRepositoryFakeImpl : PickRouteRepository {

    companion object {
        var isSuccessful = false
        var delayTime = 0L
    }

    override suspend fun getRouteByEmployeeIdAndMonth(
        id: String,
        month: Int
    ): RepositoryResult<List<Route>> {
        delay(delayTime)

        val route =
            Route(
                "",
                "0.0 km/h",
                1,
                "0.00 km",
                1,
                listOf(LatLng(1.0, 1.0)),
                "15:22",
                "15:22",
                "userId",
                2022,
                false
            )

        val data = if (isSuccessful) listOf(route) else null
        val errorMessage = if (isSuccessful) null else "error message"

        return RepositoryResult(isSuccessful, data, errorMessage)
    }
}