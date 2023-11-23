package com.mindfultechacademy.zenflow.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mindfultechacademy.zenflow.model.Training
import com.mindfultechacademy.zenflow.model.TrainingAsana
import com.mindfultechacademy.zenflow.model.TrainingDao

@Database(entities = [Training::class, TrainingAsana::class], version = 3)
abstract class AppDatabase : RoomDatabase() {
    abstract fun trainingDao(): TrainingDao
}