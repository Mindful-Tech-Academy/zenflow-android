package com.mindfultechacademy.zenflow

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.mindfultechacademy.zenflow.navigation.Screen
import com.mindfultechacademy.zenflow.repository.AuthRepository
import com.mindfultechacademy.zenflow.storage.AppPreferences
import kotlinx.coroutines.launch

class MainViewModel(private val authRepository: AuthRepository) : ViewModel() {

    val isUserLoggedIn = mutableStateOf(false)

    val items = listOf(
        Screen.HomeScreen,
        Screen.AsanasScreen,
        Screen.TrainingsScreen,
        Screen.ProfileScreen
    )

    @Composable
    fun icon(screen: Screen) {
        when (screen) {
            Screen.HomeScreen -> Icon(Icons.Filled.Home, contentDescription = null)
            Screen.AsanasScreen -> Icon(ImageVector.vectorResource(R.drawable.self_improvement_24px), contentDescription = null)
            Screen.TrainingsScreen -> Icon(ImageVector.vectorResource(R.drawable.baseline_sports_gymnastics_24), contentDescription = null)
            Screen.ProfileScreen -> Icon(Icons.Filled.Person, contentDescription = null)
            else -> TODO()
        }
    }

    fun title(screen: Screen): String {
        return when (screen) {
            Screen.HomeScreen -> "ZenFlow"
            Screen.AsanasScreen -> "Asanas"
            Screen.TrainingsScreen -> "Trainings"
            Screen.ProfileScreen -> "Profile"
            else -> TODO()
        }
    }

    fun checkIfUserIsAuthenticated() {
        authRepository.loggedInUser?.let {
            isUserLoggedIn.value = true
        }
    }

    fun logout() {
        viewModelScope.launch {
            authRepository.signOut()
            isUserLoggedIn.value = false
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            authRepository.signIn(email, password)
            isUserLoggedIn.value = true
        }
    }
}
