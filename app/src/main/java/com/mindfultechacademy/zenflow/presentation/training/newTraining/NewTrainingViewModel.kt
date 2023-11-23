package com.mindfultechacademy.zenflow.presentation.training.newTraining

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.util.copy
import com.mindfultechacademy.zenflow.model.Training
import com.mindfultechacademy.zenflow.model.TrainingAsana
import com.mindfultechacademy.zenflow.model.TrainingWithAsanas
import com.mindfultechacademy.zenflow.repository.AsanasRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Date
import java.util.UUID


class NewTrainingViewModel(private val asanasRepository: AsanasRepository): ViewModel() {
    private val _trainingAsanas: MutableStateFlow<List<TrainingAsana>> = MutableStateFlow(emptyList())

    var asanas = asanasRepository.asanas
    val trainingAsanas: StateFlow<List<TrainingAsana>> = _trainingAsanas
    var trainingName: MutableState<String> = mutableStateOf("")
    private val trainingUUID = UUID.randomUUID().hashCode()
    private val trainingDate = Date().time

    init {
        viewModelScope.launch {
            asanas = asanasRepository.getAsanas()
        }
    }

    val canSave: Boolean get() {
        return trainingName.value.isNotBlank() && _trainingAsanas.value.isNotEmpty()
    }

    val saveableTraining: TrainingWithAsanas get() {
        return TrainingWithAsanas(
            training = Training(trainingUUID, trainingName.value, trainingDate),
            asanas = _trainingAsanas.value
        )
    }

    fun addTrainingAsana(asana: TrainingAsana) {
        _trainingAsanas.value += asana.copy(trainingId = trainingUUID)
    }

    fun updateTrainingAsanaDuration(index: Int, duration: Int) {
        val asanas = _trainingAsanas.value.toMutableList()
        asanas[index] = asanas[index].copy(duration = duration)
        _trainingAsanas.value = asanas
    }

    fun updateTrainingAsanaRepetitions(index: Int, repetitions: Int) {
        val asanas = _trainingAsanas.value.toMutableList()
        asanas[index] = asanas[index].copy(repetitions = repetitions)
        _trainingAsanas.value = asanas
    }
}