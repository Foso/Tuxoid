package jensklingenberg.de.tuxoid.model.element

import jensklingenberg.de.tuxoid.interfaces.IReachable
import jensklingenberg.de.tuxoid.model.Direction
import jensklingenberg.de.tuxoid.model.MyImage

/**
 * Created by jens on 09.02.16.
 */
class Arrow(type: Int, z: Int, y: Int, x: Int, val usedStatus: Boolean, val direction: Direction) :
    Element(type, z, y, x), IReachable {

    private val group = ""

    override val elementGroup: ElementGroup
        get() = ElementGroup.Arrow

    init {
        this.image = MyImage.getImage(type)

    }


    override fun isReachable(): Boolean {
        return true
    }

    companion object {

        fun newArrowRed(type: Int, z: Int, y: Int, x: Int): Arrow {

            var direction: Direction = Direction.STAY

            when (type) {
                ElementType.ARROW_UP_RED -> direction = Direction.UP

                ElementType.ARROW_DOWN_RED -> direction = Direction.DOWN

                ElementType.ARROW_LEFT_RED -> direction = Direction.LEFT
                ElementType.ARROW_RIGHT_RED -> direction = Direction.RIGHT
            }

            return Arrow(type, z, y, x, false, direction)


        }

        @JvmStatic fun newArrow(type: Int, z: Int, y: Int, x: Int): Arrow {

            var direction: Direction = Direction.STAY

            when (type) {
                ElementType.ARROW_UP -> direction = Direction.UP

                ElementType.ARROW_DOWN -> direction = Direction.DOWN

                ElementType.ARROW_LEFT -> direction = Direction.LEFT
                ElementType.ARROW_RIGHT -> direction = Direction.RIGHT

            }

            return Arrow(type, z, y, x, true, direction)



        }
    }
}
