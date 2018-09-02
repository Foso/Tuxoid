package jensklingenberg.de.tuxoid.utils

import jensklingenberg.de.tuxoid.model.Element.ElementGroup

/**
 * Created by jens on 22/7/17.
 */

object MoveUtils {

    fun Crate_block_can_move(nextObjectDestination: ElementGroup): Boolean {
        return nextObjectDestination == ElementGroup.Destination || nextObjectDestination == ElementGroup.Arrow
    }


    fun Crate_blue_can_move(nextObjectDestination: ElementGroup): Boolean {
        return nextObjectDestination == ElementGroup.Moveable || nextObjectDestination == ElementGroup.Destination || nextObjectDestination == ElementGroup.Arrow
    }
}
