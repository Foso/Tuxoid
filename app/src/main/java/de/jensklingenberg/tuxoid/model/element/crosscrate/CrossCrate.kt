package de.jensklingenberg.tuxoid.model.element.crosscrate

import de.jensklingenberg.tuxoid.interfaces.Pushable
import de.jensklingenberg.tuxoid.interfaces.Removable
import de.jensklingenberg.tuxoid.model.element.Element

class CrossCrate(type: Int) : Element(type), Pushable, Removable {

    override val moveRule= CrossCrateMoveRule()

}