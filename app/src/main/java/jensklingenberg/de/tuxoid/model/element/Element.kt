package jensklingenberg.de.tuxoid.model.element

import android.graphics.Bitmap
import jensklingenberg.de.tuxoid.LevelHelper
import jensklingenberg.de.tuxoid.interfaces.ICollectable
import jensklingenberg.de.tuxoid.interfaces.Moveable
import jensklingenberg.de.tuxoid.interfaces.Removable
import jensklingenberg.de.tuxoid.model.Coordinate
import jensklingenberg.de.tuxoid.model.element.character.NPC
import jensklingenberg.de.tuxoid.model.element.character.Player
import jensklingenberg.de.tuxoid.model.element.Destination.*
import jensklingenberg.de.tuxoid.model.Game
import jensklingenberg.de.tuxoid.model.MyImage

open class Element :IElementGroup {


    override val elementGroup: ElementGroup
        get() = ElementGroup.EMPTY
    //public String getGroup() {
    //return group;
    //}


    open var image: Bitmap? = null

    //METHODS

    open var type: Int = 0
    private var group: ElementGroup? = null




    constructor(type: Int, z: Int, y: Int, x: Int) {
        this.type = type
        this.image = MyImage.getImage(type)

    }



    constructor() {}


    fun setGroup(group: ElementGroup) {
        this.group = group
    }



    companion object {
        private val TAG = Element::class.java.simpleName


        val collectableList = ArrayList<Int>().apply {
            add(ElementType.FISH)
            add(ElementType.KEY1)
        }

        val destinationsList = ArrayList<Int>().apply {

            add(ElementType.BACKGROUND)
            add(ElementType.HOLE1)
            add(ElementType.TELEIN1)
            add(ElementType.LADDER_DOWN)
            add(ElementType.LADDER_UP)
            add(ElementType.MOVING_WOOD)
            add(ElementType.RED_BUTTON)
            add(ElementType.SWITCH)

        }

        val moveablesList = ArrayList<Int>().apply {
            add(ElementType.CRATE_BLOCK)
            add(ElementType.CRATE_BLUE)
        }

        val arrowList = ArrayList<Int>().apply {
            add(ElementType.ARROW_DOWN)
            add(ElementType.ARROW_DOWN_RED)
        }

        @JvmStatic  fun elementFactory(type: Int, coordinate: Coordinate): Element {
            return elementFactory(type,coordinate.z,coordinate.y,coordinate.x)
        }



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

                ElementType.BACKGROUND -> return Background(z, y, x)

                ElementType.DOOR1 -> {
                    Game.mapDoor.put(1, Coordinate(z,y,x));

                    return Door(type, z, y, x)
                }

                ElementType.DOOR2 -> {
                    return Door(type, z, y, x)
                }

                ElementType.EXIT -> {
                    Game.setExitPos(z, y, x);

                    return Exit(type, z, y, x)
                }

                ElementType.FISH -> {
                    LevelHelper.game.addFish()

                    return Fish( z, y, x)
                }

                ElementType.GATE -> {
                    Game.gate = intArrayOf(z, y, x)

                    return Gate(type, z, y, x)
                }

                ElementType.GATE_HALF -> return Gate_Half(type, z, y, x)

                ElementType.HOLE1 -> return Hole1(type, z, y, x)

                ElementType.KEY1 -> {
                    Game.mapKey.put(1, intArrayOf(y, x))

                    return Key(type, z, y, x)
                }

                ElementType.KEY2 -> {
                    Game.mapKey.put(2, intArrayOf(y, x))

                    return Key(type, z, y, x)
                }

                ElementType.LADDER_DOWN -> return Ladder_Down(type, z, y, x)

                ElementType.LADDER_UP -> return Ladder_Up(type, z, y, x)

                ElementType.MOVING_WOOD -> {

                    return Moving_Wood(type, z, y, x)
                }

                ElementType.MOVING_WATER -> {
                    return Moving_Water(type, z, y, x)
                }

                ElementType.NPC1 -> return NPC(type, z, y, x, 1)

                ElementType.NPC2 -> return NPC(type, z, y, x, 2)

                ElementType.NPC3 -> return NPC(type, z, y, x, 3)

                ElementType.NPC4 -> return NPC(type, z, y, x, 4)

                ElementType.ICE -> return Ice( z, y, x)

                ElementType.WALL -> return Wall( z, y, x)

                ElementType.PLAYER -> {
                  Player.setPlayPos(z,y,x)
                  return Player(type, z, y, x)}

                ElementType.RED_BUTTON -> return Red_Button(type, z, y, x)

                ElementType.SWITCH -> return Switch(type, z, y, x)

                ElementType.SWITCH_CRATE_BLOCK -> return Switch_Crate_Block(type, z, y, x)

                ElementType.TELEIN1 -> {
                    Game.setTeleInPos(z, y, x)

                    return TeleIn1(type, z, y, x)
                }

                ElementType.TELEOUT1 -> {
                    Game.setTeleOutPos(z, y, x)

                    return TeleOut1(type, z, y, x)
                }
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

class Background(z: Int, y: Int, x: Int) : Element(ElementType.BACKGROUND, z, y, x)
class Door(type: Int, z: Int, y: Int, x: Int) : Element(type, z, y, x), Removable
class Exit(type: Int, z: Int, y: Int, x: Int) : Element(type, z, y, x)
class Fish( z: Int, y: Int, x: Int) : Element(ElementType.FISH, z, y, x), Removable
class Gate(type: Int, z: Int, y: Int, x: Int) : Element(type, z, y, x), Removable
class Gate_Half(type: Int, z: Int, y: Int, x: Int) : Destination(type, z, y, x)
class Hole1(type: Int, z: Int, y: Int, x: Int) : Destination(type, z, y, x)
class Ice( z: Int, y: Int, x: Int) : Destination(ElementType.ICE, z, y, x)
class Key(type: Int, z: Int, y: Int, x: Int) : Element(type, z, y, x), ICollectable, Removable
class Ladder_Down(type: Int, z: Int, y: Int, x: Int) : Destination(type, z, y, x)
class Ladder_Up(type: Int, z: Int, y: Int, x: Int) : Destination(type, z, y, x)
class Moving_Water(type: Int, z: Int, y: Int, x: Int) : Element(type, z, y, x)
class Moving_Wood(type: Int, z: Int, y: Int, x: Int): Destination(type, z, y, x)
class Red_Button(type: Int, z: Int, y: Int, x: Int) : Destination(type, z, y, x)
class Switch(type: Int, z: Int, y: Int, x: Int) : Destination(type, z, y, x)
class Switch_Crate_Block(type: Int, z: Int, y: Int, x: Int) : Destination(type, z, y, x)
class TeleIn1(type: Int, z: Int, y: Int, x: Int) : Element(type, z, y, x)
class Crate_Block(type: Int, z: Int, y: Int, x: Int) : Element(type, z, y, x), Moveable, Removable
class Crate_Blue(type: Int, z: Int, y: Int, x: Int) : Element(type, z, y, x), Moveable, Removable
class TeleOut1(type: Int, z: Int, y: Int, x: Int) : Element(type, z, y, x) {
    override val elementGroup= ElementGroup.TeleportOut
}
class Wall(z: Int, y: Int, x: Int) : Element(ElementType.WALL, z, y, x) {
    override val elementGroup= ElementGroup.WALL
}