package jensklingenberg.de.tuxoid.model.Element

import android.graphics.Bitmap
import android.util.Log
import jensklingenberg.de.tuxoid.MainActivity
import jensklingenberg.de.tuxoid.model.Element.Character.NPC
import jensklingenberg.de.tuxoid.model.Element.Character.Player
import jensklingenberg.de.tuxoid.model.Element.Collectable.Fish
import jensklingenberg.de.tuxoid.model.Element.Collectable.Key
import jensklingenberg.de.tuxoid.model.Element.Destination.*
import jensklingenberg.de.tuxoid.model.Element.Moveable.Crate_Block
import jensklingenberg.de.tuxoid.model.Element.Moveable.Crate_Blue
import jensklingenberg.de.tuxoid.model.MyImage

open class Element {
    //public String getGroup() {
    //return group;
    //}


    open var image: Bitmap? = null

    //METHODS

    open var type: Int = 0
    private var group: ElementGroup? = null

    //ICollectable
    //Destination
    //Moveable
    //Arrow


    constructor(type: Int, z: Int, y: Int, x: Int) {
        this.type = type
        this.image = MyImage.getImage(type)

    }

    constructor() {}

    open val isRemovable: Boolean
        get() {
            val reMoveable = false
            return reMoveable
        }

    fun setGroup(group: ElementGroup) {
        this.group = group
    }

    open val elementGroup: ElementGroup
        get() = ElementGroup.EMPTY

    companion object {
        private val TAG = Element::class.java.simpleName

        //IMAGE

      @JvmStatic  fun elementFactory(type: Int, z: Int, y: Int, x: Int): Element {

            if (ElementType.ArrowGroup.isInThisGroup(type)) {

                return Arrow.newArrow(type, z, y, x)

            }

            when (type) {



                ElementType.ARROW_UP_RED -> return Arrow.newArrowRed(type, z, y, x)

                ElementType.ARROW_DOWN_RED -> return Arrow.newArrowRed(type, z, y, x)

                ElementType.ARROW_LEFT_RED -> return Arrow.newArrowRed(type, z, y, x)

                ElementType.ARROW_RIGHT_RED -> return Arrow.newArrowRed(type, z, y, x)

                ElementType.CRATE_BLUE -> return Crate_Blue(type, z, y, x)

                ElementType.CRATE_BLOCK -> return Crate_Block(type, z, y, x)

                ElementType.BACKGROUND -> return Background(type, z, y, x)

                ElementType.DOOR1 -> return Door(1, type, z, y, x)

                ElementType.DOOR2 -> return Door(2, type, z, y, x)

                ElementType.EXIT -> return Exit(type, z, y, x)

                ElementType.FISH -> return Fish(type, z, y, x)

                ElementType.GATE -> return Gate(type, z, y, x)

                ElementType.GATE_HALF -> return Gate_Half(type, z, y, x)

                ElementType.HOLE1 -> return Hole1(type, z, y, x)

                ElementType.KEY1 -> return Key(1, type, z, y, x)

                ElementType.KEY2 -> return Key(2, type, z, y, x)

                ElementType.LADDER_DOWN -> return Ladder_Down(type, z, y, x)

                ElementType.LADDER_UP -> return Ladder_Up(type, z, y, x)

                ElementType.MOVING_WOOD -> return Moving_Wood(type, z, y, x)

                ElementType.MOVING_WATER -> return Moving_Water(type, z, y, x)

                ElementType.NPC1 -> return NPC(type, z, y, x, 1)

                ElementType.NPC2 -> return NPC(type, z, y, x, 2)

                ElementType.NPC3 -> return NPC(type, z, y, x, 3)

                ElementType.NPC4 -> return NPC(type, z, y, x, 4)

                ElementType.ICE -> return Ice(type, z, y, x)

                ElementType.WALL -> return Wall(type, z, y, x)

                ElementType.PLAYER -> {
                  Log.d("hhh","ppp")
                  print("PLAYER CREATED")
                  Player.setPlayPos(z,y,x)
                  return Player(type, z, y, x)}

                ElementType.RED_BUTTON -> return Red_Button(type, z, y, x)

                ElementType.SWITCH -> return Switch(type, z, y, x)

                ElementType.SWITCH_CRATE_BLOCK -> return Switch_Crate_Block(type, z, y, x)

                ElementType.TELEIN1 -> return TeleIn1(type, z, y, x)

                ElementType.TELEOUT1 -> return TeleOut1(type, z, y, x)
            }

            return Element(type, z, y, x)
        }

        fun changeElement(type: Int, group: ElementGroup, element: Element): Element {
            element.type = type
            element.setGroup(group)
            element.image = MyImage.getImage(type)
            return element
        }
    }
}