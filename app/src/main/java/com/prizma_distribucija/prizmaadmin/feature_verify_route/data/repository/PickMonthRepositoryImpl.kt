package com.prizma_distribucija.prizmaadmin.feature_verify_route.data.repository

import com.prizma_distribucija.prizmaadmin.core.domain.FirebaseService
import com.prizma_distribucija.prizmaadmin.core.util.DispatcherProvider
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.PickMonthRepository
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.model.*
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PickMonthRepositoryImpl @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val firebaseService: FirebaseService,
    private val employeeMapper: EmployeeMapper,
    private val routeMapper: RouteMapper
) : PickMonthRepository {

    override suspend fun getEmployeeWithUnseenRoutesById(id: String): RepositoryResult<EmployeeWithUnseenRoutes> =
        withContext(dispatcherProvider.io) {
            val employeeDtoDeferred = async { firebaseService.getEmployeeById(id) }
            val unseenRoutesDeferred = async { firebaseService.getUnseenRoutes(id) }

            val employeeDto = employeeDtoDeferred.await()
            val unseenRoutesDto = unseenRoutesDeferred.await()

            val unseenRoutes = unseenRoutesDto.map { routeMapper.mapFromDto(it) }

            val isSuccess = employeeDto != null

            val data = if (isSuccess) EmployeeWithUnseenRoutes(
                employeeMapper.mapFromDto(employeeDto!!),
                unseenRoutes
            ) else null

            val errorMessage = if (isSuccess) null else "An error occurred"

            return@withContext RepositoryResult(isSuccess, data, errorMessage)
        }
}
