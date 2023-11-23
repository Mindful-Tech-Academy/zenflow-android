package com.mindfultechacademy.zenflow

import android.app.Application
import com.mindfultechacademy.zenflow.di.AppModule
import com.mindfultechacademy.zenflow.di.AppModuleImpl

class ZenFlowApp : Application() {
    companion object {
        lateinit var appModule: AppModule
    }

    override fun onCreate() {
        super.onCreate()
        appModule = AppModuleImpl(this)
    }
}