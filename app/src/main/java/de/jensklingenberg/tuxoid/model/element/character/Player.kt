package de.jensklingenberg.tuxoid.model.element.character

import de.jensklingenberg.tuxoid.R
import de.jensklingenberg.tuxoid.interfaces.Moveable
import de.jensklingenberg.tuxoid.interfaces.Playable
import de.jensklingenberg.tuxoid.interfaces.Removable
import de.jensklingenberg.tuxoid.model.element.Element
import de.jensklingenberg.tuxoid.model.element.ElementGroup


class Player(type: Int) : Element(type), Moveable, Playable, Removable {
    override val imageResId = R.drawable.player2p
    override val elementGroup: ElementGroup = ElementGroup.charPlayer
}

