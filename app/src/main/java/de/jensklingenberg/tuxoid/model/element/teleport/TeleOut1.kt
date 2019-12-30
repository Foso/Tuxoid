package de.jensklingenberg.tuxoid.model.element.teleport

import de.jensklingenberg.tuxoid.R
import de.jensklingenberg.tuxoid.model.element.Element
import de.jensklingenberg.tuxoid.model.element.ElementGroup

class TeleOut1(type: Int) : Element(type) {
    override val imageResId: Int = R.drawable.portal_blue
    override val elementGroup = ElementGroup.TeleportOut
}