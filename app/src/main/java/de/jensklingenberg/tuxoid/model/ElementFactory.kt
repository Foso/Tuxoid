package de.jensklingenberg.tuxoid.model

import de.jensklingenberg.tuxoid.data.GameState
import de.jensklingenberg.tuxoid.data.ImageRepository
import de.jensklingenberg.tuxoid.data.LevelHelper
import de.jensklingenberg.tuxoid.data.LoadGame
import de.jensklingenberg.tuxoid.model.element.*
import de.jensklingenberg.tuxoid.model.element.character.NPC
import de.jensklingenberg.tuxoid.model.element.character.Player

class ElementFactory{


    companion object{



        fun mapToElement(levelE: Array<Array<Array<Int>>>): Array<Array<Array<Element>>> {

            val stage = levelE.size
            val cols = levelE[0].size
            val row = levelE[0][0].size


            val elelevelE: Array<Array<Array<Element>>> = LoadGame.test(stage,cols,row)


            levelE.forEachIndexed { index1, arrayOfArrays ->
                arrayOfArrays.forEachIndexed { index2, ints ->
                    ints.forEachIndexed { index3, i ->
                        elelevelE[index1][index2][index3] = elementFactory(i, Coordinate(index1,index2,index3))
                    }
                }
            }



            return elelevelE
        }

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



        fun changeElement(type: Int, group: ElementGroup, element: Element): Element {
            element.typeId = type
            element.setGroup(group)
            element.image = ImageRepository.getImage(type)
            return element
        }

        @JvmStatic
        fun elementFactory(type: Int, coordinate: Coordinate): Element {
            return elementFactory(type, coordinate.z, coordinate.y, coordinate.x)
        }


        @JvmStatic
        fun elementFactory(type: Int, z: Int, y: Int, x: Int): Element {

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
                    GameState.mapDoor.put(1, Coordinate(z, y, x));

                    return Door(type, z, y, x)
                }

                ElementType.DOOR2 -> {
                    return Door(type, z, y, x)
                }

                ElementType.EXIT -> {
                    GameState.setExitPos(z, y, x);

                    return Exit(type, z, y, x)
                }

                ElementType.FISH -> {
                    LevelHelper.game.fishData.addFish()

                    return Fish(z, y, x)
                }

                ElementType.GATE -> {
                    GameState.gate = intArrayOf(z, y, x)

                    return Gate(type, z, y, x)
                }

                ElementType.GATE_HALF -> return Gate_Half(type, z, y, x)

                ElementType.HOLE1 -> return Hole1(type, z, y, x)

                ElementType.KEY1 -> {
                    GameState.mapKey.put(1, intArrayOf(y, x))

                    return Key(type, z, y, x)
                }

                ElementType.KEY2 -> {
                    GameState.mapKey.put(2, intArrayOf(y, x))

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

                ElementType.ICE -> return Ice(z, y, x)

                ElementType.WALL -> return Wall(z, y, x)

                ElementType.PLAYER -> {
                    Player.setPlayPos(z, y, x)
                    return Player(type, z, y, x)
                }

                ElementType.RED_BUTTON -> return Red_Button(type, z, y, x)

                ElementType.SWITCH -> return Switch(type, z, y, x)

                ElementType.SWITCH_CRATE_BLOCK -> return Switch_Crate_Block(type, z, y, x)

                ElementType.TELEIN1 -> {
                    GameState.setTeleInPos(z, y, x)

                    return TeleIn1(type, z, y, x)
                }

                ElementType.TELEOUT1 -> {
                    GameState.setTeleOutPos(z, y, x)

                    return TeleOut1(type, z, y, x)
                }
            }

            return Element(type, z, y, x)
        }
    }

}