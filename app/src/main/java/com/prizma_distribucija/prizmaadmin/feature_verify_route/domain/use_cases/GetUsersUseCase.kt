package com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.use_cases

import com.prizma_distribucija.prizmaadmin.core.util.DispatcherProvider
import com.prizma_distribucija.prizmaadmin.core.util.Resource
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.PickUserRepository
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.model.User
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(
    private val pickUserRepository: PickUserRepository
) {

    suspend operator fun invoke() = flow<Resource<List<User>>> {
        emit(Resource.Loading(null))

        val result = pickUserRepository.getAllUsers()

        if (result.isSuccess) {
            emit(Resource.Success(data = result.data))
        } else {
            emit(Resource.Error(data = null, message = result.errorMessage.toString()))
        }
    }
}