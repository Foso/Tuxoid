package de.jensklingenberg.tuxoid.model.element

import de.jensklingenberg.tuxoid.model.Coordinate
import de.jensklingenberg.tuxoid.model.Direction

class ArrowFactory{

    companion object {

        fun newArrowRed(type: Int): Arrow {

            var direction: Direction = Direction.STAY

            when (type) {
                ElementType.ARROW_UP_RED -> direction = Direction.UP

                ElementType.ARROW_DOWN_RED -> direction = Direction.DOWN

                ElementType.ARROW_LEFT_RED -> direction = Direction.LEFT
                ElementType.ARROW_RIGHT_RED -> direction = Direction.RIGHT
            }

            return Arrow(type, false, direction)


        }

        @JvmStatic fun newArrow(type: Int, coordinate: Coordinate): Arrow {

            var direction: Direction = Direction.STAY

            when (type) {
                ElementType.ARROW_UP -> direction = Direction.UP

                ElementType.ARROW_DOWN -> direction = Direction.DOWN

                ElementType.ARROW_LEFT -> direction = Direction.LEFT
                ElementType.ARROW_RIGHT -> direction = Direction.RIGHT

            }

            return Arrow(type, true, direction)



        }
    }
}