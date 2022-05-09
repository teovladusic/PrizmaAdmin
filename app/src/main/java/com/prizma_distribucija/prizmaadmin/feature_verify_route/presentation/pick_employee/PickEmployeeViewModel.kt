package com.prizma_distribucija.prizmaadmin.feature_verify_route.presentation.pick_employee

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prizma_distribucija.prizmaadmin.core.util.DispatcherProvider
import com.prizma_distribucija.prizmaadmin.core.util.Resource
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.model.EmployeeWithUnseenRoutes
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.use_cases.GetAllEmployeesWithUnseenRoutesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PickEmployeeViewModel @Inject constructor(
    private val dispatchers: DispatcherProvider,
    private val getAllEmployeesWithUnseenRoutesUseCase: GetAllEmployeesWithUnseenRoutesUseCase
) : ViewModel() {

    private val _getEmployeesWithUnseenRoutesStatus = MutableSharedFlow<Resource<List<EmployeeWithUnseenRoutes>>>(1)
    val getEmployeesWithUnseenRoutesStatus = _getEmployeesWithUnseenRoutesStatus.asSharedFlow()

    private val _employeesWithUnseenRoutes = mutableListOf<EmployeeWithUnseenRoutes>()
    val employeesWithUnseenRoutes get() : List<EmployeeWithUnseenRoutes> = _employeesWithUnseenRoutes

    init {
        viewModelScope.launch(dispatchers.io) {
            getAllEmployeesWithUnseenRoutesUseCase().collectLatest {
                _getEmployeesWithUnseenRoutesStatus.emit(it)
                if (it::class.java == Resource.Success::class.java) {
                    _employeesWithUnseenRoutes.clear()
                    _employeesWithUnseenRoutes.addAll(it.data ?: emptyList())
                }
            }
        }
    }
}