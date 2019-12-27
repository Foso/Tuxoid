package de.jensklingenberg.tuxoid.data

import de.jensklingenberg.tuxoid.model.Coordinate
import de.jensklingenberg.tuxoid.model.element.Element


class LevelRepository(private val loadGame: LoadGame, private val loadSidebar: LoadSidebar, private val levelHelper: LevelHelper,val gameState: GameState) : LevelDataSource {

    private var levelE: Array<Array<Array<Element>>> = arrayOf()
    private var levelEo: Array<Array<Array<Element>>> = arrayOf()


    override fun loadLevel(aktLevel: Int) {
        loadGame.createLevel(aktLevel)
    }

    override fun setListener() {

        loadGame.setListener(object : LevelLoadListener {

            override fun onIntLevelLoaded(levelEint: Array<Array<Array<Int>>>, oldLevelint: Array<Array<Array<Int>>>) {
                this@LevelRepository.levelE = ElementFactory.mapToElement(levelEint)
                this@LevelRepository.levelEo = ElementFactory.mapToElement(oldLevelint)
                // listener.onLevelLoaded(levelE!!,levelEo!!)
                levelHelper.setLevel(levelE, levelEo)

            }

        })

    }

    override fun getLevelE(): Array<Array<Array<Element>>> {
        return levelE
    }

    override fun setRefreshListener(refreshListener: RefreshListener) {
        levelHelper.refreshListener = refreshListener
    }

    override fun getLevelOld(): Array<Array<Array<Element>>> {
        return levelEo
    }

    override fun loadSidebar(aktLevel: Int): Array<Array<Array<Element>>>? {
        return loadSidebar.createLevel(aktLevel)
    }


    override fun getAktEbene(): Int {
        return gameState.aktEbene
    }

    override fun screenTouched(touchY: Int, touchX: Int) {
        levelHelper.screenTouched(touchY,touchX)
    }

    override fun onDrag(coordinate: Coordinate, dragElement: Element) {
        levelHelper.onDrag(coordinate,dragElement)
    }

}