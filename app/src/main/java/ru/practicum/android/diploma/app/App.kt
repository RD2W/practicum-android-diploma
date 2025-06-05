package ru.practicum.android.diploma.app

import android.app.Application
import ru.practicum.android.diploma.BuildConfig
import timber.log.Timber

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }
}
