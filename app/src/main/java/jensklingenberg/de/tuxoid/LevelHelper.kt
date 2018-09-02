package jensklingenberg.de.tuxoid

import android.os.Handler
import android.util.Log
import jensklingenberg.de.tuxoid.model.Coordinate
import jensklingenberg.de.tuxoid.model.Direction
import jensklingenberg.de.tuxoid.model.Element.*
import jensklingenberg.de.tuxoid.model.Element.Character.NPC
import jensklingenberg.de.tuxoid.model.Element.Character.Player
import jensklingenberg.de.tuxoid.model.Element.ElementType.*
import jensklingenberg.de.tuxoid.model.Game
import jensklingenberg.de.tuxoid.utils.DirectionUtils
import jensklingenberg.de.tuxoid.utils.MoveUtils

class LevelHelper(){
    var teleport = false
    //var handler = Handler()
    @JvmField var aktLevel = 8
@JvmField var aktEbene = 1
    @JvmField var level: Array<Array<Array<Element>>>? = null
    @JvmField var levelo: Array<Array<Array<Element>>>? = null

    @JvmField var bTimer = false

    @JvmField var OZ = 0

    @JvmField var OY = 0
    @JvmField var OX = 0

    @JvmField var playZ: Int = 0
    @JvmField var playX: Int = 0
    @JvmField var playY: Int = 0




    var listener:Listener?=null

    var timer_water: Runnable? = null
     var timer_arrow: Runnable? = null
     var timer_ice: Runnable? = null

   init {
       //timer_water = Timer_Water(activity)
      // timer_arrow = Timer_Arrow(activity, ElementType.PLAYER)
      // timer_ice = Timer_ice(activity, ElementType.PLAYER)
       aktEbene = 0

   }

    fun handleNPC(npcCoord: Coordinate, id: Int) {
        OZ = NPC.getMapNpcPosZ(id)
        OY = NPC.getMapNpcPosY(id)
        OX = NPC.getMapNpcPosX(id)

        val z = npcCoord.z
        val x = npcCoord.x
        val y = npcCoord.y

        setPos(
            getOldElementAt(OZ, OY, OX).type,
            Coordinate(OZ, OY, OX)
        )
        aktEbene = z

        if (getOldElementAt(z, y, x).elementGroup is Arrow) {
            NPC.setMapNpcDirection(id, (levelo!![z][y][x] as Arrow).direction)

            if ((getOldElementAt(z, y, x) as Arrow).usedStatus == false) {
                changeLevel(levelo!!, BACKGROUND, Coordinate(z, y, x))
            }

            if (NPC.getMapNpcTimerStatus(id) == false) {

                NPC.setMapNpcTimerStatus(id, true)
                NPC.startTimer(id, 200,  MainActivity.getActivity(), ElementType.NPC2)
            }
        }
    }

    fun activateArrow(z: Int, y: Int, x: Int) {
        /*Sorgt dafür das Arrows aktiviert werden, wenn der Player darauf tritt */
        if (levelo!![z][y][x].elementGroup is Arrow) {

            Player.setPlayerDirection((levelo!![z][y][x] as Arrow).direction)
            if ((levelo!![z][y][x] as Arrow).usedStatus == false) {
                changeLevel(levelo!!, BACKGROUND, Coordinate(z, y, x))
            }

            if (bTimer == false) {
                //handler.removeCallbacks(timer_arrow)
                bTimer = true
                //handler.postDelayed(timer_arrow, 200)
            }
        }
    }

    fun move_To_Collectable(y: Int, x: Int, aktObject: Int, callingCharacter: Element) {
        when (aktObject) {
            ElementType.FISH -> {
                setPos(callingCharacter.type, Coordinate(aktEbene, y, x))
                Game.eatFish()
            }

            ElementType.KEY1 -> {
                setPos(callingCharacter.type, Coordinate(aktEbene, y, x))
                setPos(
                    BACKGROUND, Coordinate(
                        aktEbene, Game.mapDoor.get(1).y,
                        Game.mapDoor.get(1).x
                    )
                )
            }

            ElementType.KEY2 -> {
                setPos(callingCharacter.type, Coordinate(aktEbene, y, x))
                setPos(
                    BACKGROUND, Coordinate(
                        aktEbene, Game.mapDoor.get(2).y,
                        Game.mapDoor.get(2).x
                    )
                )
            }
        }
    }

