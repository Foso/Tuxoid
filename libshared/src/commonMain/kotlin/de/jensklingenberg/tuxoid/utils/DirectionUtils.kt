package de.jensklingenberg.tuxoid.utils

import de.jensklingenberg.tuxoid.model.Direction

/**
 * Created by jens on 22/7/17.
 */
class DirectionUtils {
    companion object{
        fun getTouchedDirection(y: Int, x: Int, playX: Int, playY: Int): Direction {
            if (x < playX && (y == playY || y == playY - 1 || y == playY + 1)) {
                return Direction.LEFT
            }
            if (x > playX && (y == playY || y == playY - 1 || y == playY + 1)) {
                return Direction.RIGHT
            }
            if ((x == playX || x == playX - 1 || x == playX + 1) && y < playY) {
                return Direction.UP
            }
            return if ((x == playX || x == playX - 1 || x == playX + 1) && y > playY) {
                Direction.DOWN
            } else Direction.STAY
        }

        fun DirectionLeftOrRight(direction: Direction): Boolean {
            return direction == Direction.LEFT || direction == Direction.RIGHT
        }
    }

}