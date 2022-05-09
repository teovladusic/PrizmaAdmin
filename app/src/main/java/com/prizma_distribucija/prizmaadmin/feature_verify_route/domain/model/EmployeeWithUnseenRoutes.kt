package com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class EmployeeWithUnseenRoutes(
    val employee: Employee,
    val unseenRoutes: List<Route>
): Parcelable