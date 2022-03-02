package com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.model

data class UsersResult(
    val isSuccess: Boolean,
    val data: List<User>?,
    val errorMessage: String?
)