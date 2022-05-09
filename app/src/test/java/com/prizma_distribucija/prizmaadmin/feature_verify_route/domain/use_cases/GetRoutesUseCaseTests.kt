package com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.use_cases

import app.cash.turbine.test
import com.google.android.gms.maps.model.LatLng
import com.google.common.truth.Truth.assertThat
import com.prizma_distribucija.prizmaadmin.core.util.Resource
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.PickRouteRepository
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.model.RepositoryResult
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.model.Route
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

@ExperimentalCoroutinesApi
class GetRoutesUseCaseTests {

    @Test
    fun `getRoutes, repository returns error, should emit error`() = runTest {
        val repository = mock(PickRouteRepository::class.java)
        val id = "id"
        val month = 1
        `when`(
            repository.getRouteByEmployeeIdAndMonth(
                id,
                month
            )
        ).thenReturn(RepositoryResult(false, null, "error message"))

        val useCase = GetRoutesUseCase(repository)

        useCase(id, month).test {
            val firstEmission = awaitItem()
            val secondEmission = awaitItem()

            assertThat(firstEmission).isInstanceOf(Resource.Loading::class.java)
            assertThat(secondEmission).isInstanceOf(Resource.Error::class.java)
            assertThat(secondEmission.data).isNull()
            assertThat(secondEmission.message).isEqualTo("error message")

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getRoutes, repository returns success, should emit success with correct data`() = runTest {
        val repository = mock(PickRouteRepository::class.java)
        val id = "id"
        val month = 1
        val data =
            listOf(
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
            )
        `when`(
            repository.getRouteByEmployeeIdAndMonth(
                id,
                month
            )
        ).thenReturn(RepositoryResult(true, data, null))

        val useCase = GetRoutesUseCase(repository)

        useCase(id, month).test {
            val firstEmission = awaitItem()
            val secondEmission = awaitItem()

            assertThat(firstEmission).isInstanceOf(Resource.Loading::class.java)
            assertThat(secondEmission).isInstanceOf(Resource.Success::class.java)
            assertThat(secondEmission.message).isNull()
            assertThat(secondEmission.data).isEqualTo(data)

            cancelAndIgnoreRemainingEvents()
        }
    }

}