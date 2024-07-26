package com.example.testtask.app

import android.app.Application
import com.example.testtask.di.appModule
import com.example.testtask.di.dataModule
import com.example.testtask.di.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidLogger(level = Level.DEBUG)
            androidContext(this@App)
            modules(listOf(appModule, dataModule, domainModule))
        }
    }
}