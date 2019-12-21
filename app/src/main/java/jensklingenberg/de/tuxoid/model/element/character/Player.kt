package jensklingenberg.de.tuxoid.model.element.character

import android.util.Log

import jensklingenberg.de.tuxoid.interfaces.Moveable
import jensklingenberg.de.tuxoid.interfaces.Playable
import jensklingenberg.de.tuxoid.interfaces.Removable
import jensklingenberg.de.tuxoid.model.Coordinate
import jensklingenberg.de.tuxoid.model.Direction
import jensklingenberg.de.tuxoid.model.element.Element
import jensklingenberg.de.tuxoid.model.element.ElementGroup

/**
 * Created by jens on 09.02.16.
 */
class Player(type: Int, z: Int, y: Int, x: Int) : Element(type, z, y, x), Moveable, Playable,
    Removable {


    override val elementGroup: ElementGroup
        get() = ElementGroup.charPlayer

    override fun isRemovable(): Boolean {
        return true
    }

    companion object {

        var playerDirection= Direction.STAY
        var position= Coordinate(0,0,0)


        fun setPlayPos(z: Int, y: Int, x: Int) {
            Log.i("TAG", "setPlayPos: $z $y $x")
            position = Coordinate(z, y, x)
        }
    }
}
