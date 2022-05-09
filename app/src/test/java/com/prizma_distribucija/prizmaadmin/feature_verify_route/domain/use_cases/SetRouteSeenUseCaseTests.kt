package com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.use_cases

import com.prizma_distribucija.prizmaadmin.feature_verify_route.data.repository.CheckRouteRepositoryImpl
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.CheckRouteRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify

@ExperimentalCoroutinesApi
class SetRouteSeenUseCaseTests {

    @Test
    fun `invoke setRouteSeenUseCase, should call repository setRouteSeen with correct id`() =
        runTest {
            val checkRouteRepositoryMock = mock(CheckRouteRepository::class.java)

            val useCase = SetRouteSeenUseCase(checkRouteRepositoryMock)

            val routeId = "random route id"

            useCase(routeId)

            verify(checkRouteRepositoryMock).setRouteSeen(routeId)
        }
}