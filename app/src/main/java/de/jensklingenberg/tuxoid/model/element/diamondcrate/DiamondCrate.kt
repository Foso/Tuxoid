package de.jensklingenberg.tuxoid.model.element.diamondcrate

import de.jensklingenberg.tuxoid.interfaces.Pushable
import de.jensklingenberg.tuxoid.interfaces.Removable
import de.jensklingenberg.tuxoid.model.element.Element

class DiamondCrate(type: Int) : Element(type), Pushable, Removable {

    override val moveRule = DiamondCrateMoveRule()

}