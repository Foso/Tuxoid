package de.jensklingenberg.tuxoid.data

import de.jensklingenberg.tuxoid.model.Coordinate
import de.jensklingenberg.tuxoid.model.element.Element


interface LevelDataSource{
    fun loadLevel(aktLevel:Int)
    fun setListener()
    fun getLevelE(): Array<Array<Array<Element>>>
    fun setRefreshListener(refreshListener: RefreshListener)
    fun getLevelOld(): Array<Array<Array<Element>>>
    fun loadSidebar(aktLevel: Int): Array<Array<Array<Element>>>?
    fun getGame(): GameState
    fun getAktEbene(): Int
    fun screenTouched(touchY: Int, touchX: Int)
    fun onDrag(coordinate: Coordinate, dragElement: Element)
}

