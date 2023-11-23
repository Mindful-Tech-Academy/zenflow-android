package com.mindfultechacademy.zenflow.presentation.training.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindfultechacademy.zenflow.model.TrainingWithAsanas
import com.mindfultechacademy.zenflow.repository.TrainingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class TrainingDetailsViewModel(
    private val id: Int,
    private val trainingsRepository: TrainingsRepository
): ViewModel() {
    private val _training: MutableStateFlow<TrainingWithAsanas?> = MutableStateFlow(null)
    val training: StateFlow<TrainingWithAsanas?> = _training

    init {
        viewModelScope.launch {
            trainingsRepository
                .getTraininig(id)
                .collect() {
                    _training.value = it
                }
        }
    }
}