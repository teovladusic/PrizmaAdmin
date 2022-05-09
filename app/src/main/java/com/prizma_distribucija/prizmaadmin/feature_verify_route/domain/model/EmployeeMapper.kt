package com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.model

import com.prizma_distribucija.prizmaadmin.core.util.EntityMapper
import com.prizma_distribucija.prizmaadmin.feature_verify_route.data.remote.dto.EmployeeDto

class EmployeeMapper : EntityMapper<EmployeeDto, Employee> {
    override fun mapFromDto(dto: EmployeeDto): Employee {
        return Employee(
            code = dto.code,
            lastName = dto.lastName,
            name = dto.name,
            userId = dto.userId
        )
    }

    override fun mapToDto(domainModel: Employee): EmployeeDto {
        return EmployeeDto(
            code = domainModel.code,
            lastName = domainModel.lastName,
            name = domainModel.name,
            userId = domainModel.userId
        )
    }
}