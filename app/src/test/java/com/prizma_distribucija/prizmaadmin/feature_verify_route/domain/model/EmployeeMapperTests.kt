package com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.model

import com.google.common.truth.Truth.assertThat
import com.prizma_distribucija.prizmaadmin.feature_verify_route.data.remote.dto.EmployeeDto
import org.junit.Before
import org.junit.Test

class EmployeeMapperTests {

    private lateinit var mapper: EmployeeMapper

    @Before
    fun setUp() {
        mapper = EmployeeMapper()
    }

    @Test
    fun `map employee dto to employee domain model, passing the right data`() {
        val employeeDto =
            EmployeeDto(
                code = "1234",
                lastName = "LastName",
                name = "Name",
                userId = "randomUserId"
            )
        val expectedDomainObject =
            Employee(code = "1234", lastName = "LastName", name = "Name", userId = "randomUserId")

        val domainEmployee = mapper.mapFromDto(employeeDto)

        assertThat(expectedDomainObject.code).isEqualTo(domainEmployee.code)
        assertThat(expectedDomainObject.lastName).isEqualTo(domainEmployee.lastName)
        assertThat(expectedDomainObject.name).isEqualTo(domainEmployee.name)
        assertThat(expectedDomainObject.userId).isEqualTo(domainEmployee.userId)
    }

    @Test
    fun `map employee domain model to dto, passing the right data`() {
        val employeeDomainModel =
            Employee(code = "1234", lastName = "LastName", name = "Name", userId = "randomUserId")
        val expectedDto =
            EmployeeDto(
                code = "1234",
                lastName = "LastName",
                name = "Name",
                userId = "randomUserId"
            )

        val dtoModel = mapper.mapToDto(employeeDomainModel)

        assertThat(expectedDto.code).isEqualTo(dtoModel.code)
        assertThat(expectedDto.lastName).isEqualTo(dtoModel.lastName)
        assertThat(expectedDto.name).isEqualTo(dtoModel.name)
        assertThat(expectedDto.userId).isEqualTo(dtoModel.userId)
    }
}