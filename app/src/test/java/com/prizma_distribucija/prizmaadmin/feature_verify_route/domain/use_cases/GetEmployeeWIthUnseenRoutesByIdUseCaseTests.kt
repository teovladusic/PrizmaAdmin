package com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.use_cases

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.prizma_distribucija.prizmaadmin.core.util.Resource
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.PickMonthRepository
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.model.Employee
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.model.EmployeeWithUnseenRoutes
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.model.RepositoryResult
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.model.Route
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

@ExperimentalCoroutinesApi
class GetEmployeeWIthUnseenRoutesByIdUseCaseTests {

    @Test
    fun `on repositoryResult failure, emit Error with correct message`() = runTest {
        val repository = mock(PickMonthRepository::class.java)
        val repositoryResult =
            RepositoryResult<EmployeeWithUnseenRoutes>(false, null, "error message")
        `when`(repository.getEmployeeWithUnseenRoutesById("id")).thenReturn(repositoryResult)

        val useCase = GetEmployeeWIthUnseenRoutesByIdUseCase(repository)

        useCase("id").test {
            val firstItem = awaitItem()
            assertThat(firstItem).isInstanceOf(Resource.Loading::class.java)
            val secondItem = awaitItem()
            assertThat(secondItem).isInstanceOf(Resource.Error::class.java)
            assertThat(secondItem.message).isEqualTo("error message")
            assertThat(secondItem.data).isNull()

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `on repositoryResult success, emit Success with correct data`() = runTest {
        val repository = mock(PickMonthRepository::class.java)
        val employee = Employee("1234", "lastName", "name", "id")

        val unseenRoutes = emptyList<Route>()

        val employeeWithUnseenRoutes = EmployeeWithUnseenRoutes(employee, unseenRoutes)

        val repositoryResult = RepositoryResult(true, employeeWithUnseenRoutes, null)
        `when`(repository.getEmployeeWithUnseenRoutesById("id")).thenReturn(repositoryResult)

        val useCase = GetEmployeeWIthUnseenRoutesByIdUseCase(repository)

        useCase("id").test {
            val firstItem = awaitItem()
            assertThat(firstItem).isInstanceOf(Resource.Loading::class.java)
            val secondItem = awaitItem()
            assertThat(secondItem).isInstanceOf(Resource.Success::class.java)
            assertThat(secondItem.message).isNull()
            assertThat(secondItem.data).isEqualTo(employeeWithUnseenRoutes)

            cancelAndIgnoreRemainingEvents()
        }
    }
}