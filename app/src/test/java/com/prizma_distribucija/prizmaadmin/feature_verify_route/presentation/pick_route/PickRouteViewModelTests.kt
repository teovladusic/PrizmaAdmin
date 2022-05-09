package com.prizma_distribucija.prizmaadmin.feature_verify_route.presentation.pick_route

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.prizma_distribucija.prizmaadmin.core.util.Resource
import com.prizma_distribucija.prizmaadmin.core.util.TestDispatchers
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.model.Employee
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.model.EmployeeWithUnseenRoutes
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.model.Route
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.use_cases.GetRoutesUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

@ExperimentalCoroutinesApi
class PickRouteViewModelTests {

    @Test
    fun `init, should emit getUserStatus`() = runTest {
        val useCase = mock(GetRoutesUseCase::class.java)

        val employee = Employee("1234", "lastName", "name", "userId")
        val unseenRoutes = emptyList<Route>()
        val employeeWithUnseenRoutes = EmployeeWithUnseenRoutes(employee, unseenRoutes)

        val month = 1
        `when`(useCase(employee.userId, month)).thenReturn(flowOf(Resource.Loading()))

        val testDispatcherProvider = TestDispatchers()

        val savedStateHandle = SavedStateHandle()
        savedStateHandle.set("employeeWithUnseenRoutes", employeeWithUnseenRoutes)
        savedStateHandle.set("month", month)

        val viewModel = PickRouteViewModel(savedStateHandle, useCase, testDispatcherProvider)

        viewModel.getRoutesStatus.test {
            val status = awaitItem()
            assertThat(status).isInstanceOf(Resource.Loading::class.java)

            cancelAndIgnoreRemainingEvents()
        }
    }
}