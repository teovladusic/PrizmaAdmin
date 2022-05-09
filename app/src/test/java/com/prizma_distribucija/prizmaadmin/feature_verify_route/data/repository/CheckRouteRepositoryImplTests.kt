package com.prizma_distribucija.prizmaadmin.feature_verify_route.data.repository

import com.prizma_distribucija.prizmaadmin.core.domain.FirebaseService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify

@ExperimentalCoroutinesApi
class CheckRouteRepositoryImplTests {

    @Test
    fun `setRouteSeen, should call firebaseService with correct id`() = runTest {
        val firebaseServiceMock = mock(FirebaseService::class.java)

        val repository = CheckRouteRepositoryImpl(firebaseServiceMock)

        val routeId = "random route id"
        repository.setRouteSeen(routeId)

        verify(firebaseServiceMock).setRouteSeen(routeId)
    }
}