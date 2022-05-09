package com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.model

data class RepositoryResult<T>(
    val isSuccess: Boolean,
    val data: T?,
    val errorMessage: String?
)