package com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.model

import com.google.android.gms.maps.model.LatLng
import com.google.common.truth.Truth.assertThat
import com.google.firebase.firestore.GeoPoint
import com.prizma_distribucija.prizmaadmin.feature_verify_route.data.remote.dto.RouteDto
import org.junit.Before
import org.junit.Test

class RouteMapperTests {

    lateinit var routeMapper: RouteMapper

    @Before
    fun setUp() {
        routeMapper = RouteMapper()
    }

    @Test
    fun `map to dto, should correctly map`() {
        val pathPoints = listOf(LatLng(1.0, 1.0))
        val geoPoints = pathPoints.map { GeoPoint(it.latitude, it.longitude) }

        val domainModel =
            Route(
                "",
                "0.0 km/h",
                1,
                "0.00 km",
                1,
                pathPoints,
                "15:22",
                "15:22",
                "userId",
                2022,
                false
            )

        val expectedDto = RouteDto(
            "",
            "0.0 km/h",
            1,
            "0.00 km",
            1,
            geoPoints,
            "15:22",
            "15:22",
            "userId",
            2022,
            false
        )

        val actualDto = routeMapper.mapToDto(domainModel)

        assertThat(actualDto).isEqualTo(expectedDto)
    }

    @Test
    fun `map from dto, should correctly map`() {
        val pathPoints = listOf(LatLng(1.0, 1.0))
        val geoPoints = pathPoints.map { GeoPoint(it.latitude, it.longitude) }

        val dto =
            RouteDto(
                "",
                "0.0 km/h",
                1,
                "0.00 km",
                1,
                geoPoints,
                "15:22",
                "15:22",
                "userId",
                2022,
                false
            )

        val expectedDomainModel = Route(
            "",
            "0.0 km/h",
            1,
            "0.00 km",
            1,
            pathPoints,
            "15:22",
            "15:22",
            "userId",
            2022,
            false
        )

        val actualDomainObject = routeMapper.mapFromDto(dto)

        assertThat(actualDomainObject).isEqualTo(expectedDomainModel)
    }
}