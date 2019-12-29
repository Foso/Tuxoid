package de.jensklingenberg.tuxoid.model.element.player

import de.jensklingenberg.tuxoid.R
import de.jensklingenberg.tuxoid.interfaces.Pushable
import de.jensklingenberg.tuxoid.interfaces.Playable
import de.jensklingenberg.tuxoid.interfaces.Removable
import de.jensklingenberg.tuxoid.model.element.Element
import de.jensklingenberg.tuxoid.model.element.ElementGroup
import de.jensklingenberg.tuxoid.model.element.MoveRule


class Player(type: Int) : Element(type), Pushable, Playable, Removable {
    override val moveRule: MoveRule = PlayerMoveRule()
    override val imageResId = R.drawable.player2p
    override val elementGroup: ElementGroup = ElementGroup.charPlayer
}

