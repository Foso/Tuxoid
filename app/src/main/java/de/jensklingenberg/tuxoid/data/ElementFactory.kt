package de.jensklingenberg.tuxoid.data

import de.jensklingenberg.tuxoid.model.Coordinate
import de.jensklingenberg.tuxoid.model.element.*
import de.jensklingenberg.tuxoid.model.element.character.NPC
import de.jensklingenberg.tuxoid.model.element.player.Player
import de.jensklingenberg.tuxoid.model.element.player.PlayerState
import de.jensklingenberg.tuxoid.model.element.crosscrate.CrossCrate
import de.jensklingenberg.tuxoid.model.element.diamondcrate.DiamondCrate
import de.jensklingenberg.tuxoid.model.element.ladder.LadderDown
import de.jensklingenberg.tuxoid.model.element.ladder.LadderUp
import de.jensklingenberg.tuxoid.model.element.teleport.TeleIn1
import de.jensklingenberg.tuxoid.model.element.teleport.TeleOut1

class ElementFactory : ElementDataSource {


    override fun createElement(type: Int, coordinate: Coordinate): Element {
        return parseElement(type, coordinate)
    }

    override fun changeElement(type: Int, group: ElementGroup, element: Element): Element {
        return Companion.elementFactory(type)
    }

    companion object {


        fun mapToElement(levelE: Array<Array<Array<Int>>>): Array<Array<Array<Element>>> {

            val stage = levelE.size
            val cols = levelE[0].size
            val row = levelE[0][0].size


            val elelevelE: Array<Array<Array<Element>>> = GameLoader.test(stage, cols, row)


            levelE.forEachIndexed { index1, arrayOfArrays ->
                arrayOfArrays.forEachIndexed { index2, ints ->
                    ints.forEachIndexed { index3, i ->
                        elelevelE[index1][index2][index3] = elementFactory(i)
                    }
                }
            }



            return elelevelE
        }

        val arrowList = ArrayList<Int>().apply {
            add(ElementType.ARROW_DOWN)
            add(ElementType.ARROW_DOWN_RED)
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
            addAll(arrowList)
        }

        val moveablesList = ArrayList<Int>().apply {
            add(ElementType.DIAMOND_CRATE)
            add(ElementType.CROSS_CRATE)
        }


        private fun changeElement(type: Int, group: ElementGroup, element: Element): Element {
            element.typeId = type
            element.setGroup(group)
            //  element.image = ImageRepository.getImage(type)
            return element
        }


        @JvmStatic
        fun parseElement(type: Int, coordinate: Coordinate): Element {
            val element = elementFactory(type)

            when (type) {
                ElementType.DOOR1 -> {
                    GameState.mapDoor.put(1, coordinate)
                }
                ElementType.EXIT -> {
                    GameState.setExitPos(coordinate)
                }
                ElementType.FISH -> {
                    GameState.getInstance().fishData.addFish()
                }
                ElementType.GATE -> {
                    GameState.gate = coordinate
                }
                ElementType.KEY1 -> {
                    GameState.mapKey.put(1, coordinate)
                }

                ElementType.KEY2 -> {
                    GameState.mapKey.put(2, coordinate)
                    return Key(type)
                }
                ElementType.NPC1 -> {
                    NPC.mapNpc.put(1, coordinate)

                    if (NPC.mapNpcTimerStatus[1] == null) {
                        NPC.setMapNpcTimerStatus(1, false)
                    }
                }

                ElementType.NPC2 -> {
                    NPC.mapNpc.put(2, coordinate)

                    if (NPC.mapNpcTimerStatus[2] == null) {
                        NPC.setMapNpcTimerStatus(2, false)
                    }
                }

                ElementType.NPC3 -> {
                    NPC.mapNpc.put(3, coordinate)

                    if (NPC.mapNpcTimerStatus[3] == null) {
                        NPC.setMapNpcTimerStatus(3, false)
                    }
                }

                ElementType.NPC4 -> {
                    NPC.mapNpc.put(4, coordinate)

                    if (NPC.mapNpcTimerStatus[4] == null) {
                        NPC.setMapNpcTimerStatus(4, false)
                    }
                }

                ElementType.PLAYER -> {
                    PlayerState.setPlayPos(coordinate)
                }
                ElementType.TELEIN1 -> {
                    GameState.setTeleInPos(coordinate)
                }

                ElementType.TELEOUT1 -> {
                    GameState.setTeleOutPos(coordinate)
                }

            }

            return element
        }


        @JvmStatic
        fun elementFactory(type: Int): Element {

            if (ElementType.ArrowGroup.isInThisGroup(type)) {

                return ArrowFactory.newArrow(type)

            }

            when (type) {

                ElementType.ARROW_UP_RED,
                ElementType.ARROW_RIGHT_RED,
                ElementType.ARROW_DOWN_RED,
                ElementType.ARROW_LEFT_RED -> return ArrowFactory.newArrowRed(type)


                ElementType.CROSS_CRATE -> return CrossCrate(type)

                ElementType.DIAMOND_CRATE -> return DiamondCrate(type)

                ElementType.BACKGROUND -> return Background()

                ElementType.DOOR1, ElementType.DOOR2 -> {
                    return Door(type)
                }


                ElementType.EXIT -> {
                    return Exit(type)
                }

                ElementType.FISH -> {
                    return Fish()
                }

                ElementType.GATE -> {
                    return Gate(type)
                }

                ElementType.GATE_HALF -> return Gate_Half(type)

                ElementType.HOLE1 -> return Hole1(type)

                ElementType.KEY1, ElementType.KEY2 -> {
                    return Key(type)
                }

                ElementType.LADDER_DOWN -> return LadderDown(type)

                ElementType.LADDER_UP -> return LadderUp(type)

                ElementType.MOVING_WOOD -> {

                    return Moving_Wood(type)
                }

                ElementType.MOVING_WATER -> {
                    return Moving_Water(type)
                }

                ElementType.NPC1 -> {
                    return NPC(type, 1)
                }

                ElementType.NPC2 -> return NPC(type, 2)

                ElementType.NPC3 -> return NPC(type, 3)

                ElementType.NPC4 -> return NPC(type, 4)

                ElementType.ICE -> return Ice()

                ElementType.WALL -> return Wall()

                ElementType.PLAYER -> {
                    return Player(type)
                }

                ElementType.RED_BUTTON -> return Red_Button(type)

                ElementType.SWITCH -> return Switch(type)

                ElementType.SWITCH_CRATE_BLOCK -> return Switch_Crate_Block(type)

                ElementType.TELEIN1 -> {
                    return TeleIn1(type)
                }

                ElementType.TELEOUT1 -> {
                    return TeleOut1(type)
                }
            }

            return Element(type)
        }
    }

}