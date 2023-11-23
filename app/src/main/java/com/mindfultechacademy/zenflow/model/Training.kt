package com.mindfultechacademy.zenflow.model

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Relation
import androidx.room.Transaction
import androidx.room.TypeConverter
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Entity
data class Training(
    @PrimaryKey val id: Int,
    val name: String,
    val date: Long)

@Entity(primaryKeys = ["asana_id", "training_id"])
data class TrainingAsana(
    @ColumnInfo(name = "asana_id") val asanaId: String,
    @ColumnInfo(name = "asana_name") val asanaName: String,
    val duration: Int,
    @ColumnInfo(name = "training_id")
    val trainingId: Int,
    val repetitions: Int)

data class TrainingWithAsanas(
    @Embedded val training: Training,
    @Relation(
        parentColumn = "id",
        entityColumn = "training_id"
    )
    val asanas: List<TrainingAsana>
)

@Dao
interface TrainingDao {
    @Transaction
    @Query("SELECT * FROM training")
    fun getTrainings(): Flow<List<TrainingWithAsanas>>

    @Transaction
    @Query("SELECT * FROM training WHERE id = (:id)")
    fun getTraining(id: Int): Flow<TrainingWithAsanas>

    @Insert
    suspend fun saveTraining(training: Training)

    @Insert
    suspend fun saveTrainingAsanas(trainingAsana: List<TrainingAsana>)

    @Delete
    suspend fun deleteTraining(training: Training)

    @Delete
    suspend fun deleteTrainigAsanas(training: List<TrainingAsana>)
}