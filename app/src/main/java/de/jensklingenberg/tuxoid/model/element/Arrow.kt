package de.jensklingenberg.tuxoid.model.element

import de.jensklingenberg.tuxoid.interfaces.IReachable
import de.jensklingenberg.tuxoid.model.Direction
import de.jensklingenberg.tuxoid.data.ImageRepository
import de.jensklingenberg.tuxoid.interfaces.IArrow

/**
 * Created by jens on 09.02.16.
 */
class Arrow(type: Int, val usedStatus: Boolean, val direction: Direction) :
    Element(type), IReachable, IArrow {

    override val elementGroup: ElementGroup = ElementGroup.Arrow

    override fun isReachable(): Boolean = true


}
