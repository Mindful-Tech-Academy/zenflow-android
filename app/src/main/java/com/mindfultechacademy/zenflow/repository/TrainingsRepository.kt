package com.mindfultechacademy.zenflow.repository

import com.mindfultechacademy.zenflow.model.TrainingDao
import com.mindfultechacademy.zenflow.model.TrainingWithAsanas
import kotlinx.coroutines.flow.Flow

interface TrainingsRepository {
    fun getTrainings(): Flow<List<TrainingWithAsanas>>
    fun getTraininig(id: Int): Flow<TrainingWithAsanas>
    suspend fun saveTraining(training: TrainingWithAsanas)
    suspend fun deleteTraining(training: TrainingWithAsanas)
}

class TrainingsRepositoryImpl(private val dao: TrainingDao): TrainingsRepository {
    override fun getTrainings(): Flow<List<TrainingWithAsanas>> {
        return dao.getTrainings()
    }

    override fun getTraininig(id: Int): Flow<TrainingWithAsanas> {
        return dao.getTraining(id)
    }

    override suspend fun saveTraining(training: TrainingWithAsanas) {
        dao.saveTraining(training.training)
        dao.saveTrainingAsanas(training.asanas)
    }

    override suspend fun deleteTraining(training: TrainingWithAsanas) {
        dao.deleteTraining(training.training)
        dao.deleteTrainigAsanas(training.asanas)
    }
}