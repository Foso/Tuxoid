package de.jensklingenberg.tuxoid

import android.app.Application
import android.util.Log
import de.jensklingenberg.tuxoid.data.AppComponent
import de.jensklingenberg.tuxoid.data.AppModule
import de.jensklingenberg.tuxoid.data.DaggerAppComponent

class App : Application() {

    init {
        initializeDagger()

    }


    private fun initializeDagger() {
        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }

    companion object {
        lateinit var appComponent: AppComponent
    }
}