package de.jensklingenberg.tuxoid.ui

import de.jensklingenberg.tuxoid.data.Array2D
import de.jensklingenberg.tuxoid.data.Array3D
import de.jensklingenberg.tuxoid.model.Coordinate
import de.jensklingenberg.tuxoid.model.element.Element
import de.jensklingenberg.tuxoid.data.RefreshListener

interface MvpPresenter{
   fun onCreate()
   fun onDestroy()
}


interface GameContract {
    interface View {
        fun onRefresh(levelData:Array2D<Element>)
        fun setGameData(level: Array2D<Element>)
        fun setSidebarData(sidebar: Array3D<Element>)
    }

    interface Presenter : RefreshListener,MvpPresenter {

        fun createLevel(levelId: Int)
        fun screenTouched(touchY: Int, touchX: Int)
        fun onDrag(coordinate: Coordinate, dragElement: Element)
    }
}

