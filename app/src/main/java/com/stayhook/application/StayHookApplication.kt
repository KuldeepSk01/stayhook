package com.stayhook.application

import android.app.Application
import com.stayhook.koin.appModule
import com.stayhook.koin.repositoryModule
import com.stayhook.koin.viewModelModule
import com.stayhook.network.CheckConnection
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin

class StayHookApplication : Application(),KoinComponent {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@StayHookApplication)
            modules(listOf(appModule, repositoryModule, viewModelModule))
        }
        CheckConnection(applicationContext)
    }
}