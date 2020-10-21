package com.agustin.todoapp.domain.repository

import com.agustin.todoapp.domain.entities.User

interface IUserRepository {
    suspend fun createUser(user: User)
    suspend fun signIn(user: User)
    suspend fun signOut()
    suspend fun checkIfUserIsAuthenticated(): Boolean
}