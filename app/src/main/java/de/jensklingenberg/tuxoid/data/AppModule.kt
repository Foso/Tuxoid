package de.jensklingenberg.tuxoid.data

import android.app.Application
import android.content.Context
import android.content.res.AssetManager
import dagger.Module
import dagger.Provides
import de.jensklingenberg.tuxoid.App
import javax.inject.Singleton

@Module
open class AppModule(private val application: App) {

    @Provides
    @Singleton
    fun provideContext(): Context = application

    @Provides
    @Singleton
    fun provideAssetManager(context: Context): AssetManager = context.assets


    @Provides
    @Singleton
    fun provideApplication(): Application = application

    @Provides
    @Singleton
    fun provideLoadGame(context: Context): LoadGame = LoadGame(context.assets)

    @Provides
    @Singleton
    fun provideLevelHelper(): LevelHelper = LevelHelper()

    @Provides
    @Singleton
    fun provideLoadSidebar(assetManager: AssetManager): LoadSidebar = LoadSidebar(assetManager)

    @Provides
    @Singleton
    fun provideMyImage(context: Context): ImageRepository = ImageRepository(context)

    @Provides
    @Singleton
    fun provideImageSource(context: Context): ImageSource = ImageRepository(context)

    @Provides
    @Singleton
    fun provideLevelDataSource(loadGame: LoadGame, loadSidebar: LoadSidebar, levelHelper: LevelHelper): LevelDataSource = LevelRepository(loadGame, loadSidebar, levelHelper)

}