package com.prizma_distribucija.prizmaadmin.core.data.services

import com.google.firebase.firestore.FirebaseFirestore
import com.prizma_distribucija.prizmaadmin.core.domain.FirebaseService
import com.prizma_distribucija.prizmaadmin.core.util.Constants
import com.prizma_distribucija.prizmaadmin.feature_verify_route.data.remote.dto.UserDto
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseServiceImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore
) : FirebaseService {

    override suspend fun getAllUsers() : List<UserDto> {
        return firebaseFirestore.collection(Constants.USERS_COLLECTION)
            .get().await().toObjects(UserDto::class.java)
    }
}