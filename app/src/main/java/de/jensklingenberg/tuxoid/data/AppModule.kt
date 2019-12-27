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
    fun provideGameStatee(): GameState = GameState()

    @Provides
    @Singleton
    fun provideLevelHelper(gameState: GameState,elementDataSource: ElementDataSource): LevelHelper = LevelHelper(gameState,elementDataSource)

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
    fun provideElementDataSource(): ElementDataSource = ElementFactory()

    @Provides
    @Singleton
    fun provideLevelDataSource(loadGame: LoadGame, loadSidebar: LoadSidebar, levelHelper: LevelHelper,gameState: GameState): LevelDataSource = LevelRepository(loadGame, loadSidebar, levelHelper, gameState)

}