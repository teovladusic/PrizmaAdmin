package com.prizma_distribucija.prizmaadmin.feature_verify_route.data.repository

import com.google.common.truth.Truth.assertThat
import com.prizma_distribucija.prizmaadmin.core.domain.FirebaseService
import com.prizma_distribucija.prizmaadmin.feature_verify_route.data.remote.dto.EmployeeDto
import com.prizma_distribucija.prizmaadmin.feature_verify_route.data.remote.dto.RouteDto
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.model.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

@ExperimentalCoroutinesApi
class PickEmployeeRepositoryImplTests {

    @Test
    fun `getAllEmployeesWithUnseenRoutes, employees are empty, should return data = null and error message`() =
        runTest {
            val firebaseServiceMock = mock(FirebaseService::class.java)
            `when`(firebaseServiceMock.getAllEmployees()).thenReturn(emptyList())
            `when`(firebaseServiceMock.getUnseenRoutes()).thenReturn(emptyList())
            val routeMapper = RouteMapper()
            val employeeMapper = EmployeeMapper()
            val repository =
                PickEmployeeRepositoryImpl(firebaseServiceMock, routeMapper, employeeMapper)
            val result = repository.getAllEmployeesWithUnseenRoutes()

            val expectedResult = RepositoryResult(
                isSuccess = false,
                data = null,
                errorMessage = "An error occurred"
            )

            assertThat(result).isEqualTo(expectedResult)
        }

    @Test
    fun `getAllEmployeesWithUnseenRoutes, employees are not empty, should return correct data and errorMessage = null`() =
        runTest {
            val firebaseServiceMock = mock(FirebaseService::class.java)

            val employeesDto = listOf(EmployeeDto("", "", "", "0"), EmployeeDto("", "", "", "1"))
            val employees = listOf(Employee("", "", "", "0"), Employee("", "", "", "1"))
            `when`(firebaseServiceMock.getAllEmployees()).thenReturn(employeesDto)

            val allUnseenRoutesDto = listOf(
                RouteDto("", "", 1, "", 1, emptyList(), "", "", "0", 2022, false),
                RouteDto("", "", 1, "", 1, emptyList(), "", "", "1", 2022, false)
            )
            `when`(firebaseServiceMock.getUnseenRoutes()).thenReturn(allUnseenRoutesDto)

            val allUnseenRoutes = listOf(
                Route("", "", 1, "", 1, emptyList(), "", "", "0", 2022, false),
                Route("", "", 1, "", 1, emptyList(), "", "", "1", 2022, false)
            )

            val unseenRoutesForUser0 = allUnseenRoutes.filter { it.userId == "0" }
            val unseenRoutesForUser1 = allUnseenRoutes.filter { it.userId == "1" }

            val employeesWithUnseenRoutes = listOf(
                EmployeeWithUnseenRoutes(employees.first(), unseenRoutesForUser0),
                EmployeeWithUnseenRoutes(employees.last(), unseenRoutesForUser1),
            )

            val routeMapper = RouteMapper()
            val employeeMapper = EmployeeMapper()
            val repository =
                PickEmployeeRepositoryImpl(firebaseServiceMock, routeMapper, employeeMapper)
            val result = repository.getAllEmployeesWithUnseenRoutes()

            val expectedResult = RepositoryResult(
                isSuccess = true,
                data = employeesWithUnseenRoutes,
                errorMessage = null
            )

            assertThat(result).isEqualTo(expectedResult)
        }
}