package com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.model

import com.google.common.truth.Truth.assertThat
import com.prizma_distribucija.prizmaadmin.feature_verify_route.data.remote.dto.UserDto
import org.junit.Before
import org.junit.Test

class UserMapperTests {

    private lateinit var mapper: UserMapper

    @Before
    fun setUp() {
        mapper = UserMapper()
    }

    @Test
    fun `map user dto to user domain model, passing the right data`() {
        val userDto =
            UserDto(code = "1234", lastName = "LastName", name = "Name", userId = "randomUserId")
        val expectedDomainObject =
            User(code = "1234", lastName = "LastName", name = "Name", userId = "randomUserId")

        val domainUser = mapper.mapFromDto(userDto)

        assertThat(expectedDomainObject.code).isEqualTo(domainUser.code)
        assertThat(expectedDomainObject.lastName).isEqualTo(domainUser.lastName)
        assertThat(expectedDomainObject.name).isEqualTo(domainUser.name)
        assertThat(expectedDomainObject.userId).isEqualTo(domainUser.userId)
    }

    @Test
    fun `map user domain model to dto, passing the right data`() {
        val userDomainModel =
            User(code = "1234", lastName = "LastName", name = "Name", userId = "randomUserId")
        val expectedDto =
            UserDto(code = "1234", lastName = "LastName", name = "Name", userId = "randomUserId")

        val dtoModel = mapper.mapToDto(userDomainModel)

        assertThat(expectedDto.code).isEqualTo(dtoModel.code)
        assertThat(expectedDto.lastName).isEqualTo(dtoModel.lastName)
        assertThat(expectedDto.name).isEqualTo(dtoModel.name)
        assertThat(expectedDto.userId).isEqualTo(dtoModel.userId)
    }
}