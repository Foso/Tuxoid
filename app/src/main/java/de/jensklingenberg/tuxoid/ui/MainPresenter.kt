package de.jensklingenberg.tuxoid.ui

import de.jensklingenberg.tuxoid.App
import de.jensklingenberg.tuxoid.data.LevelDataSource
import de.jensklingenberg.tuxoid.model.Coordinate
import de.jensklingenberg.tuxoid.model.element.Element
import de.jensklingenberg.tuxoid.utils.LevelHelper
import de.jensklingenberg.tuxoid.utils.LevelLoadListener
import de.jensklingenberg.tuxoid.utils.LoadSidebar
import javax.inject.Inject

class MainPresenter(val view: MainContract.View) : MainContract.Presenter {

    @Inject
    lateinit var levelHelper: LevelHelper

    @Inject
    lateinit var loadSidebar : LoadSidebar

    @Inject
    lateinit var levelDataSource: LevelDataSource


    init {
        App.appComponent.inject(this)
    }

    override fun createLevel(level: Int) {
        levelHelper.listener=(this)

        levelDataSource.setListener( object : LevelLoadListener {
            override fun onLevelLoaded(levelE: Array<Array<Array<Element>>>, levelEo: Array<Array<Array<Element>>>) {
                levelHelper.setLevel(levelE, levelEo)

            }})


        levelDataSource.loadLevel(level)


        view.setGameData(levelHelper.levelData!![levelHelper.aktEbene]!!,levelHelper.aktEbene)

        val sidebar: Array<Array<Array<Element>>>? = loadSidebar.createLevel(level)
       sidebar?.let {
           { sidebar: Array<Array<Array<Element>>> -> view.setSidebarData(sidebar) }
       }

    }

    override fun screenTouched(touchY: Int, touchX: Int) {
            levelHelper.screenTouched(touchY,touchX)
    }

    override fun onDrag(coordinate: Coordinate, dragElement: Element) {
        levelHelper.onDrag(coordinate,dragElement)
    }


    override fun onRefresh() {
        view.onRefresh(levelHelper.levelData, levelHelper.aktEbene)

    }

    override fun onCreate() {

    }

    override fun onDestroy() {

    }


}