    fun setPos(newType: Int, newCoord: Coordinate) {
        /*
   * int newType = Type des neuen Elements
	 * int z,x,y = Koordinaten des neuen Elements
	 *
	 */

        val z = newCoord.z
        val y = newCoord.y
        val x = newCoord.x

        val oldType = getOldElementAt(z, y, x).type

        if (ElementType.SWITCH == oldType) {
            if (ElementType.CRATE_BLOCK == newType) {
                setPos(ElementType.SWITCH_CRATE_BLOCK, Coordinate(aktEbene, y, x))
                if (Gate.getGate() != null) {
                    setPos(
                        BACKGROUND,
                        Coordinate(Gate.getGate()[0], Gate.getGate()[1], Gate.getGate()[2])
                    )
                }
                changeLevel(
                    levelo!!,
                    ElementType.SWITCH_CRATE_BLOCK,
                    Coordinate(z, y, x)
                )
            }
        }

        when (newType) {
            ElementType.NPC1 -> handleNPC(Coordinate(z, y, x), 1)
            ElementType.NPC2 -> handleNPC(Coordinate(z, y, x), 2)
            ElementType.NPC3 -> handleNPC(Coordinate(z, y, x), 3)
            ElementType.PLAYER -> {
                OZ = playZ
                OY = playY
                OX = playX
                setPos(
                    getOldElementAt(
                        OZ,
                        OY,
                        OX
                    ).type, Coordinate(playZ, playY, playX)
                )
                aktEbene = z

                activateArrow(z, y, x)
            }
        }

        when (newType) {
            ElementType.MOVING_WATER -> {
                level!![z][y][x] = Element.changeElement(
                    ElementType.MOVING_WATER,
                    ElementGroup.EMPTY,
                    level!![z][y][x]
                )

                //imgGameField[y][x].setImageBitmap(getElementAt(aktEbene, y, x).getImage());
                MainActivity.getActivity().setImage(y, x, level!![aktEbene][y][x].image)
            }
            ElementType.MOVING_WOOD -> {
                level!![z][y][x] = Element.changeElement(
                    ElementType.MOVING_WOOD, ElementGroup.Destination,
                    level!![z][y][x]
                )

                Game.setMoving_Wood(z, y, x)
                //imgGameField[y][x].setImageBitmap(level[aktEbene][y][x].getImage());
                MainActivity.getActivity().setImage(y, x, level!![aktEbene][y][x].image)
            }
            else -> {
                changeLevel(level!!, newType, Coordinate(z, y, x))

                //Prüfen ob was auf RedButton steht
                checkIfRedButtonCovered(z, y, x)

                //imgGameField[y][x].setImageBitmap(level[aktEbene][y][x].getImage());
                MainActivity.getActivity().setImage(y, x, level!![aktEbene][y][x].image)
            }
        }
    }

fun screenTouched(y: Int, x: Int) {
    playZ = Player.getPosition().z
    playX = Player.getPosition().x
    playY = Player.getPosition().y

    if (bTimer == false) {
        val touchDirection =
            DirectionUtils.getTouchedDirection(y, x, playX, playY)
        checkMove(
            touchDirection,
            ElementType.PLAYER,
            Coordinate(aktEbene, playY, playX),
            level!![playZ][playY][playX]
        )

        teleport = false
    }
}


