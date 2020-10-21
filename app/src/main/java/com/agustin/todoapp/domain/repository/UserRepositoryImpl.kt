package com.agustin.todoapp.domain.repository

import com.agustin.todoapp.data.dataresources.IRemoteDataResource
import com.agustin.todoapp.domain.entities.User

class UserRepositoryImpl(
    private val remoteDataResource: IRemoteDataResource
) : IUserRepository {
    override suspend fun createUser(user: User) {
        remoteDataResource.createUser(user.email, user.password)
    }

    override suspend fun signIn(user: User) {
        remoteDataResource.signIn(user.email, user.password)
    }

    override suspend fun signOut() {
        remoteDataResource.signOut()
    }

    override suspend fun checkIfUserIsAuthenticated(): Boolean = remoteDataResource.checkUserIsAuthenticated()

}