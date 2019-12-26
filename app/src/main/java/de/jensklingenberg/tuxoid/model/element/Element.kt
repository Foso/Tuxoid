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


    constructor(type: Int, z: Int, y: Int, x: Int) {
        this.typeId = type
        this.image = imageSource.loadBitmap(type)

    }


    constructor() {}


    fun setGroup(group: ElementGroup) {
        this.group = group
    }


    companion object {
        private val TAG = Element::class.java.simpleName


    }
}

class Door(type: Int, z: Int, y: Int, x: Int) : Element(type, z, y, x), Removable
class Exit(type: Int, z: Int, y: Int, x: Int) : Element(type, z, y, x)
class Fish(z: Int, y: Int, x: Int) : Element(ElementType.FISH, z, y, x), Removable
class Gate(type: Int, z: Int, y: Int, x: Int) : Element(type, z, y, x), Removable
class Gate_Half(type: Int, z: Int, y: Int, x: Int) : Destination(type, z, y, x)
class Hole1(type: Int, z: Int, y: Int, x: Int) : Destination(type, z, y, x)
class Ice(z: Int, y: Int, x: Int) : Destination(ElementType.ICE, z, y, x)
class Key(type: Int, z: Int, y: Int, x: Int) : Element(type, z, y, x), ICollectable, Removable
class Ladder_Down(type: Int, z: Int, y: Int, x: Int) : Destination(type, z, y, x)
class Ladder_Up(type: Int, z: Int, y: Int, x: Int) : Destination(type, z, y, x)
class Moving_Water(type: Int, z: Int, y: Int, x: Int) : Element(type, z, y, x)
class Moving_Wood(type: Int, z: Int, y: Int, x: Int) : Destination(type, z, y, x)
class Red_Button(type: Int, z: Int, y: Int, x: Int) : Destination(type, z, y, x)
class Switch(type: Int, z: Int, y: Int, x: Int) : Destination(type, z, y, x)
class Switch_Crate_Block(type: Int, z: Int, y: Int, x: Int) : Destination(type, z, y, x)
class TeleIn1(type: Int, z: Int, y: Int, x: Int) : Element(type, z, y, x)
class Crate_Block(type: Int, z: Int, y: Int, x: Int) : Element(type, z, y, x), Moveable, Removable
class Crate_Blue(type: Int, z: Int, y: Int, x: Int) : Element(type, z, y, x), Moveable, Removable
class TeleOut1(type: Int, z: Int, y: Int, x: Int) : Element(type, z, y, x) {
    override val elementGroup = ElementGroup.TeleportOut
}

class Wall(z: Int, y: Int, x: Int) : Element(ElementType.WALL, z, y, x) {
    override val elementGroup = ElementGroup.WALL
    override val imageResId = R.drawable.wallp
}