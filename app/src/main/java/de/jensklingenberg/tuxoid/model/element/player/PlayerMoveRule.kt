package de.jensklingenberg.tuxoid.model.element.player

import de.jensklingenberg.tuxoid.model.element.MoveRule

class PlayerMoveRule(): MoveRule {
    override fun canMove(nexDes: Int)=true

}