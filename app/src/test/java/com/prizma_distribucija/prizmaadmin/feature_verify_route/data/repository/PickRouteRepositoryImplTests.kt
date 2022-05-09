package com.prizma_distribucija.prizmaadmin.feature_verify_route.data.repository

import com.google.android.gms.maps.model.LatLng
import com.google.common.truth.Truth
import com.google.firebase.firestore.GeoPoint
import com.prizma_distribucija.prizmaadmin.core.domain.FirebaseService
import com.prizma_distribucija.prizmaadmin.core.util.Constants
import com.prizma_distribucija.prizmaadmin.core.util.TestDispatchers
import com.prizma_distribucija.prizmaadmin.feature_verify_route.data.remote.dto.RouteDto
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.model.EmployeeMapper
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.model.Route
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.model.RouteMapper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito

@ExperimentalCoroutinesApi
class PickRouteRepositoryImplTests {

    @Test
    fun `getRoutes, routes are empty, should return repositoryResult with correct data`() =
        runTest {
            val dispatcherProvider = TestDispatchers()
            val firebaseServiceMock = Mockito.mock(FirebaseService::class.java)
            Mockito.`when`(firebaseServiceMock.getRoutesByEmployeeIdAndMonth("id", 1)).thenReturn(
                emptyList()
            )
            val mapper = RouteMapper()
            val repository =
                PickRouteRepositoryImpl(dispatcherProvider, firebaseServiceMock, mapper)

            val result = repository.getRouteByEmployeeIdAndMonth("id", 1)

            Truth.assertThat(result.isSuccess).isFalse()
            Truth.assertThat(result.data).isNull()
            Truth.assertThat(result.errorMessage).isEqualTo(Constants.ROUTES_EMPTY_ERROR)
        }

    @Test
    fun `getRoutes, routes are not empty, should return repositoryResult with correct data`() =
        runTest {
            val pathPoints = listOf(GeoPoint(1.0, 1.0))
            val route =
                RouteDto (
                    "",
                    "0.0 km/h",
                    1,
                    "0.00 km",
                    1,
                    pathPoints,
                    "15:22",
                    "15:22",
                    "userId",
                    2022,
                    false
                )
            val dispatcherProvider = TestDispatchers()
            val firebaseServiceMock = Mockito.mock(FirebaseService::class.java)
            Mockito.`when`(firebaseServiceMock.getRoutesByEmployeeIdAndMonth("id", 1)).thenReturn(
                listOf(route)
            )
            val mapper = RouteMapper()
            val repository =
                PickRouteRepositoryImpl(dispatcherProvider, firebaseServiceMock, mapper)

            val result = repository.getRouteByEmployeeIdAndMonth("id", 1)

            Truth.assertThat(result.isSuccess).isTrue()
            Truth.assertThat(result.data).isNotNull()
            Truth.assertThat(result.errorMessage).isNull()
        }

}