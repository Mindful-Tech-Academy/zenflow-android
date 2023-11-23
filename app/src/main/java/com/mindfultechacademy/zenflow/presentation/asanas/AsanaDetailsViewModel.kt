package com.mindfultechacademy.zenflow.presentation.asanas

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindfultechacademy.zenflow.model.Asana
import com.mindfultechacademy.zenflow.repository.AsanasRepository
import kotlinx.coroutines.launch

class AsanaDetailsViewModel(
    asanaId: String,
    private val asanasRepository: AsanasRepository
): ViewModel() {

        val asana: MutableState<Asana?> = mutableStateOf(null)

    init {
        viewModelScope.launch {
            asana.value = asanasRepository.getAsanas().first { it.id == asanaId }
        }
    }
}