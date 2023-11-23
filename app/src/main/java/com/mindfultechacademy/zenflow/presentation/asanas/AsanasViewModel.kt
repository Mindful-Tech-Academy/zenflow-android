package com.mindfultechacademy.zenflow.presentation.asanas

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindfultechacademy.zenflow.model.Asana
import com.mindfultechacademy.zenflow.repository.AsanasRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AsanasViewModel(private val asanasRepository: AsanasRepository): ViewModel() {
    private val _state = MutableStateFlow(AsanasState())
    val state = _state.asStateFlow()

    init {
        _state.update {
            it.copy(asanasRepository.asanas.toMutableStateList())
        }
        getAsanas()
    }

    fun getAsanas() {
        viewModelScope.launch {
            _state.update {
                it.copy(asanasRepository.getAsanas().toMutableStateList())
            }
        }
    }

    companion object {
        data class AsanasState (
            val asanas: List<Asana> = listOf()
        )
    }
}

