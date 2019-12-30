package de.jensklingenberg.tuxoid.model.element

import de.jensklingenberg.tuxoid.App
import de.jensklingenberg.tuxoid.R
import de.jensklingenberg.tuxoid.data.ImageSource
import de.jensklingenberg.tuxoid.interfaces.*
import de.jensklingenberg.tuxoid.model.element.destination.*
import javax.inject.Inject

interface MoveRule {

    fun canMove(nexDes: Int): Boolean
}

class ElementMoveRule : MoveRule {
    override fun canMove(nexDes: Int) = false
}


open class Element(type: Int)  {

    open val moveRule: MoveRule = ElementMoveRule()
    open val elementGroup: ElementGroup = ElementGroup.EMPTY


    @Inject
    lateinit var imageSource: ImageSource

    init {
        App.appComponent.inject(this)
    }


    open fun isCollectable() = false

    open fun isPushable() = false

    open val imageResId: Int = 0

    open fun isReachable() = false

    //METHODS

    open var typeId: Int = type
    private var group: ElementGroup? = null


    fun setGroup(group: ElementGroup) {
        this.group = group
    }


    companion object {
        private val TAG = Element::class.java.simpleName


    }
}

class Door(type: Int) : Element(type), Removable
class Exit(type: Int) : Element(type)
class Fish : Element(ElementType.FISH), Removable {
    override fun isCollectable() = true
}

class Gate(type: Int) : Element(type), Removable
class Gate_Half(type: Int) : Destination(type)
class Hole1(type: Int) : Destination(type)
class Ice : Destination(ElementType.ICE)
class Key(type: Int) : Element(type), Removable {

    override fun isCollectable() = true
}

class LadderDown(type: Int) : Destination(type)
class Ladder_Up(type: Int) : Destination(type)
class Moving_Water(type: Int) : Element(type)
class Moving_Wood(type: Int) : Destination(type)
class Red_Button(type: Int) : Destination(type)
class Switch(type: Int) : Destination(type)
class Switch_Crate_Block(type: Int) : Destination(type)
class TeleIn1(type: Int) : Element(type)

class TeleOut1(type: Int) : Element(type) {
    override val imageResId: Int = R.drawable.portal_blue
    override val elementGroup = ElementGroup.TeleportOut
}

class Wall : Element(ElementType.WALL) {
    override val elementGroup = ElementGroup.WALL
    override val imageResId = R.drawable.wallp
}