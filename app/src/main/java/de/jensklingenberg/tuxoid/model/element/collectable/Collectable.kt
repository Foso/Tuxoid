package de.jensklingenberg.tuxoid.model.element.collectable

import de.jensklingenberg.tuxoid.interfaces.IReachable
import de.jensklingenberg.tuxoid.model.element.Element
import de.jensklingenberg.tuxoid.model.element.ElementGroup

/**
 * Created by jens on 18.04.17.
 */

open class Collectable internal constructor(type: Int, z: Int, y: Int, x: Int) :
    Element(type, z, y, x), IReachable {

    override val elementGroup= ElementGroup.Collectable



}
