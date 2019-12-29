package de.jensklingenberg.tuxoid.data

import de.jensklingenberg.tuxoid.model.Coordinate
import de.jensklingenberg.tuxoid.model.Level
import de.jensklingenberg.tuxoid.model.element.Element
import io.reactivex.Single

typealias Array3D<T> = Array<Array<Array<T>>>

typealias Array2D<T> = Array<Array<T>>


class LevelRepository(private val gameLoader: GameLoader, private val loadSidebar: LoadSidebar, private val levelHelper: LevelHelper, val gameState: GameState) : LevelDataSource {

    private var levelE: Array3D<Element> = arrayOf()
    private var levelEo: Array3D<Element> = arrayOf()



    override fun loadLevel(aktLevel: Int) : Single<Level> {
      return Single.create<Level> { emitter->
            gameLoader.setListener(object : LevelLoadListener {

                override fun onIntLevelLoaded(levelEint: Array3D<Int>, oldLevelint: Array3D<Int>) {
                    this@LevelRepository.levelE = ElementFactory.mapToElement(levelEint)
                    this@LevelRepository.levelEo = ElementFactory.mapToElement(oldLevelint)
                    gameState.setLevel(Level(levelE, levelEo))
                    val level = Level(levelE, levelEo)
                    emitter.onSuccess(level)
                }

            })
            gameLoader.createLevel(aktLevel)

        }

    }



    override fun getLevelE(): Array3D<Element> {
        return levelE
    }

    override fun setRefreshListener(refreshListener: RefreshListener) {
        levelHelper.refreshListener = refreshListener
    }

    override fun getLevelOld(): Array3D<Element> {
        return levelEo
    }

    override fun loadSidebar(aktLevel: Int): Array3D<Element>? {
        return loadSidebar.createLevel(aktLevel)
    }


    override fun getAktEbene(): Int {
        return gameState.aktEbene
    }

    override fun screenTouched(touchY: Int, touchX: Int) {
        levelHelper.screenTouched(touchY,touchX)
    }

    override fun onDrag(coordinate: Coordinate, dragElement: Element) {
       // levelHelper.onDrag(coordinate,dragElement)
    }

}