package de.jensklingenberg.tuxoid.data

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import de.jensklingenberg.tuxoid.App
import de.jensklingenberg.tuxoid.model.MyImage
import de.jensklingenberg.tuxoid.utils.LevelHelper
import de.jensklingenberg.tuxoid.utils.LoadGame
import javax.inject.Singleton

@Module
open class AppModule(private val application: App) {

    @Provides
    @Singleton
    fun provideContext(): Context = application


    @Provides
    @Singleton
    fun provideApplication(): Application = application

    @Provides
    @Singleton
    fun provideLoadGame(context:Context): LoadGame = LoadGame(context.assets)

    @Provides
    @Singleton
    fun provideLevelHelper(): LevelHelper = LevelHelper()

    @Provides
    @Singleton
    fun provideMyImage(context:Context): MyImage = MyImage(context)



    @Provides
    @Singleton
    fun provideLevelDataSource(loadGame: LoadGame): LevelDataSource = LevelRepository(loadGame)

}