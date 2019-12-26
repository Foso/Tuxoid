package de.jensklingenberg.tuxoid.data

import android.util.SparseArray
import de.jensklingenberg.tuxoid.model.Coordinate
import de.jensklingenberg.tuxoid.model.element.ElementType

/**
 * Created by jens on 18.04.17.
 */


class FishData {

    var fishCount = 0
        internal set


    fun addFish() {
        fishCount++
    }

    //FISCH
    fun eatFish() {
        fishCount--
    }

    fun resetFish() {
        fishCount = 0
    }

}

class GameState {


    val fishData = FishData()
    var moving_Wood = IntArray(3)
    val mapMoving = SparseArray<IntArray>()
    val mapDoor: SparseArray<Coordinate>
        get() = Companion.mapDoor

    val mapKey: SparseArray<IntArray>
        get() = Companion.mapKey

    fun setMoving(type: Int, z: Int, y: Int, x: Int) {
        when (type) {
            ElementType.MOVING_WOOD -> {
                moving_Wood = intArrayOf(z, y, x)

            }
        }


        mapMoving.put(mapMoving.size() + 1, intArrayOf(z, y, x))


    }


    fun checkWin(): Boolean {
        return fishData.fishCount == 0
    }




    companion object {


        private var TeleInPos = IntArray(3)
        var mapKey = SparseArray<IntArray>()

        internal var gate: IntArray? = null
        var mapDoor = SparseArray<Coordinate>()

        private var TeleOutPos = IntArray(3)

        fun setTeleOutPos(z: Int, y: Int, x: Int) {
            TeleOutPos = intArrayOf(z, y, x)
        }


        //TELEIN
        fun getTeleOutPosZ(): Int {
            return TeleOutPos[0]
        }

        fun getTeleOutPosY(): Int {
            return TeleOutPos[1]
        }

        fun getTeleOutPosX(): Int {
            return TeleOutPos[2]
        }


        fun setTeleInPos(z: Int, y: Int, x: Int) {
            TeleInPos = intArrayOf(z, y, x)
        }


        //TELEIN
        fun getTeleInPosZ(): Int {
            return TeleInPos[0]
        }

        fun getTeleInPosY(): Int {
            return TeleInPos[1]
        }

        fun getTeleInPosX(): Int {
            return TeleInPos[2]
        }


        fun getGate(): IntArray? {
            return gate
        }


        private var exitPos = IntArray(3)

        fun setExitPos(z: Int, y: Int, x: Int) {
            exitPos = intArrayOf(z, y, x)
        }


        //TELEIN
        fun getExitPosZ(): Int {
            return exitPos[0]
        }

        fun getExitPosY(): Int {
            return exitPos[1]
        }

        fun getExitPosX(): Int {
            return exitPos[2]
        }
    }


}
