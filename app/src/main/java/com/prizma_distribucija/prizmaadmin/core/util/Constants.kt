package com.prizma_distribucija.prizmaadmin.core.util

import android.graphics.Color

object Constants {

    const val EMPLOYEES_COLLECTION = "users"

    const val ROUTES_COLLECTION = "routes_2"

    const val PATH_POINTS_COLLECTION = "path_points"

    val months = listOf(
        "Siječanj (1)",
        "Veljača (2)",
        "Ožujak (3)",
        "Travanj  (4)",
        "Svibanj (5)",
        "Lipanj (6)",
        "Srpanj  (7)",
        "Kolovoz (8)",
        "Rujan (9)",
        "Listopad (10)",
        "Studeni (11)",
        "Prosinac (12)"
    )

    const val ROUTES_EMPTY_ERROR = "This user doesn't have any recorded routes for this month"

    const val POLYLINE_COLOR = Color.RED
    const val POLYLINE_WIDTH = 8f
}