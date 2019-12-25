package de.jensklingenberg.tuxoid.data

import de.jensklingenberg.tuxoid.model.element.Element

class LevelRepository(private val loadGame: LoadGame, private val loadSidebar: LoadSidebar): LevelDataSource {

    private var levelE: Array<Array<Array<Element>>>? = null
    private var levelEo: Array<Array<Array<Element>>>?=null


    override fun loadLevel(aktLevel: Int) {
        loadGame.createLevel(aktLevel)
    }

    override fun setListener(listener: LevelLoadListener) {

        loadGame.setListener(object :LevelLoadListener{
            override fun onLevelLoaded(levelE: Array<Array<Array<Element>>>, oldLevel: Array<Array<Array<Element>>>) {
                this@LevelRepository.levelE=levelE
                this@LevelRepository.levelEo =oldLevel
                listener.onLevelLoaded(levelE,oldLevel)
            }

        })

    }

    override fun getLevelE(): Array<Array<Array<Element>>>? {
        return levelE
    }

    override fun getLevelOld(): Array<Array<Array<Element>>>? {
        return levelEo
    }

    override fun loadSidebar(aktLevel: Int): Array< Array<Array<Element>>>? {
       return loadSidebar.createLevel(aktLevel)
    }


}