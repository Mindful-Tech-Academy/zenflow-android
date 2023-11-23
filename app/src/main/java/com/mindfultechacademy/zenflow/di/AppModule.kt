package com.mindfultechacademy.zenflow.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mindfultechacademy.zenflow.repository.AsanasRepository
import com.mindfultechacademy.zenflow.repository.AsanasRepositoryImpl
import com.mindfultechacademy.zenflow.repository.AuthRepository
import com.mindfultechacademy.zenflow.repository.TrainingsRepository
import com.mindfultechacademy.zenflow.repository.TrainingsRepositoryImpl
import com.mindfultechacademy.zenflow.storage.AppDatabase
import com.mindfultechacademy.zenflow.storage.AppPreferences

interface AppModule {
    val authRepository: AuthRepository
    val asanasRepository: AsanasRepository
    val trainingsRepository: TrainingsRepository
}

class AppModuleImpl(private val context: Context): AppModule {
    private val appPreferences: AppPreferences by lazy {
        AppPreferences(context)
    }

    private val db: AppDatabase by lazy {
        Room.databaseBuilder(context, AppDatabase::class.java, "ZenFlowDatabase")
            .fallbackToDestructiveMigration()
            .build()
    }

    override val authRepository: AuthRepository by lazy {
        AuthRepository(appPreferences)
    }

    override val asanasRepository: AsanasRepository by lazy {
        AsanasRepositoryImpl()
    }

    override val trainingsRepository: TrainingsRepository by lazy {
        TrainingsRepositoryImpl(db.trainingDao())
    }
}