package com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.model

import com.prizma_distribucija.prizmaadmin.core.util.EntityMapper
import com.prizma_distribucija.prizmaadmin.feature_verify_route.data.remote.dto.UserDto

class UserMapper : EntityMapper<UserDto, User> {
    override fun mapFromDto(dto: UserDto): User {
        return User(
            code = dto.code,
            lastName = dto.lastName,
            name = dto.name,
            userId = dto.userId
        )
    }

    override fun mapToDto(domainModel: User): UserDto {
        return UserDto(
            code = domainModel.code,
            lastName = domainModel.lastName,
            name = domainModel.name,
            userId = domainModel.userId
        )
    }
}