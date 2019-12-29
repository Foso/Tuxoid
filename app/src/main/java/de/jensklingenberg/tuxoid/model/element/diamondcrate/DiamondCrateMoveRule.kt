package de.jensklingenberg.tuxoid.model.element.diamondcrate

import de.jensklingenberg.tuxoid.data.ElementFactory
import de.jensklingenberg.tuxoid.model.element.MoveRule

class DiamondCrateMoveRule: MoveRule {

    override fun canMove(nexDes: Int): Boolean {
        return ElementFactory.destinationsList.contains(nexDes)
    }
}