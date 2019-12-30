package de.jensklingenberg.tuxoid.model.element.destination

import de.jensklingenberg.tuxoid.interfaces.IReachable
import de.jensklingenberg.tuxoid.model.element.Element
import de.jensklingenberg.tuxoid.model.element.ElementGroup

/**
 * Created by jens on 18.04.17.
 */

open class Destination(type: Int) : Element(type) {
    override val elementGroup =  ElementGroup.Destination
    override fun isReachable() = true
}
