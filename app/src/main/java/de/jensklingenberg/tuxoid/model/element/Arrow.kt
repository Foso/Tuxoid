package de.jensklingenberg.tuxoid.model.element

import de.jensklingenberg.tuxoid.interfaces.IReachable
import de.jensklingenberg.tuxoid.model.Direction
import de.jensklingenberg.tuxoid.data.ImageRepository

/**
 * Created by jens on 09.02.16.
 */
class Arrow(type: Int, val usedStatus: Boolean, val direction: Direction) :
    Element(type), IReachable {

    private val group = ""

    override val elementGroup: ElementGroup
        get() = ElementGroup.Arrow

    init {

        this.image = ImageRepository.getImage(type)

    }


    override fun isReachable(): Boolean = true


}
