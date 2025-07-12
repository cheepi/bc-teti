package com.example.bcnetworking.repository

import com.example.bcnetworking.model.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class UserRepository(private val db: FirebaseFirestore) {

    private val usersCollection = db.collection("users")

    suspend fun getUsers(): List<User> {
        return try {
            val snapshot = usersCollection.get().await()
            snapshot.documents.map {
                User(
                    id = it.id,
                    name = it.getString("name") ?: "",
                    email = it.getString("email") ?: ""
                )
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun addUser(user: User) {
        usersCollection.add(user).await()
    }

    suspend fun deleteUser(id: String) {
        usersCollection.document(id).delete().await()
    }
}
