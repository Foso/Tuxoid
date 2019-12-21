package jensklingenberg.de.tuxoid.model.element.Collectable

import jensklingenberg.de.tuxoid.interfaces.IReachable
import jensklingenberg.de.tuxoid.model.element.Element
import jensklingenberg.de.tuxoid.model.element.ElementGroup

/**
 * Created by jens on 18.04.17.
 */

open class Collectable internal constructor(type: Int, z: Int, y: Int, x: Int) :
    Element(type, z, y, x), IReachable {

    override val elementGroup= ElementGroup.Collectable



}
