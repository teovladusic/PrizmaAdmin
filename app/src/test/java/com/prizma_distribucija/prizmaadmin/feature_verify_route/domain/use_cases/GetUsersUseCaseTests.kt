package com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.use_cases

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.prizma_distribucija.prizmaadmin.core.util.Resource
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.PickUserRepository
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.model.User
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.model.UsersResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

@ExperimentalCoroutinesApi
class GetUsersUseCaseTests {

    @Test
    fun `invoke, should emit loading`() = runTest {
        val repository = mock(PickUserRepository::class.java)

        val usersResult = UsersResult(false, emptyList(), "An error occurred")
        `when`(repository.getAllUsers()).thenReturn(usersResult)

        val useCase = GetUsersUseCase(repository)

        useCase().test {
            val firstItem = awaitItem()
            assertThat(firstItem).isInstanceOf(Resource.Loading::class.java)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `invoke, repository returns error, should emit error`() = runTest {
        val repository = mock(PickUserRepository::class.java)

        val usersResult = UsersResult(false, emptyList(), "An error occurred")
        `when`(repository.getAllUsers()).thenReturn(usersResult)

        val useCase = GetUsersUseCase(repository)

        useCase().test {
            //loading
            val firstItem = awaitItem()
            assertThat(firstItem).isInstanceOf(Resource.Loading::class.java)
            val secondItem = awaitItem()
            assertThat(secondItem).isInstanceOf(Resource.Error::class.java)
            assertThat(secondItem.message).isEqualTo("An error occurred")
            assertThat(secondItem.data).isNull()
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `invoke, repository returns success, should emit success`() = runTest {
        val repository = mock(PickUserRepository::class.java)
        val users = listOf(User("", "", "", ""))
        val usersResult = UsersResult(true, users, null)
        `when`(repository.getAllUsers()).thenReturn(usersResult)

        val useCase = GetUsersUseCase(repository)

        useCase().test {
            //loading
            val firstItem = awaitItem()
            assertThat(firstItem).isInstanceOf(Resource.Loading::class.java)
            val secondItem = awaitItem()
            assertThat(secondItem).isInstanceOf(Resource.Success::class.java)
            assertThat(secondItem.data).isEqualTo(users)
            assertThat(secondItem.message).isNull()
            cancelAndIgnoreRemainingEvents()
        }
    }
}