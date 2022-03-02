package com.prizma_distribucija.prizmaadmin.feature_verify_route.domain

import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.model.User
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.model.UsersResult

interface PickUserRepository {

    suspend fun getAllUsers() : UsersResult
}