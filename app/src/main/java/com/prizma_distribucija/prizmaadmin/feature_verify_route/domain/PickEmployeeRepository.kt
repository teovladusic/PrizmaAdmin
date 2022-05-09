package com.prizma_distribucija.prizmaadmin.feature_verify_route.domain

import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.model.EmployeeWithUnseenRoutes
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.model.RepositoryResult

interface PickEmployeeRepository {

    suspend fun getAllEmployeesWithUnseenRoutes() : RepositoryResult<List<EmployeeWithUnseenRoutes>?>
}