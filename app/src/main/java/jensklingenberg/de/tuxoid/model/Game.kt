package jensklingenberg.de.tuxoid.model

import android.util.SparseArray
import jensklingenberg.de.tuxoid.model.Element.Collectable.Key
import jensklingenberg.de.tuxoid.model.Element.Destination.Door

/**
 * Created by jens on 18.04.17.
 */

object Game {

    var mapMoving = SparseArray<IntArray>()
    //ELEMENT POSITION
    //MOVING WOOD
    var moving_Wood = IntArray(3)

    var fish = 0
        internal set


    fun addFish() {
        fish++
    }

    //FISCH
    fun eatFish() {
        fish--
    }

    fun checkWin(): Boolean {
        return fish == 0
    }

    fun resetFish() {
        fish = 0
    }

    val mapDoor: SparseArray<Coordinate>
        get() = Door.mapDoor

    val mapKey: SparseArray<IntArray>
        get() = Key.mapKey

    fun setMoving_Wood(z: Int, y: Int, x: Int) {

        moving_Wood[0] = z
        moving_Wood[1] = y
        moving_Wood[2] = x


    }


}
