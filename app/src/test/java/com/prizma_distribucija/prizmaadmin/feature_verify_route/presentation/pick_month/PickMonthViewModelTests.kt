package com.prizma_distribucija.prizmaadmin.feature_verify_route.presentation.pick_month

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.prizma_distribucija.prizmaadmin.core.util.Resource
import com.prizma_distribucija.prizmaadmin.core.util.TestDispatchers
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.use_cases.GetEmployeeWIthUnseenRoutesByIdUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

@ExperimentalCoroutinesApi
class PickMonthViewModelTests {

    @Test
    fun `init, should get employee by id`() = runBlocking {
        val useCase = mock(GetEmployeeWIthUnseenRoutesByIdUseCase::class.java)
        `when`(useCase("id")).thenReturn(flowOf(Resource.Loading()))
        val savedStateHandle = SavedStateHandle()
        savedStateHandle.set("userId", "id")
        val dispatcherProvider = TestDispatchers()

        lateinit var viewModel: PickMonthViewModel

        val job = launch {
            viewModel.getEmployeeWithUnseenRoutesStatus.test {
                val status = awaitItem()
                assertThat(status).isInstanceOf(Resource.Loading::class.java)
            }
        }

        viewModel = PickMonthViewModel(savedStateHandle, useCase, dispatcherProvider)

        job.join()
        job.cancel()
    }
}