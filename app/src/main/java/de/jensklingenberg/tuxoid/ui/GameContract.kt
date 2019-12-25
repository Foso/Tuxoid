package de.jensklingenberg.tuxoid.ui

import de.jensklingenberg.tuxoid.model.Coordinate
import de.jensklingenberg.tuxoid.model.element.Element
import de.jensklingenberg.tuxoid.data.RefreshListener

interface MvpPresenter{
   fun onCreate()
   fun onDestroy()
}


interface GameContract {
    interface View {
        fun onRefresh(levelData: Array<Array<Array<Element>>>?, aktEbene: Int)
        fun setGameData(level: Array<Array<Element>>, aktEbene: Int)
        fun setSidebarData(sidebar: Array<Array<Array<Element>>>)
    }

    interface Presenter : RefreshListener,MvpPresenter {

        fun createLevel(level: Int)
        fun screenTouched(touchY: Int, touchX: Int)
        fun onDrag(coordinate: Coordinate, dragElement: Element)
    }
}