    // Prueft ob Bewegung/Verschiebung mglich ist
    fun checkMove(
        direction: Direction, Object: Int, newCoord: Coordinate,
        callingCharacter: Element
    ) {

        val z = newCoord.z
        val y = newCoord.y
        val x = newCoord.x
val TAG = "HH"
        Log.d(TAG, "checkMove: $z $y $x $direction")

        if (Direction.STAY == direction) {
            return
        }

        /*
     * direction=Richtung
		 * Object= Das Element das die Funktion aufruft
		 * z= Z Wert von Object
		 * y= Y Wer von Object
		 * x= X Wert von Object
		 * callingCharacter = Der Character(Player/NPC) der die Funktion gestart
		 * hat.
		 */

        var dirX = 0
        var dirY = 0
        val aktObject = level!![z][y][x].type
        var nextObjectGroup = ElementGroup.EMPTY

        playZ = Player.getPosition().z
        playX = Player.getPosition().x
        playY = Player.getPosition().y

        if (ElementGroup.charNPC == callingCharacter.elementGroup) {

            val npc = (callingCharacter as NPC).npcNumber

            OZ = NPC.getMapNpcPosZ(npc)
            OY = NPC.getMapNpcPosY(npc)
            OX = NPC.getMapNpcPosX(npc)
        } else {
            OZ = playZ
            OY = playY
            OX = playX
        }
        Log.i(TAG, "checkMove: $direction")
        when (direction) {

            Direction.LEFT -> dirX = -1

            Direction.RIGHT -> dirX = 1

            Direction.UP -> dirY = -1

            Direction.DOWN -> dirY = 1
        }

        if (isaWall(y, x, dirX, dirY)) {
            Log.i(TAG, callingCharacter.type.toString())
            stopTimer(callingCharacter.type)
        } else {
            nextObjectGroup = getElementAt(z, y + dirY, x + dirX).elementGroup
        }

        when (getElementAt(z, y, x).elementGroup) {

            is Arrow -> move_to_arrow(
                direction,
                y,
                x,
                callingCharacter,
                dirX,
                dirY,
                aktObject
            )

            ElementGroup.Collectable -> move_To_Collectable(
                y,
                x,
                aktObject,
                callingCharacter
            )

            ElementGroup.Destination -> move_to_destination(
                direction,
                Object,
                y,
                x,
                callingCharacter,
                dirX,
                dirY,
                aktObject
            )

            ElementGroup.Moveable -> move_moveable(
                direction,
                newCoord,
                callingCharacter,
                dirX,
                dirY,
                aktObject,
                nextObjectGroup
            )

            ElementGroup.charNPC -> move_character(
                direction, z, y, x, callingCharacter, dirX, dirY, aktObject,
                nextObjectGroup
            )

            ElementGroup.charPlayer -> move_character(
                direction, z, y, x, callingCharacter, dirX, dirY, aktObject,
                nextObjectGroup
            )
            ElementGroup.TeleportOut -> if (Object == ElementType.TELEIN1) {
                checkMove(
                    direction, aktObject,
                    Coordinate(TeleOut.getTeleOutPosZ(), y + dirY, x + dirX), callingCharacter
                )
            }
        }
    }

    private fun move_moveable(
        direction: Direction, newCoord: Coordinate, callingCharacter: Element,
        dirX: Int, dirY: Int, aktObject: Int, nextObjectDestination: ElementGroup
    ) {

        val z = newCoord.z
        val y = newCoord.y
        val x = newCoord.x

        when (aktObject) {

            ElementType.CRATE_BLUE -> if (MoveUtils.Crate_blue_can_move(nextObjectDestination)) {
                checkMove(direction, aktObject, Coordinate(z, y + dirY, x + dirX), callingCharacter)
            }

            ElementType.CRATE_BLOCK -> if (MoveUtils.Crate_block_can_move(nextObjectDestination)) {
                checkMove(direction, aktObject, Coordinate(z, y + dirY, x + dirX), callingCharacter)
            }
        }
    }


    private fun stopTimer(callingCharacter: Int) {

        if (ElementType.PLAYER == callingCharacter) {
            bTimer = false
        }

        if (ElementType.NPC1 == callingCharacter) {
            NPC.setMapNpcTimerStatus(1, false)
            NPC.stopTimer(1)
        }

        if (ElementType.NPC2 == callingCharacter) {
            NPC.setMapNpcTimerStatus(2, false)
            NPC.stopTimer(2)
        }

        if (ElementType.NPC3 == callingCharacter) {
            NPC.setMapNpcTimerStatus(3, false)
            NPC.stopTimer(3)
        }
    }

    private fun move_character(
        direction: Direction, z: Int, y: Int, x: Int, callingCharacter: Element,
        dirX: Int, dirY: Int, aktObject: Int, nextObjectGroup: ElementGroup
    ) {

        when (aktObject) {

            ElementType.NPC1 -> if (!WALL.equals(nextObjectGroup) ) {
                NPC.setMapNpcDirection(1, direction)
                checkMove(direction, aktObject, Coordinate(z, y + dirY, x + dirX), callingCharacter)
            } else {
                NPC.stopTimer(1)
            }

            ElementType.NPC2 -> if (!WALL.equals(nextObjectGroup) ) {
                NPC.setMapNpcDirection(2, direction)
                checkMove(direction, aktObject, Coordinate(z, y + dirY, x + dirX), callingCharacter)
            } else {
                NPC.stopTimer(2)
            }

            ElementType.NPC3 -> if (!WALL.equals(nextObjectGroup) ) {
                NPC.setMapNpcDirection(3, direction)
                checkMove(direction, aktObject, Coordinate(z, y + dirY, x + dirX), callingCharacter)
            } else {
                NPC.stopTimer(3)
            }

            ElementType.PLAYER -> if (!WALL.equals(nextObjectGroup) ) {
                Player.setPlayerDirection(direction)
                checkMove(direction, aktObject, Coordinate(z, y + dirY, x + dirX), callingCharacter)
            } else {
                //handler.removeCallbacks(timer_arrow)
            }
        }
    }


