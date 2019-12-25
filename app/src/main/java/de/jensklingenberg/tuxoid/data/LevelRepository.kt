package de.jensklingenberg.tuxoid.data

import de.jensklingenberg.tuxoid.model.element.Element

class LevelRepository(private val loadGame: LoadGame, private val loadSidebar: LoadSidebar): LevelDataSource {

    override fun loadLevel(aktLevel: Int) {
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

    override fun loadSidebar(aktLevel: Int): Array< Array<Array<Element>>>? {
       return loadSidebar.createLevel(aktLevel)
    }


}