package com.prizma_distribucija.prizmaadmin.feature_verify_route.presentation.pick_month

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prizma_distribucija.prizmaadmin.core.util.DispatcherProvider
import com.prizma_distribucija.prizmaadmin.core.util.Resource
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.model.EmployeeWithUnseenRoutes
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.use_cases.GetEmployeeWIthUnseenRoutesByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PickMonthViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getEmployeeWIthUnseenRoutesByIdUseCase: GetEmployeeWIthUnseenRoutesByIdUseCase,
    dispatcherProvider: DispatcherProvider
) : ViewModel() {

    private val employeeId = savedStateHandle.get<String>("userId") ?: "id"

    var employeeWithUnseenRoutes: EmployeeWithUnseenRoutes? = null

    private val _getEmployeeWithUnseenRoutesStatus =
        MutableSharedFlow<Resource<EmployeeWithUnseenRoutes>>(1)
    val getEmployeeWithUnseenRoutesStatus = _getEmployeeWithUnseenRoutesStatus.asSharedFlow()

    init {
        viewModelScope.launch(dispatcherProvider.io) {
            getEmployeeWIthUnseenRoutesByIdUseCase(employeeId).collectLatest {
                _getEmployeeWithUnseenRoutesStatus.emit(it)
                if (it::class == Resource.Success::class) {
                    employeeWithUnseenRoutes = it.data!!
                }
            }
        }
    }
}