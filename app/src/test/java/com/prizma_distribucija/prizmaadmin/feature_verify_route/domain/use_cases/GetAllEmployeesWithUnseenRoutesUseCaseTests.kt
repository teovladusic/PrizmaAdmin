package com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.use_cases

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.prizma_distribucija.prizmaadmin.core.util.Resource
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.PickEmployeeRepository
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
class GetAllEmployeesWithUnseenRoutesUseCaseTests {

    @Test
    fun `invoke, should emit loading`() = runTest {
        val repository = mock(PickEmployeeRepository::class.java)

        val employeesResult =
            RepositoryResult<List<EmployeeWithUnseenRoutes>?>(false, null, "An error occurred")
        `when`(repository.getAllEmployeesWithUnseenRoutes()).thenReturn(employeesResult)

        val useCase = GetAllEmployeesWithUnseenRoutesUseCase(repository)

        useCase().test {
            val firstItem = awaitItem()
            assertThat(firstItem).isInstanceOf(Resource.Loading::class.java)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `invoke, repository returns error, should emit error`() = runTest {
        val repository = mock(PickEmployeeRepository::class.java)

        val employeesResult =
            RepositoryResult<List<EmployeeWithUnseenRoutes>?>(false, null, "An error occurred")
        `when`(repository.getAllEmployeesWithUnseenRoutes()).thenReturn(employeesResult)

        val useCase = GetAllEmployeesWithUnseenRoutesUseCase(repository)

        useCase().test {
            //loading
            val firstItem = awaitItem()
            assertThat(firstItem).isInstanceOf(Resource.Loading::class.java)
            val secondItem = awaitItem()
            assertThat(secondItem).isInstanceOf(Resource.Error::class.java)
            assertThat(secondItem.message).isEqualTo("An error occurred")
            assertThat(secondItem.data).isNull()
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `invoke, repository returns success, should emit success`() = runTest {
        val repository = mock(PickEmployeeRepository::class.java)
        val employee = Employee("", "", "", "0")
        val unseenRoutes = listOf(Route("", "", 1, "", 1, emptyList(), "", "", "0", 2022, false))

        val employeesWithUnseenRoutes = listOf(EmployeeWithUnseenRoutes(employee, unseenRoutes))
        val employeesResult =
            RepositoryResult<List<EmployeeWithUnseenRoutes>?>(true, employeesWithUnseenRoutes, null)
        `when`(repository.getAllEmployeesWithUnseenRoutes()).thenReturn(employeesResult)

        val useCase = GetAllEmployeesWithUnseenRoutesUseCase(repository)

        useCase().test {
            //loading
            val firstItem = awaitItem()
            assertThat(firstItem).isInstanceOf(Resource.Loading::class.java)
            val secondItem = awaitItem()
            assertThat(secondItem).isInstanceOf(Resource.Success::class.java)
            assertThat(secondItem.data).isEqualTo(employeesWithUnseenRoutes)
            assertThat(secondItem.message).isNull()
            cancelAndIgnoreRemainingEvents()
        }
    }
}