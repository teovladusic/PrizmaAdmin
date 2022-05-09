package com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.use_cases

import com.prizma_distribucija.prizmaadmin.core.util.Resource
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.PickMonthRepository
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.model.EmployeeWithUnseenRoutes
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetEmployeeWIthUnseenRoutesByIdUseCase @Inject constructor(
    private val pickMonthRepositoryImpl: PickMonthRepository
) {

    suspend operator fun invoke(id: String) = flow<Resource<EmployeeWithUnseenRoutes>> {
        emit(Resource.Loading())
        val repositoryResult = pickMonthRepositoryImpl.getEmployeeWithUnseenRoutesById(id)

        if (repositoryResult.isSuccess) {
            emit(Resource.Success(data = repositoryResult.data!!))
        } else {
            emit(
                Resource.Error(
                    message = repositoryResult.errorMessage ?: "Unknown error appeared",
                    data = null
                )
            )
        }
    }
}