    fun setLevel(
        levelE: Array<Array<Array<Element>>>,
        levelEo: Array<Array<Array<Element>>>
    ) {
        this.level = levelE
        this.levelo=levelEo
    }


    fun move_to_destination(
        direction: Direction, Object: Int, y: Int, x: Int,
        callingCharacter: Element, dirX: Int, dirY: Int, aktObject: Int
    ) {

        when (aktObject) {

            BACKGROUND ->

                if (teleport) {
                    move_teleport(direction, y, x, dirX, dirY)
                } else {
                    move(aktObject, direction, y, x, dirX, dirY, callingCharacter)
                }

            GATE_HALF -> move(aktObject, direction, y, x, dirX, dirY, callingCharacter)

            ElementType.HOLE1 -> if (ElementType.PLAYER != Object) {
                move(aktObject, direction, y, x, dirX, dirY, callingCharacter)
            }

            ElementType.ICE ->
                // setPos(ElementType.PLAYER, aktEbene, y, x);
                move(aktObject, direction, y, x, dirX, dirY, callingCharacter)

            ElementType.LADDER_UP -> if (ElementType.PLAYER == Object) {
                aktEbene--
                setPos(ElementType.PLAYER, Coordinate(aktEbene, y, x))
                listener?.onRefresh()
            }

            ElementType.LADDER_DOWN -> if (ElementType.PLAYER == Object) {
                aktEbene++
                setPos(ElementType.PLAYER, Coordinate(aktEbene, y, x))
                listener?.onRefresh()
            }

            ElementType.MOVING_WOOD -> move(
                aktObject,
                direction,
                y,
                x,
                dirX,
                dirY,
                callingCharacter
            )

            ElementType.TELEIN1 -> {
                teleport = true
             checkMove(
                    direction, aktObject,
                    Coordinate(
                        TeleOut.getTeleOutPosZ(), TeleOut.getTeleOutPosY(),
                        TeleOut.getTeleOutPosX()
                    ), callingCharacter
                )
            }

            ElementType.RED_BUTTON -> if (teleport) {
                move_teleport(direction, y, x, dirX, dirY)
            } else {
                move(aktObject, direction, y, x, dirX, dirY, callingCharacter)
            }

            ElementType.SWITCH -> if (ElementType.CRATE_BLOCK == Object) {
                move(aktObject, direction, y, x, dirX, dirY, callingCharacter)
            }

            ElementType.SWITCH_CRATE_BLOCK -> move(
                aktObject,
                direction,
                y,
                x,
                dirX,
                dirY,
                callingCharacter
            )
        }
    }
    
    
    
    fun move_to_arrow(
        direction: Direction, y: Int, x: Int, callingCharacter: Element, dirX: Int,
        dirY: Int, aktObject: Int
    ) {

        if (teleport) {
            move_teleport(direction, y, x, dirX, dirY)
        } else {
            move(aktObject, direction, y, x, dirX, dirY, callingCharacter)
        }
    }

    // MOVE BACKGROUND, HOLE, RED_BUTTON
    fun move(
        aktObject: Int, direction: Direction, y: Int, x: Int, dirX: Int, dirY: Int,
        callingCharacter: Element
    ) {

        var charX = 0
        var charY = 0

        when (callingCharacter.elementGroup) {

            ElementGroup.charPlayer -> {
                charX = playX
                charY = playY
            }

            ElementGroup.charNPC -> {
                val npc = (callingCharacter as NPC).npcNumber

                charX = NPC.getMapNpcPosX(npc)
                charY = NPC.getMapNpcPosY(npc)
            }
        }

        // LEFT AND RIGHT
        if (DirectionUtils.DirectionLeftOrRight(direction)) {

            var i = x
            while (i != charX) {

                if (ElementType.HOLE1 == aktObject) {

                    if (i == x) {
                        setPos(
                            getElementTypeAt(
                                aktEbene,
                                y,
                                x - dirX
                            ), Coordinate(aktEbene + 1, y, x)
                        )
                    } else {
                        setPos(
                            getElementTypeAt(
                                aktEbene,
                                y,
                                i - dirX
                            ), Coordinate(aktEbene, y, i)
                        )
                    }
                } else {
                    setPos(
                        getElementTypeAt(
                            aktEbene,
                            y,
                            i - dirX
                        ), Coordinate(aktEbene, y, i)
                    )
                }
                i = i - dirX - dirY
            }
        } else {

            // UP AND DOWN
            var i = y
            while (i != charY) {

                if (ElementType.HOLE1 == aktObject) {

                    if (i == y) {
                        setPos(
                            getElementTypeAt(
                                aktEbene,
                                y - dirY,
                                x
                            ), Coordinate(aktEbene + 1, y, x)
                        )
                    } else {
                        setPos(
                            getElementTypeAt(
                                aktEbene,
                                i - dirY,
                                x
                            ), Coordinate(aktEbene, i, x)
                        )
                    }
                } else {
                    setPos(
                        getElementTypeAt(
                            aktEbene,
                            i - dirY,
                            x
                        ), Coordinate(aktEbene, i, x)
                    )
                }
                i = i - dirY - dirX
            }
        }
    }


