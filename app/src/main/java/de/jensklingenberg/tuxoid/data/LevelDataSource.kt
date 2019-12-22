package de.jensklingenberg.tuxoid.data

import de.jensklingenberg.tuxoid.model.element.Element
import de.jensklingenberg.tuxoid.utils.LevelLoadListener
import de.jensklingenberg.tuxoid.utils.LoadGame


interface LevelDataSource{
    fun createLevel(aktLevel:Int)
    fun setListener(listener: LevelLoadListener)
    fun getLevelE(): Array<Array<Array<Element?>?>?>?

    fun getLevelEo(): Array<Array<Array<Element?>?>?>?
}

class LevelRepository(val loadGame: LoadGame): LevelDataSource{

    override fun createLevel(aktLevel: Int) {
        loadGame.createLevel(aktLevel)
    }

    override fun setListener(listener: LevelLoadListener) {
        loadGame.setListener(listener)
    }

    override fun getLevelE(): Array<Array<Array<Element?>?>?>? {
        return loadGame.levelE
    }

    override fun getLevelEo(): Array<Array<Array<Element?>?>?>? {
        return loadGame.levelEo
    }


}