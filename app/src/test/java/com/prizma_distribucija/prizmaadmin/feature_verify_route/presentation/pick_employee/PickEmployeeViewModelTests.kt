package com.prizma_distribucija.prizmaadmin.feature_verify_route.presentation.pick_employee

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.prizma_distribucija.prizmaadmin.core.util.Resource
import com.prizma_distribucija.prizmaadmin.core.util.TestDispatchers
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.model.Employee
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.model.EmployeeWithUnseenRoutes
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.model.Route
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.use_cases.GetAllEmployeesWithUnseenRoutesUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

@ExperimentalCoroutinesApi
class PickEmployeeViewModelTests {

    @Test
    fun `init, should get all employees and emit getAllEmployeesStatus`() = runTest {
        val testDispatchers = TestDispatchers()
        val getAllEmployeesWithUnseenRoutesUseCase =
            mock(GetAllEmployeesWithUnseenRoutesUseCase::class.java)
        `when`(getAllEmployeesWithUnseenRoutesUseCase()).thenReturn(flowOf(Resource.Loading()))

        lateinit var viewModel: PickEmployeeViewModel

        val job = launch {
            viewModel.getEmployeesWithUnseenRoutesStatus.test {
                val status = awaitItem()
                assertThat(status).isInstanceOf(Resource.Loading::class.java)
            }
        }

        viewModel = PickEmployeeViewModel(testDispatchers, getAllEmployeesWithUnseenRoutesUseCase)

        job.join()
        job.cancel()
    }

    @Test
    fun `init, on success, should set employees`() = runTest {
        val testDispatchers = TestDispatchers()
        val getAllEmployeesWithUnseenRoutesUseCase =
            mock(GetAllEmployeesWithUnseenRoutesUseCase::class.java)
        val data =
            listOf(
                EmployeeWithUnseenRoutes(
                    Employee("1234", "lastName", "name", "userId"),
                    listOf(Route("", "", 1, "", 1, emptyList(), "", "", "userId", 2022, false))
                )
            )
        `when`(getAllEmployeesWithUnseenRoutesUseCase()).thenReturn(flowOf(Resource.Success(data = data)))

        lateinit var viewModel: PickEmployeeViewModel

        val job = launch {
            viewModel.getEmployeesWithUnseenRoutesStatus.test {
                val status = awaitItem()
                assertThat(viewModel.employeesWithUnseenRoutes).isEqualTo(data)
                assertThat(status).isInstanceOf(Resource.Success::class.java)
            }
        }

        viewModel = PickEmployeeViewModel(testDispatchers, getAllEmployeesWithUnseenRoutesUseCase)

        job.join()
        job.cancel()
    }
}