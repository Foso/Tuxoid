package de.jensklingenberg.tuxoid.utils

import de.jensklingenberg.tuxoid.model.Coordinate
import java.util.*

/**
 * Created by jens on 23/7/17.
 */
class Utils {
    companion object{
        fun newCoordinates(z: Int, y: Int, x: Int): Coordinate {
            val coord: MutableList<Int> = ArrayList()
            coord.add(z)
            coord.add(y)
            coord.add(x)
            return Coordinate(z, y, x)
        }
    }

}