package com.prizma_distribucija.prizmaadmin.feature_verify_route.data.repository

import com.google.common.truth.Truth.assertThat
import com.prizma_distribucija.prizmaadmin.core.domain.FirebaseService
import com.prizma_distribucija.prizmaadmin.feature_verify_route.data.remote.dto.UserDto
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.model.User
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.model.UserMapper
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.model.UsersResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

@ExperimentalCoroutinesApi
class PickUserRepositoryImplTests {

    @Test
    fun `getAllUsers, users are empty, should return data = null and error message`() = runTest {
        val firebaseServiceMock = mock(FirebaseService::class.java)
        `when`(firebaseServiceMock.getAllUsers()).thenReturn(emptyList())
        val mapper = UserMapper()
        val repository = PickUserRepositoryImpl(firebaseServiceMock, mapper)
        val result = repository.getAllUsers()

        val expectedResult = UsersResult(
            isSuccess = false,
            data = null,
            errorMessage = "An error occurred"
        )

        assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun `getAllUsers, users are not empty, should return correct data and errorMessage = null`() =
        runTest {
            val firebaseServiceMock = mock(FirebaseService::class.java)
            val usersDto = listOf(UserDto("", "", "", "0"), UserDto("", "", "", "1"))
            val users = listOf(User("", "", "", "0"), User("", "", "", "1"))
            `when`(firebaseServiceMock.getAllUsers()).thenReturn(usersDto)
            val mapper = UserMapper()
            val repository = PickUserRepositoryImpl(firebaseServiceMock, mapper)
            val result = repository.getAllUsers()

            val expectedResult = UsersResult(
                isSuccess = true,
                data = users,
                errorMessage = null
            )

            assertThat(result).isEqualTo(expectedResult)
        }
}