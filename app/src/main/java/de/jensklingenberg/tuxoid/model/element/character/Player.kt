package de.jensklingenberg.tuxoid.model.element.character

import android.util.Log
import de.jensklingenberg.tuxoid.R

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
class Player(type: Int) : Element(type), Moveable, Playable,
        Removable {


    override val imageResId = R.drawable.player2p

    override val elementGroup: ElementGroup
        get() = ElementGroup.charPlayer

    override fun isRemovable(): Boolean = true

    companion object {


        var playerDirection = Direction.STAY
        var position = Coordinate(0, 0, 0)


        fun setPlayPos(z: Int, y: Int, x: Int) {
            position = Coordinate(z, y, x)
        }
    }
}
