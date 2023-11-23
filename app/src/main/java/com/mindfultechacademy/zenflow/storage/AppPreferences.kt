package com.mindfultechacademy.zenflow.storage

import android.content.Context
import androidx.core.content.edit
import com.google.gson.Gson
import com.mindfultechacademy.zenflow.model.User

class AppPreferences(context: Context) {
    private val preferences = context.getSharedPreferences("ZenFlowPreferences", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun setUser(user: User) {
        val json = gson.toJson(user)
        preferences.edit {
            putString("user", json)
        }
    }

    fun getUser(): User? {
        val json = preferences.getString("user", null)
        return if (json != null) {
            gson.fromJson(json, User::class.java)
        } else {
            null
        }
    }
}