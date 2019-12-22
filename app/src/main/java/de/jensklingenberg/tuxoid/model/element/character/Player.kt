package de.jensklingenberg.tuxoid.model.element.character

import android.util.Log

import de.jensklingenberg.tuxoid.interfaces.Moveable
import de.jensklingenberg.tuxoid.interfaces.Playable
import de.jensklingenberg.tuxoid.interfaces.Removable
import de.jensklingenberg.tuxoid.model.Coordinate
import de.jensklingenberg.tuxoid.model.Direction
import de.jensklingenberg.tuxoid.model.element.Element
import de.jensklingenberg.tuxoid.model.element.ElementGroup

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
