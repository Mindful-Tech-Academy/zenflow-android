package com.mindfultechacademy.zenflow

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.mindfultechacademy.zenflow.presentation.login.LoginScreen
import com.mindfultechacademy.zenflow.navigation.Screen
import com.mindfultechacademy.zenflow.presentation.asanas.AsanaDetailsScreen
import com.mindfultechacademy.zenflow.presentation.asanas.AsanasScreen
import com.mindfultechacademy.zenflow.presentation.home.HomeScreen
import com.mindfultechacademy.zenflow.presentation.profile.ProfileScreen
import com.mindfultechacademy.zenflow.presentation.training.TrainingsScreen
import com.mindfultechacademy.zenflow.presentation.common.viewModelFactory
import com.mindfultechacademy.zenflow.ui.theme.AppTheme
import com.mindfultechacademy.zenflow.presentation.training.TrainingsViewModel
import com.mindfultechacademy.zenflow.presentation.training.details.TrainingDetailsScreen
import com.mindfultechacademy.zenflow.presentation.training.newTraining.NewTrainingForm
import com.mindfultechacademy.zenflow.presentation.training.newTraining.NewTrainingViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val mainViewModel: MainViewModel = viewModel(factory = viewModelFactory { MainViewModel(authRepository = ZenFlowApp.appModule.authRepository) })
            AppTheme {
                when(mainViewModel.isUserLoggedIn.value) {
                    true -> {
                        MainView(mainViewModel)
                    }
                    false -> {
                        LaunchedEffect(key1 = mainViewModel.isUserLoggedIn) {
                            mainViewModel.checkIfUserIsAuthenticated()
                        }
                        LoginScreen(
                            onLogin = { email, password ->
                                mainViewModel.login(email, password)
                            }
                        )
                    }
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainView(mainViewModel: MainViewModel) {
    val navController = rememberNavController()
    val trainingsViewModel: TrainingsViewModel =
        viewModel(factory = viewModelFactory { TrainingsViewModel(trainingsRepository = ZenFlowApp.appModule.trainingsRepository) })
    Scaffold(
        bottomBar = {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        NavigationBar {
            mainViewModel.items.forEach { screen ->
                NavigationBarItem(
                    selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                    onClick = {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                        }
                    },
                    icon = { mainViewModel.icon(screen) },
                    label = { Text(mainViewModel.title(screen)) }
                )
            }
        }
    }
    ) {
        NavHost(
            navController = navController,
            startDestination = Screen.HomeScreen.route,
            modifier = Modifier.padding(bottom = it.calculateBottomPadding())
        ) {
            composable(Screen.HomeScreen.route) {
                HomeScreen(
                    featuredAsanaTap = {
                        navController.navigate(Screen.AsanaDetails.route + "/$it")
                    }
                )
            }
            composable(Screen.ProfileScreen.route) {
                ProfileScreen(
                    user = ZenFlowApp.appModule.authRepository.loggedInUser,
                    onLogout = {
                        mainViewModel.logout()
                    }
                )
            }
            asanasGraph(navController)
            trainingsGraph(navController, trainingsViewModel)
        }
    }
}

fun NavGraphBuilder.trainingsGraph(
    navController: NavController,
    viewModel: TrainingsViewModel,
) {
    navigation(startDestination = Screen.TrainingList.route, route = Screen.TrainingsScreen.route) {
        composable(Screen.TrainingList.route) {
            TrainingsScreen(
                viewModel = viewModel,
                onAddTrainingTap = {
                    navController.navigate(Screen.NewTraining.route)
                },
                onTrainingTap = {
                    val id = it.training.id
                    navController.navigate(Screen.TrainingDetails.route + "/$id")
                }
            )
        }

        composable(Screen.NewTraining.route) {
            NewTrainingForm(
                onBack = {
                    navController.popBackStack()
                },
                onSave = {
                    viewModel.saveTraining(it)
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.TrainingDetails.route + "/{id}") { backStackEntry ->
            backStackEntry.arguments?.getString("id")?.let { trainingId ->
                TrainingDetailsScreen(
                    id = trainingId.toInt(),
                    onBack = {
                        navController.popBackStack()
                    },
                    onAsanaTap = {
                        navController.navigate(Screen.AsanaDetails.route + "/$it")
                    }
                )
            }
        }
    }
}

fun NavGraphBuilder.asanasGraph(navController: NavController) {
    navigation(startDestination = Screen.AsanasList.route, route = Screen.AsanasScreen.route) {
        composable(Screen.AsanasList.route) {
            AsanasScreen(
                onAsanaTap = {
                    navController.navigate(Screen.AsanaDetails.route + "/${it.id}")
                }
            )
        }

        composable(Screen.AsanaDetails.route + "/{id}") { backStackEntry ->
            backStackEntry.arguments?.getString("id")?.let { asanaId ->
                AsanaDetailsScreen(
                    asanaId = asanaId,
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}