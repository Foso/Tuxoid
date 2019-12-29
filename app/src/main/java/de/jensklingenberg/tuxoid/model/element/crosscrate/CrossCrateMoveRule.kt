package de.jensklingenberg.tuxoid.model.element.crosscrate

import de.jensklingenberg.tuxoid.data.ElementFactory
import de.jensklingenberg.tuxoid.model.element.MoveRule

class CrossCrateMoveRule: MoveRule {
    override fun canMove(nexDes: Int): Boolean {
        return ElementFactory.moveablesList.contains(nexDes) || ElementFactory.destinationsList.contains(
                nexDes
        ) || ElementFactory.arrowList.contains(nexDes)
    }
}