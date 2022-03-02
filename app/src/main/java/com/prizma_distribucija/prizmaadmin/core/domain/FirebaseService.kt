package com.prizma_distribucija.prizmaadmin.core.domain

import com.prizma_distribucija.prizmaadmin.feature_verify_route.data.remote.dto.UserDto

interface FirebaseService {

    suspend fun getAllUsers(): List<UserDto>
}