package de.jensklingenberg.tuxoid

import android.app.Application
import android.util.Log
import de.jensklingenberg.tuxoid.data.AppComponent
import de.jensklingenberg.tuxoid.data.AppModule
import de.jensklingenberg.tuxoid.data.DaggerAppComponent

class App : Application() {
    companion object {
        lateinit var appComponent: AppComponent
        const val FILE_AUTHORITY = "de.jensklingenberg.sheasy.fileprovider"
    }

    override fun onCreate() {
        super.onCreate()
        initializeDagger()
    }

    open fun initializeDagger() {
        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }
}