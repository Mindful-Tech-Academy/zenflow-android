package com.mindfultechacademy.zenflow.presentation.training

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.type.Date
import com.mindfultechacademy.zenflow.model.Training
import com.mindfultechacademy.zenflow.model.TrainingAsana
import com.mindfultechacademy.zenflow.model.TrainingWithAsanas
import com.mindfultechacademy.zenflow.repository.AsanasRepository
import com.mindfultechacademy.zenflow.repository.TrainingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.UUID

class TrainingsViewModel(
    private val trainingsRepository: TrainingsRepository): ViewModel() {
    private val _trainings: MutableStateFlow<List<TrainingWithAsanas>> = MutableStateFlow(emptyList())
    val trainings: StateFlow<List<TrainingWithAsanas>> = _trainings

    init {
        viewModelScope.launch {
            trainingsRepository.getTrainings().collect {
                _trainings.value = it
            }
        }
    }

    fun saveTraining(training: TrainingWithAsanas) {
        viewModelScope.launch {
            trainingsRepository.saveTraining(training)
        }
    }
}