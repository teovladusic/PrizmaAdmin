package com.prizma_distribucija.prizmaadmin.core.di

import com.google.firebase.firestore.FirebaseFirestore
import com.prizma_distribucija.prizmaadmin.core.data.services.FirebaseServiceImpl
import com.prizma_distribucija.prizmaadmin.core.domain.FirebaseService
import com.prizma_distribucija.prizmaadmin.core.util.DefaultDispatchers
import com.prizma_distribucija.prizmaadmin.core.util.DispatcherProvider
import com.prizma_distribucija.prizmaadmin.feature_verify_route.data.repository.CheckRouteRepositoryImpl
import com.prizma_distribucija.prizmaadmin.feature_verify_route.data.repository.PickEmployeeRepositoryImpl
import com.prizma_distribucija.prizmaadmin.feature_verify_route.data.repository.PickMonthRepositoryImpl
import com.prizma_distribucija.prizmaadmin.feature_verify_route.data.repository.PickRouteRepositoryImpl
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.CheckRouteRepository
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.PickEmployeeRepository
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.PickMonthRepository
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.PickRouteRepository
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.model.EmployeeMapper
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.model.RouteMapper
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.use_cases.GetAllEmployeesWithUnseenRoutesUseCase
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.use_cases.GetEmployeeWIthUnseenRoutesByIdUseCase
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.use_cases.GetRoutesUseCase
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.use_cases.SetRouteSeenUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SingletonModule {

    @Provides
    @Singleton
    fun provideDispatcherProvider(): DispatcherProvider = DefaultDispatchers()

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseService(firebaseFirestore: FirebaseFirestore): FirebaseService =
        FirebaseServiceImpl(firebaseFirestore)

    @Provides
    @Singleton
    fun provideEmployeeMapper(): EmployeeMapper = EmployeeMapper()

    @Provides
    @Singleton
    fun providePickEmployeeRepository(
        firebaseService: FirebaseService,
        routeMapper: RouteMapper,
        employeeMapper: EmployeeMapper
    ): PickEmployeeRepository =
        PickEmployeeRepositoryImpl(firebaseService, routeMapper, employeeMapper)

    @Provides
    @Singleton
    fun provideGetAllEmployeesUseCase(pickEmployeeRepository: PickEmployeeRepository): GetAllEmployeesWithUnseenRoutesUseCase =
        GetAllEmployeesWithUnseenRoutesUseCase(pickEmployeeRepository)

    @Provides
    @Singleton
    fun providePickMonthRepository(
        dispatcherProvider: DispatcherProvider,
        firebaseService: FirebaseService,
        employeeMapper: EmployeeMapper,
        routeMapper: RouteMapper
    ): PickMonthRepository =
        PickMonthRepositoryImpl(dispatcherProvider, firebaseService, employeeMapper, routeMapper)

    @Provides
    @Singleton
    fun provideGetEmployeeByIdUseCase(
        pickMonthRepository: PickMonthRepository
    ) = GetEmployeeWIthUnseenRoutesByIdUseCase(pickMonthRepository)

    @Provides
    @Singleton
    fun provideRouteMapper() = RouteMapper()

    @Provides
    @Singleton
    fun providePickRouteRepository(
        dispatcherProvider: DispatcherProvider,
        firebaseService: FirebaseService,
        routeMapper: RouteMapper
    ): PickRouteRepository =
        PickRouteRepositoryImpl(dispatcherProvider, firebaseService, routeMapper)

    @Provides
    @Singleton
    fun provideGetRoutesUseCase(
        pickRouteRepository: PickRouteRepository
    ) = GetRoutesUseCase(pickRouteRepository)

    @Provides
    @Singleton
    fun provideCheckRouteRepository(
        firebaseService: FirebaseService
    ): CheckRouteRepository = CheckRouteRepositoryImpl(firebaseService)

    @Provides
    @Singleton
    fun provideSetRouteSeenUseCase(
        checkRouteRepository: CheckRouteRepository
    ) = SetRouteSeenUseCase(checkRouteRepository)

}