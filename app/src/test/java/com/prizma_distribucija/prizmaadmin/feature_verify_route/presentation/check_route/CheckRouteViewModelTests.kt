package com.prizma_distribucija.prizmaadmin.feature_verify_route.presentation.check_route

import androidx.lifecycle.SavedStateHandle
import com.google.common.truth.Truth.assertThat
import com.prizma_distribucija.prizmaadmin.core.util.TestDispatchers
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.CheckRouteRepository
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.model.Employee
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.model.Route
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.use_cases.SetRouteSeenUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify

@ExperimentalCoroutinesApi
class CheckRouteViewModelTests {

    @Test
    fun `changeStatsVisibility, should emit correct value`() = runTest {
        val dispatcherProvider = TestDispatchers()

        val savedStateHandle = SavedStateHandle()
        val employee = Employee("1234", "lastName", "name", "id")
        val route = Route("id", "", 1, "", 1, emptyList(), "", "", "id", 2022, false)
        savedStateHandle.set("employee", employee)
        savedStateHandle.set("route", route)

        val checkRouteRepositoryMock = mock(CheckRouteRepository::class.java)
        val setRouteSeenUseCase = SetRouteSeenUseCase(checkRouteRepositoryMock)

        val viewModel =
            CheckRouteViewModel(dispatcherProvider, savedStateHandle, setRouteSeenUseCase)

        assertThat(viewModel.areStatsVisible.value).isFalse()

        viewModel.changeStatsVisibility()

        assertThat(viewModel.areStatsVisible.value).isTrue()
    }

    @Test
    fun `on viewModel init, should set route seen property to true`() = runTest {
        val dispatcherProvider = TestDispatchers()

        val savedStateHandle = SavedStateHandle()
        val employee = Employee("1234", "lastName", "name", "id")
        val route = Route("id", "", 1, "", 1, emptyList(), "", "", "id", 2022, false)
        savedStateHandle.set("employee", employee)
        savedStateHandle.set("route", route)

        val checkRouteRepositoryMock = mock(CheckRouteRepository::class.java)
        val setRouteSeenUseCase = SetRouteSeenUseCase(checkRouteRepositoryMock)

        val viewModel =
            CheckRouteViewModel(dispatcherProvider, savedStateHandle, setRouteSeenUseCase)

        verify(checkRouteRepositoryMock).setRouteSeen(route.routeId)
    }
}