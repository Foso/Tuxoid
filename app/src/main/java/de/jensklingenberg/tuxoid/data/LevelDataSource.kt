package de.jensklingenberg.tuxoid.data

import de.jensklingenberg.tuxoid.model.element.Element


interface LevelDataSource{
    fun loadLevel(aktLevel:Int)
    fun setListener(listener: LevelLoadListener)
    fun getLevelE(): Array<Array<Array<Element?>?>?>?

    fun getLevelEo(): Array<Array<Array<Element?>?>?>?
    fun loadSidebar(aktLevel: Int): Array<Array<Array<Element>>>?
}

