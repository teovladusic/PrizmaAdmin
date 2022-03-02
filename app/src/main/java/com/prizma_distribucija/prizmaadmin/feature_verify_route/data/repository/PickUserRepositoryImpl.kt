package com.prizma_distribucija.prizmaadmin.feature_verify_route.data.repository

import com.prizma_distribucija.prizmaadmin.core.domain.FirebaseService
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.PickUserRepository
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.model.User
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.model.UserMapper
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.model.UsersResult
import javax.inject.Inject

class PickUserRepositoryImpl @Inject constructor(
    private val firebaseService: FirebaseService,
    private val userMapper: UserMapper
) : PickUserRepository {

    override suspend fun getAllUsers(): UsersResult {
        val usersDto = firebaseService.getAllUsers()

        val isSuccess = usersDto.isNotEmpty()

        val users = usersDto.map { userMapper.mapFromDto(it) }

        val data = if (isSuccess) users else null

        val errorMessage = if (isSuccess) null else "An error occurred"

        return UsersResult(
            isSuccess, data, errorMessage
        )
    }
}