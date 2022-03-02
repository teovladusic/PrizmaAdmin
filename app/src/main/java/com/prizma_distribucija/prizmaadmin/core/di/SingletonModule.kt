package com.prizma_distribucija.prizmaadmin.core.di

import com.google.firebase.firestore.FirebaseFirestore
import com.prizma_distribucija.prizmaadmin.core.data.services.FirebaseServiceImpl
import com.prizma_distribucija.prizmaadmin.core.domain.FirebaseService
import com.prizma_distribucija.prizmaadmin.core.util.DefaultDispatchers
import com.prizma_distribucija.prizmaadmin.core.util.DispatcherProvider
import com.prizma_distribucija.prizmaadmin.core.util.EntityMapper
import com.prizma_distribucija.prizmaadmin.feature_verify_route.data.repository.PickUserRepositoryImpl
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.PickUserRepository
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.model.UserMapper
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.use_cases.GetUsersUseCase
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
    fun provideUserMapper(): UserMapper = UserMapper()

    @Provides
    @Singleton
    fun providePickUserRepository(
        firebaseService: FirebaseService,
        userMapper: UserMapper
    ): PickUserRepository =
        PickUserRepositoryImpl(firebaseService, userMapper)

    @Provides
    @Singleton
    fun provideGetUsersUseCase(pickUserRepository: PickUserRepository): GetUsersUseCase =
        GetUsersUseCase(pickUserRepository)
}