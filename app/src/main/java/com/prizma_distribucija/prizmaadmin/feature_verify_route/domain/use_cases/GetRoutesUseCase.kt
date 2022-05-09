package com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.use_cases

import com.prizma_distribucija.prizmaadmin.core.util.Resource
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.PickRouteRepository
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.model.Route
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetRoutesUseCase @Inject constructor(
    private val pickRouteRepository: PickRouteRepository
) {

    suspend operator fun invoke(id: String, month: Int) = flow<Resource<List<Route>>> {
        emit(Resource.Loading())
        val repositoryResponse = pickRouteRepository.getRouteByEmployeeIdAndMonth(id, month)

        if (repositoryResponse.isSuccess) {
            emit(Resource.Success(data = repositoryResponse.data))
        } else {
            emit(
                Resource.Error(
                    data = null,
                    message = repositoryResponse.errorMessage ?: "Unknown error appeared"
                )
            )
        }
    }
}