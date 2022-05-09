package com.prizma_distribucija.prizmaadmin.feature_verify_route.presentation.check_route

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prizma_distribucija.prizmaadmin.core.util.DispatcherProvider
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.model.Employee
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.model.Route
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.use_cases.SetRouteSeenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CheckRouteViewModel @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val savedStateHandle: SavedStateHandle,
    private val setRouteSeenUseCase: SetRouteSeenUseCase
) : ViewModel() {

    val route = savedStateHandle.get<Route>("route") ?: throw Exception("Route is null")
    val employee = savedStateHandle.get<Employee>("employee") ?: throw Exception("Employee is null")

    init {
        viewModelScope.launch(dispatcherProvider.io) {
            setRouteSeenUseCase(route.routeId)
        }
    }

    private val _areStatsVisible = MutableStateFlow(false)
    val areStatsVisible = _areStatsVisible.asStateFlow()

    fun changeStatsVisibility() = viewModelScope.launch(dispatcherProvider.default) {
        _areStatsVisible.emit(!areStatsVisible.value)
    }
}