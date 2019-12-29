package de.jensklingenberg.tuxoid.data

import android.util.SparseArray
import de.jensklingenberg.tuxoid.model.Coordinate
import de.jensklingenberg.tuxoid.model.Level
import de.jensklingenberg.tuxoid.model.element.Element
import de.jensklingenberg.tuxoid.model.element.ElementType
import de.jensklingenberg.tuxoid.model.element.player.PlayerState
import de.jensklingenberg.tuxoid.model.emptyCoordinate

class GameState {

    init {
        Companion.gameState = this
    }


    var aktEbene = 0


    var levelData: Array3D<Element>? = null

    var levelo: Array3D<Element>? = null

    var level2: Level? = null

    private var TeleInPos = Coordinate(0,0,0)


    val fishData = FishData()
    var moving_Wood = emptyCoordinate()
    val mapMoving = SparseArray<IntArray>()
    val mapDoor: SparseArray<Coordinate>
        get() = Companion.mapDoor

    val mapKey: SparseArray<Coordinate>
        get() = Companion.mapKey


    fun getPlayerPosition() = PlayerState.position

    fun setMoving(type: Int, coordinate: Coordinate) {
        val (z, y, x) = coordinate
        when (type) {
            ElementType.MOVING_WOOD -> {
                moving_Wood = coordinate

            }
        }


        mapMoving.put(mapMoving.size() + 1, intArrayOf(z, y, x))


    }


    fun checkWin(): Boolean {
        return fishData.fishCount == 0
    }

    fun setLevel(
            level: Level
    ) {
        this.levelData = level.foregroundlevelData
        this.levelo = level.backgroundlevelData
    }


    companion object {

        lateinit var gameState: GameState



        fun getInstance(): GameState {
            return gameState
        }

        var mapKey = SparseArray<Coordinate>()

        internal var gate: Coordinate = Coordinate(0,0,0)
        var mapDoor = SparseArray<Coordinate>()

        private var TeleOutPos = Coordinate(0,0,0)

        fun setTeleOutPos(coordinate: Coordinate) {
            TeleOutPos = coordinate
        }


        //TELEIN

        fun getTeleOutPos(): Coordinate {
            return TeleOutPos
        }


        fun setTeleInPos(coordinate: Coordinate) {
            gameState.TeleInPos = coordinate
        }


        //TELEIN
        fun getTeleInPos(): Coordinate = gameState.TeleInPos


        fun getGate(): Coordinate{
            return gate
        }


        private var exitPos = Coordinate(-1,-1,-1)

        fun setExitPos(coordinate: Coordinate) {
            exitPos = coordinate
        }


        //TELEIN

        fun getExitPos(): Coordinate {
            return exitPos
        }


    }


}
