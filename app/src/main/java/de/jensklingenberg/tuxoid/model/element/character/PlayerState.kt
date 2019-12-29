package de.jensklingenberg.tuxoid.model.element.character

import de.jensklingenberg.tuxoid.model.Coordinate
import de.jensklingenberg.tuxoid.model.Direction

class PlayerState{
    companion object {


        var playerDirection = Direction.STAY
        var position = Coordinate(0, 0, 0)


        fun setPlayPos(coordinate: Coordinate) {
            position = coordinate
        }
    }
}