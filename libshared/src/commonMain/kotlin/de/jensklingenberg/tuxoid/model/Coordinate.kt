package de.jensklingenberg.tuxoid.model

/**
 * Created by jens on 22/7/17.
 */

data class Coordinate(val z: Int, val y: Int, val x: Int)

fun emptyCoordinate() = Coordinate(-1, -1, -1)
