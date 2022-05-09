package com.prizma_distribucija.prizmaadmin.feature_verify_route.data.repository

import com.google.common.truth.Truth.assertThat
import com.prizma_distribucija.prizmaadmin.core.domain.FirebaseService
import com.prizma_distribucija.prizmaadmin.core.util.TestDispatchers
import com.prizma_distribucija.prizmaadmin.feature_verify_route.data.remote.dto.EmployeeDto
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.model.EmployeeMapper
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.model.EmployeeWithUnseenRoutes
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.model.RouteMapper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`

@ExperimentalCoroutinesApi
class PickMonthRepositoryImplTest {

    @Test
    fun `getEmployeeWithUnseenRoutes, employee is null, should return repositoryResult with correct data`() =
        runTest {
            val dispatcherProvider = TestDispatchers()
            val firebaseServiceMock = Mockito.mock(FirebaseService::class.java)
            `when`(firebaseServiceMock.getEmployeeById("id")).thenReturn(null)
            `when`(firebaseServiceMock.getUnseenRoutes("id")).thenReturn(emptyList())
            val employeeMapper = EmployeeMapper()
            val routeMapper = RouteMapper()
            val repository =
                PickMonthRepositoryImpl(
                    dispatcherProvider,
                    firebaseServiceMock,
                    employeeMapper,
                    routeMapper
                )

            val result = repository.getEmployeeWithUnseenRoutesById("id")

            assertThat(result.isSuccess).isFalse()
            assertThat(result.data).isNull()
            assertThat(result.errorMessage).isEqualTo("An error occurred")
        }

    @Test
    fun `getEmployeeWithUnseenRoutes, employee is not null, should return repositoryResult with correct data`() =
        runTest {
            val dispatcherProvider = TestDispatchers()
            val firebaseServiceMock = Mockito.mock(FirebaseService::class.java)
            val employeeDto = EmployeeDto("1234", "lastName", "name", "id")
            `when`(firebaseServiceMock.getEmployeeById("id")).thenReturn(employeeDto)
            `when`(firebaseServiceMock.getUnseenRoutes("id")).thenReturn(emptyList())

            val employeeMapper = EmployeeMapper()
            val routeMapper = RouteMapper()
            val repository =
                PickMonthRepositoryImpl(
                    dispatcherProvider,
                    firebaseServiceMock,
                    employeeMapper,
                    routeMapper
                )

            val employeeWithUnseenRoutes =
                EmployeeWithUnseenRoutes(employeeMapper.mapFromDto(employeeDto), emptyList())

            val result = repository.getEmployeeWithUnseenRoutesById("id")

            assertThat(result.isSuccess).isTrue()
            assertThat(result.data).isEqualTo(employeeWithUnseenRoutes)
            assertThat(result.errorMessage).isNull()
        }
}