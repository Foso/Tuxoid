package de.jensklingenberg.tuxoid.model.element.player

import de.jensklingenberg.tuxoid.data.ElementFactory
import de.jensklingenberg.tuxoid.model.element.MoveRule

class PlayerMoveRule : MoveRule {
    override fun canMove(neighbourElementType: Int): Boolean {
        val nextElement = ElementFactory.elementFactory(neighbourElementType)

        return when {
            nextElement.isReachable() -> {
                true
            }
            nextElement.isPushable() -> {
                true
            }
            nextElement.isCollectable() -> {
                true
            }
            else -> false
        }
    }

}