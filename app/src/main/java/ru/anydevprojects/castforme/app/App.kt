package ru.anydevprojects.castforme.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import ru.anydevprojects.castforme.BuildConfig
import ru.anydevprojects.castforme.utils.CredentialsProvider

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        CredentialsProvider.setAPICredentials(
            key = BuildConfig.API_KEY,
            secret = BuildConfig.SECRET_KEY
        )
    }
}