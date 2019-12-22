package de.jensklingenberg.tuxoid.ui

import de.jensklingenberg.tuxoid.App
import de.jensklingenberg.tuxoid.data.LevelDataSource
import de.jensklingenberg.tuxoid.model.element.Element
import de.jensklingenberg.tuxoid.utils.LevelHelper
import de.jensklingenberg.tuxoid.utils.LevelLoadListener
import de.jensklingenberg.tuxoid.utils.Listener
import javax.inject.Inject

interface MainContract {
    interface View {
        fun onRefresh()
        fun setGameData(level: Array<Array<Array<Element>>>)
    }

    interface Presenter : Listener {

        fun createLevel(level: Int)
    }
}

class MainPresenter(val view: MainContract.View) : MainContract.Presenter {

    @Inject
    lateinit var levelHelper: LevelHelper

    @Inject
    lateinit var levelDataSource: LevelDataSource

    init {
        App.appComponent.inject(this)
    }

    override fun createLevel(level: Int) {
        levelHelper.aktLevel = level

        levelDataSource.setListener( object :LevelLoadListener{
            override fun onLevelLoaded(levelE: Array<Array<Array<Element>>>, levelEo: Array<Array<Array<Element>>>) {
                levelHelper.setLevel(levelE, levelEo)

            }})


        levelDataSource.createLevel(levelHelper.aktLevel)


        view.setGameData(levelHelper.level!!)

    }


    override fun onRefresh() {
        view.onRefresh()

    }


}