    // Called when teleport found
    fun move_teleport(direction: Direction, y: Int, x: Int, dirX: Int, dirY: Int) {

        val tOZ = TeleOut.getTeleOutPosZ()
        val tOY = TeleOut.getTeleOutPosY()
        val tOX = TeleOut.getTeleOutPosX()

        val tIZ = TeleIn.getTeleInPosZ()
        val tIY = TeleIn.getTeleInPosY()
        val tIX = TeleIn.getTeleInPosX()

        // Left Right
        if (DirectionUtils.DirectionLeftOrRight(direction)) {

            run {
                var i = x
                while (i != tOX) {

                    if (i == tOX + dirX) {

                        setPos(
                            getElementTypeAt(tIZ, tIY, tIX - dirX),
                            Coordinate(tOZ, tOY, tOX + dirX)
                        )
                    } else {
                        setPos(
                            getElementTypeAt(tOZ, tOY, i - dirX),
                            Coordinate(tOZ, tOY, i)
                        )
                    }
                    i = i - dirX
                }
            }

            var i = tIX - dirX
            while (i != playX) {

                if (level!![tIZ][tIY][i].type != 0) {

                    setPos(
                        getElementTypeAt(tIZ, tIY, i - dirX),
                        Coordinate(tIZ, tIY, i)
                    )
                } else {
                    break
                }
                i = i - dirX
            }
        } else {

            // Up and Down
            run {
                var i = y
                while (i != tOY) {

                    if (i == tOY + dirY) {
                        setPos(
                            getElementTypeAt(tIZ, tIY - dirY, tIX),
                            Coordinate(tOZ, tOY + dirY, tOX)
                        )
                    } else {
                        setPos(
                            getElementTypeAt(tOZ, i - dirY, tOX),
                            Coordinate(tOZ, i, tOX)
                        )
                    }
                    i = i - dirY
                }
            }

            var i = tIY - dirY
            while (i != playY) {

                if (level!![tIZ][i][tIX].type != 0) {
                    setPos(
                        getElementTypeAt(tIZ, i - dirY, tIX),
                        Coordinate(tIZ, i, tIX)
                    )
                } else {
                    break
                }
                i = i - dirY
            }
        }

listener?.onRefresh()

    }



     fun isaWall(y: Int, x: Int, dirX: Int, dirY: Int): Boolean {
        return (x + dirX == -1
                || x + dirX >= level!![aktEbene][0].size
                || y + dirY == -1
                || y + dirY >= level!![aktEbene].size)
    }

    fun getOldElementAt(z: Int, y: Int, x: Int): Element {
        return levelo!![z][y][x]
    }

     fun getElementAt(z: Int, y: Int, x: Int): Element {
        return level!![z][y][x]
    }

     fun checkIfRedButtonCovered(z: Int, y: Int, x: Int) {
        if (ElementType.RED_BUTTON == levelo!![z][y][x].type) {

            if (ElementType.RED_BUTTON == level!![z][y][x].type) {
                //handler.removeCallbacks(timer_water)
            } else {
                //handler.post(timer_water)
            }
        }
    }

    fun getElementTypeAt(z: Int, y: Int, x: Int): Int {
        return getElementAt(z, y, x).type
    }




    companion object {





        @JvmStatic  fun changeLevel(level: Array<Array<Array<Element>>>,newType: Int, newCoord: Coordinate) {
            val z = newCoord.z
            val y = newCoord.y
            val x = newCoord.x

            level[z][y][x] = Element.elementFactory(newType, z, y, x)
        }

        
    }
}

interface Listener{
    fun onRefresh()
   // fun onSetPos()
}