package com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.model

import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.GeoPoint
import com.prizma_distribucija.prizmaadmin.core.util.EntityMapper
import com.prizma_distribucija.prizmaadmin.feature_verify_route.data.remote.dto.RouteDto

class RouteMapper : EntityMapper<RouteDto, Route> {

    override fun mapFromDto(dto: RouteDto): Route {
        return Route(
            avgSpeed = dto.avgSpeed,
            day = dto.day,
            distanceTravelled = dto.distanceTravelled,
            month = dto.month,
            pathPoints = dto.pathPoints.map { LatLng(it.latitude, it.longitude) },
            timeFinished = dto.timeFinished,
            timeStarted = dto.timeStarted,
            userId = dto.userId,
            year = dto.year,
            seen = dto.seen,
            routeId = dto.routeId
        )
    }

    override fun mapToDto(domainModel: Route): RouteDto {
        return RouteDto(
            avgSpeed = domainModel.avgSpeed,
            day = domainModel.day,
            distanceTravelled = domainModel.distanceTravelled,
            month = domainModel.month,
            pathPoints = domainModel.pathPoints.map { GeoPoint(it.latitude, it.longitude) },
            timeFinished = domainModel.timeFinished,
            timeStarted = domainModel.timeStarted,
            userId = domainModel.userId,
            year = domainModel.year,
            seen = domainModel.seen,
            routeId = domainModel.routeId
        )
    }
}