package com.prizma_distribucija.prizmaadmin.feature_verify_route.data.remote.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class EmployeeDto(
    val code: String = "",
    val lastName: String = "",
    val name: String = "",
    val userId: String = ""
) : Parcelable