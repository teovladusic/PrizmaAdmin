package com.prizma_distribucija.prizmaadmin.feature_verify_route.presentation.pick_user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prizma_distribucija.prizmaadmin.core.util.DispatcherProvider
import com.prizma_distribucija.prizmaadmin.core.util.Resource
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.model.User
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.use_cases.GetUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PickUserViewModel @Inject constructor(
    private val dispatchers: DispatcherProvider,
    private val getUsersUseCase: GetUsersUseCase
) : ViewModel() {

    private val _getUsersStatus = MutableSharedFlow<Resource<List<User>>>()
    val getUserStatus = _getUsersStatus.asSharedFlow()

    init {
        viewModelScope.launch(dispatchers.io) {
            getUsersUseCase().collectLatest {
                _getUsersStatus.emit(it)
            }
        }
    }
}