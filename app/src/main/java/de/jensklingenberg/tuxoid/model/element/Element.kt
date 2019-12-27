package de.jensklingenberg.tuxoid.model.element

import android.graphics.Bitmap
import de.jensklingenberg.tuxoid.App
import de.jensklingenberg.tuxoid.R
import de.jensklingenberg.tuxoid.data.ImageSource
import de.jensklingenberg.tuxoid.interfaces.ICollectable
import de.jensklingenberg.tuxoid.interfaces.Moveable
import de.jensklingenberg.tuxoid.interfaces.Removable
import de.jensklingenberg.tuxoid.model.element.destination.*
import de.jensklingenberg.tuxoid.interfaces.IElementGroup
import javax.inject.Inject

open class Element : IElementGroup {

    @Inject
    lateinit var imageSource: ImageSource

    init {
        App.appComponent.inject(this)
    }

    override val elementGroup: ElementGroup = ElementGroup.EMPTY

    open var image: Bitmap? = null

    open val imageResId: Int = 0

    //METHODS

    open var typeId: Int = 0
    private var group: ElementGroup? = null


    constructor(type: Int) {
        this.typeId = type
        this.image = imageSource.loadBitmap(type)

    }


    fun setGroup(group: ElementGroup) {
        this.group = group
    }


    companion object {
        private val TAG = Element::class.java.simpleName


    }
}

class Door(type: Int) : Element(type), Removable
class Exit(type: Int) : Element(type)
class Fish : Element(ElementType.FISH), Removable
class Gate(type: Int) : Element(type), Removable
class Gate_Half(type: Int) : Destination(type)
class Hole1(type: Int) : Destination(type)
class Ice : Destination(ElementType.ICE)
class Key(type: Int) : Element(type), ICollectable, Removable
class LadderDown(type: Int) : Destination(type)
class Ladder_Up(type: Int) : Destination(type)
class Moving_Water(type: Int) : Element(type)
class Moving_Wood(type: Int) : Destination(type)
class Red_Button(type: Int) : Destination(type)
class Switch(type: Int) : Destination(type)
class Switch_Crate_Block(type: Int) : Destination(type)
class TeleIn1(type: Int) : Element(type)
class CrateBlock(type: Int) : Element(type), Moveable, Removable
class CrateBlue(type: Int) : Element(type), Moveable, Removable
class TeleOut1(type: Int) : Element(type) {
    override val imageResId: Int= R.drawable.portal_blue
    override val elementGroup = ElementGroup.TeleportOut
}

class Wall : Element(ElementType.WALL) {
    override val elementGroup = ElementGroup.WALL
    override val imageResId = R.drawable.wallp
}