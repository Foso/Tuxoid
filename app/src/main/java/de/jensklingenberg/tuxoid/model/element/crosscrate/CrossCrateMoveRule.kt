package de.jensklingenberg.tuxoid.model.element.crosscrate

import de.jensklingenberg.tuxoid.data.ElementFactory
import de.jensklingenberg.tuxoid.model.element.MoveRule

class CrossCrateMoveRule: MoveRule {
    override fun canMove(neighbourElementType: Int): Boolean {
        return ElementFactory.moveablesList.contains(neighbourElementType) || ElementFactory.destinationsList.contains(
                neighbourElementType
        ) || ElementFactory.arrowList.contains(neighbourElementType)
    }
}