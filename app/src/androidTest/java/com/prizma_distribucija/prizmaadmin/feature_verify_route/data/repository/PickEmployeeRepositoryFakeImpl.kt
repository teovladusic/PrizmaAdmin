package com.prizma_distribucija.prizmaadmin.feature_verify_route.data.repository

import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.PickEmployeeRepository
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.model.Employee
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.model.EmployeeWithUnseenRoutes
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.model.RepositoryResult
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.model.Route
import kotlinx.coroutines.delay

class PickEmployeeRepositoryFakeImpl : PickEmployeeRepository {

    companion object {
        var isSuccess = false
        var delayTime = 0L
    }

    override suspend fun getAllEmployeesWithUnseenRoutes(): RepositoryResult<List<EmployeeWithUnseenRoutes>?> {
        delay(delayTime)
        val employee1 = Employee("1111", "lastName", "name", "employee1")
        val employee2 = Employee("1111", "lastName", "name", "employee2")

        val unseenRoutesForEmployee1 = emptyList<Route>()
        val unseenRoutesForEmployee2 =
            listOf(Route("", "", 1, "", 1, emptyList(), "", "", "employee2", 2022, false))

        val employeesWithUnseenRoutes = listOf(
            EmployeeWithUnseenRoutes(employee1, unseenRoutesForEmployee1),
            EmployeeWithUnseenRoutes(employee2, unseenRoutesForEmployee2)
        )

        return if (isSuccess) {
            RepositoryResult(true, employeesWithUnseenRoutes, null)
        } else {
            RepositoryResult(false, null, "error message")
        }
    }
}