package com.prizma_distribucija.prizmaadmin.feature_verify_route.data.repository

import com.prizma_distribucija.prizmaadmin.core.domain.FirebaseService
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.PickEmployeeRepository
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.model.*
import kotlinx.coroutines.*
import javax.inject.Inject

class PickEmployeeRepositoryImpl @Inject constructor(
    private val firebaseService: FirebaseService,
    private val routeMapper: RouteMapper,
    private val employeeMapper: EmployeeMapper
) : PickEmployeeRepository {

    override suspend fun getAllEmployeesWithUnseenRoutes(): RepositoryResult<List<EmployeeWithUnseenRoutes>?> {
        return coroutineScope {
            val employeesDtoDeferred = async { firebaseService.getAllEmployees() }
            val unseenRoutesDeferred = async { firebaseService.getUnseenRoutes() }

            val employeesDto = employeesDtoDeferred.await()
            val employees = employeesDto.map { employeeMapper.mapFromDto(it) }

            val unseenRoutesDto = unseenRoutesDeferred.await()
            val unseenRoutes = unseenRoutesDto.map { routeMapper.mapFromDto(it) }

            val isSuccess = employeesDto.isNotEmpty()

            val employeesWithUnseenRoutes = employees.map { employee ->
                EmployeeWithUnseenRoutes(
                    employee,
                    unseenRoutes.filter { it.userId == employee.userId })
            }

            val data = if (isSuccess) employeesWithUnseenRoutes else null

            val errorMessage = if (isSuccess) null else "An error occurred"

            return@coroutineScope RepositoryResult(
                isSuccess, data, errorMessage
            )
        }
    }
}