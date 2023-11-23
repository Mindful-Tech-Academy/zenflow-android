package com.mindfultechacademy.zenflow.navigation

sealed class Screen(val route: String) {
    object HomeScreen: Screen("home_screen")
    object AsanasScreen: Screen("asanas_screen")
    object TrainingsScreen: Screen("trainings_screen")
    object ProfileScreen: Screen("profile_screen")
    object AsanaDetails: Screen("asana_details")
    object AsanasList: Screen("asanas_list")
    object TrainingDetails: Screen("trainings_training_details")
    object TrainingList: Screen("trainings_list")
    object NewTraining: Screen("trainings_new_training")
}
