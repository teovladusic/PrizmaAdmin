package com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.use_cases

import com.prizma_distribucija.prizmaadmin.core.util.Resource
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.PickEmployeeRepository
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.model.EmployeeWithUnseenRoutes
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAllEmployeesWithUnseenRoutesUseCase @Inject constructor(
    private val pickEmployeeRepository: PickEmployeeRepository
) {

    suspend operator fun invoke() = flow<Resource<List<EmployeeWithUnseenRoutes>>> {
        emit(Resource.Loading(null))

        val result = pickEmployeeRepository.getAllEmployeesWithUnseenRoutes()

        if (result.isSuccess) {
            emit(Resource.Success(data = result.data))
        } else {
            emit(Resource.Error(data = null, message = result.errorMessage.toString()))
        }
    }
}