package com.mindfultechacademy.zenflow.repository

import com.google.firebase.auth.FirebaseAuth
import com.mindfultechacademy.zenflow.model.User
import com.mindfultechacademy.zenflow.storage.AppPreferences
import kotlinx.coroutines.tasks.await
import java.util.concurrent.CancellationException

class AuthRepository(private val appPreferences: AppPreferences) {
    private var auth: FirebaseAuth
    var loggedInUser: User? = appPreferences.getUser()

    init {
        auth = FirebaseAuth.getInstance()
        auth.addAuthStateListener {
            loggedInUser = if (it.currentUser != null) {
                val user = it.currentUser!!
                User(user.uid.hashCode(), user.email ?: "")
            } else {
                null
            }
        }
    }

    suspend fun signIn(email: String, password: String) {
        val user = auth.signInWithEmailAndPassword(email, password).await().user
        user?.run {
            val appUser = User(this.uid.hashCode(), this.email ?:"")
            appPreferences.setUser(appUser)
        }
    }

    suspend fun signOut() {
        try {
            auth.signOut()
        } catch(e: Exception) {
            e.printStackTrace()
            if(e is CancellationException) throw e
        }
    }
}