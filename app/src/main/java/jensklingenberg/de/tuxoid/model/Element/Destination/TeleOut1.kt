package jensklingenberg.de.tuxoid.model.Element.Destination

import jensklingenberg.de.tuxoid.model.Element.Element
import jensklingenberg.de.tuxoid.model.Element.ElementGroup

/**
 * Created by jens on 18.04.17.
 */

class TeleOut1(type: Int, z: Int, y: Int, x: Int) : Element(type, z, y, x) {


    override val elementGroup= ElementGroup.TeleportOut
}
