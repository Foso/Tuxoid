package de.jensklingenberg.tuxoid.ui

import de.jensklingenberg.tuxoid.App
import de.jensklingenberg.tuxoid.data.LevelDataSource
import de.jensklingenberg.tuxoid.model.Coordinate
import de.jensklingenberg.tuxoid.model.element.Element
import javax.inject.Inject

class GamePresenter(private val view: GameContract.View) : GameContract.Presenter {

    @Inject
    lateinit var levelDataSource: LevelDataSource

    init {
        App.appComponent.inject(this)
    }

    override fun createLevel(level: Int) {
        levelDataSource.setRefreshListener(this)
        levelDataSource.setListener()

        levelDataSource.loadLevel(level)


        view.setGameData(levelDataSource.getLevelE()[levelDataSource.getAktEbene()])

        val sidebar: Array<Array<Array<Element>>>? = levelDataSource.loadSidebar(level)
        sidebar?.let {
            { sidebar: Array<Array<Array<Element>>> -> view.setSidebarData(sidebar) }
        }

    }

    override fun screenTouched(touchY: Int, touchX: Int) {
        levelDataSource.screenTouched(touchY, touchX)
    }

    override fun onDrag(coordinate: Coordinate, dragElement: Element) {
        levelDataSource.onDrag(coordinate, dragElement)
    }


    override fun onRefresh(arrayOfArrays: Array<Array<Element>>) {
        view.onRefresh(arrayOfArrays)

    }

    override fun onCreate() {

    }

    override fun onDestroy() {

    }


}