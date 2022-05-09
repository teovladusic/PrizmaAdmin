package com.prizma_distribucija.prizmaadmin.feature_verify_route.data.repository

import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.PickMonthRepository
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.model.Employee
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.model.EmployeeWithUnseenRoutes
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.model.RepositoryResult
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.model.Route
import kotlinx.coroutines.delay

class PickMonthRepositoryFakeImpl : PickMonthRepository {

    companion object {
        var isSuccessful = false
        var delayTime = 0L
    }

    override suspend fun getEmployeeWithUnseenRoutesById(id: String): RepositoryResult<EmployeeWithUnseenRoutes> {
        delay(delayTime)

        val employee = Employee("1234", "lastName", "name", "id")
        val unseenRoutes = emptyList<Route>()
        val employeeWithUnseenRoutes = EmployeeWithUnseenRoutes(employee, unseenRoutes)

        return if (isSuccessful) {
            RepositoryResult(true, employeeWithUnseenRoutes, null)
        } else {
            RepositoryResult(false, null, "error message")
        }
    }
}