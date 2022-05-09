package com.prizma_distribucija.prizmaadmin.core.di

import com.prizma_distribucija.prizmaadmin.core.util.DispatcherProvider
import com.prizma_distribucija.prizmaadmin.core.util.TestAndroidDispatchers
import com.prizma_distribucija.prizmaadmin.feature_verify_route.data.repository.CheckRouteRepositoryFakeImpl
import com.prizma_distribucija.prizmaadmin.feature_verify_route.data.repository.PickEmployeeRepositoryFakeImpl
import com.prizma_distribucija.prizmaadmin.feature_verify_route.data.repository.PickMonthRepositoryFakeImpl
import com.prizma_distribucija.prizmaadmin.feature_verify_route.data.repository.PickRouteRepositoryFakeImpl
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.CheckRouteRepository
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.PickEmployeeRepository
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.PickMonthRepository
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.PickRouteRepository
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.model.EmployeeMapper
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.use_cases.GetAllEmployeesWithUnseenRoutesUseCase
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.use_cases.GetEmployeeWIthUnseenRoutesByIdUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [SingletonModule::class]
)
object TestAppModule {

    @Provides
    @Singleton
    fun provideDispatcherProvider(): DispatcherProvider = TestAndroidDispatchers()

    @Provides
    @Singleton
    fun provideUserMapper(): EmployeeMapper = EmployeeMapper()

    @Provides
    @Singleton
    fun providePickEmployeeRepository(): PickEmployeeRepository = PickEmployeeRepositoryFakeImpl()

    @Provides
    @Singleton
    fun provideGetAllEmployeesUseCase(pickEmployeeRepository: PickEmployeeRepository): GetAllEmployeesWithUnseenRoutesUseCase =
        GetAllEmployeesWithUnseenRoutesUseCase(pickEmployeeRepository)

    @Provides
    @Singleton
    fun providePickMonthRepository(): PickMonthRepository = PickMonthRepositoryFakeImpl()

    @Provides
    @Singleton
    fun provideGetEmployeeByIdUseCase(pickMonthRepository: PickMonthRepository) =
        GetEmployeeWIthUnseenRoutesByIdUseCase(pickMonthRepository)

    @Provides
    @Singleton
    fun providePickRouteRepository(): PickRouteRepository = PickRouteRepositoryFakeImpl()

    @Provides
    @Singleton
    fun provideCheckRouteRepository(): CheckRouteRepository = CheckRouteRepositoryFakeImpl()

}