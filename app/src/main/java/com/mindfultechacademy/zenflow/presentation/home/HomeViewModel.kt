package com.mindfultechacademy.zenflow.presentation.home

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindfultechacademy.zenflow.model.Asana
import com.mindfultechacademy.zenflow.repository.AsanasRepository
import com.mindfultechacademy.zenflow.repository.TrainingsRepository
import kotlinx.coroutines.launch

class HomeViewModel(
    private val trainingsRepository: TrainingsRepository,
    private val asanasRepository: AsanasRepository
): ViewModel() {

    val trainingsCount = mutableIntStateOf(0)
    val mostUsedAsana = mutableStateOf<Asana?>(null)
    val featuredAsana = mutableStateOf<Asana?>(null)

    init {
        viewModelScope.launch {

            trainingsRepository.getTrainings().collect() {
                trainingsCount.value = it.size
                val id = it
                    .flatMap { it.asanas }
                    .groupingBy { it.asanaId }
                    .eachCount()
                    .maxByOrNull { it.value }
                    ?.key

                val asanas = asanasRepository.getAsanas()
                mostUsedAsana.value = asanas.find { it.id == id }
                if (asanas.isEmpty() == false ) {
                    featuredAsana.value = asanas.random()
                }
            }
        }
    }
}