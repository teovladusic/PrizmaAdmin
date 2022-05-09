package com.prizma_distribucija.prizmaadmin.feature_verify_route.presentation.pick_route

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prizma_distribucija.prizmaadmin.core.util.DispatcherProvider
import com.prizma_distribucija.prizmaadmin.core.util.Resource
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.model.Employee
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.model.EmployeeWithUnseenRoutes
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.model.Route
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.use_cases.GetRoutesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PickRouteViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getRoutesUseCase: GetRoutesUseCase,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    val employeeWithUnseenRoutes =
        savedStateHandle.get<EmployeeWithUnseenRoutes>("employeeWithUnseenRoutes")
            ?: throw Exception("Employee is null")

    val month: Int = savedStateHandle.get<Int>("month") ?: throw Exception("Month is null")

    private var _routes = listOf<Route>()
    val routes get() = _routes

    private val _getRoutesStatus = MutableSharedFlow<Resource<List<Route>>>(1)
    val getRoutesStatus = _getRoutesStatus.asSharedFlow()

    init {
        viewModelScope.launch(dispatcherProvider.io) {
            getRoutesUseCase(employeeWithUnseenRoutes.employee.userId, month).collectLatest {
                _getRoutesStatus.emit(it)
                if (it::class.java == Resource.Success::class.java) {
                    _routes = it.data ?: emptyList()
                }
            }
        }
    }
}