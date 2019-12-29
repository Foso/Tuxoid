package de.jensklingenberg.tuxoid.data

import de.jensklingenberg.tuxoid.model.Coordinate
import de.jensklingenberg.tuxoid.model.Level
import de.jensklingenberg.tuxoid.model.element.Element
import io.reactivex.Single


interface LevelDataSource{
    fun loadLevel(aktLevel:Int) : Single<Level>
    fun getLevelE(): Array<Array<Array<Element>>>
    fun setRefreshListener(refreshListener: RefreshListener)
    fun getLevelOld(): Array<Array<Array<Element>>>
    fun loadSidebar(aktLevel: Int): Array<Array<Array<Element>>>?
    fun getAktEbene(): Int
    fun screenTouched(touchY: Int, touchX: Int)
    fun onDrag(coordinate: Coordinate, dragElement: Element)
}

