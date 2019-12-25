package de.jensklingenberg.tuxoid.ui

import de.jensklingenberg.tuxoid.model.Coordinate
import de.jensklingenberg.tuxoid.model.element.Element
import de.jensklingenberg.tuxoid.utils.Listener

interface MvpPresenter{
   fun onCreate()
   fun onDestroy()
}


interface MainContract {
    interface View {
        fun onRefresh(levelData: Array<Array<Array<Element>>>?, aktEbene: Int)
        fun setGameData(level: Array<Array<Element>>, aktEbene: Int)
        fun setSidebarData(sidebar: Array<Array<Array<Element>>>)
    }

    interface Presenter : Listener,MvpPresenter {

        fun createLevel(level: Int)
        fun screenTouched(touchY: Int, touchX: Int)
        fun onDrag(coordinate: Coordinate, dragElement: Element)
    }
}

