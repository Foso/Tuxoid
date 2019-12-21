package jensklingenberg.de.tuxoid

import android.os.Handler
import android.util.Log
import jensklingenberg.de.tuxoid.interfaces.Removable
import jensklingenberg.de.tuxoid.model.Coordinate
import jensklingenberg.de.tuxoid.model.Direction
import jensklingenberg.de.tuxoid.model.element.*
import jensklingenberg.de.tuxoid.model.element.character.NPC
import jensklingenberg.de.tuxoid.model.element.character.Player
import jensklingenberg.de.tuxoid.model.element.ElementType.*
import jensklingenberg.de.tuxoid.model.element.Timer.Timer_Arrow
import jensklingenberg.de.tuxoid.model.element.Timer.Timer_Water
import jensklingenberg.de.tuxoid.model.element.Timer.Timer_ice
import jensklingenberg.de.tuxoid.model.element.Timer.Timer_npc
import jensklingenberg.de.tuxoid.model.Game
import jensklingenberg.de.tuxoid.utils.DirectionUtils

class LevelHelper() : Timer_Arrow.TimerClock, Timer_Water.TimerClock, Timer_ice.TimerClock,
    Timer_npc.TimerClock {


    var bMovingDirRight = false
    var teleport = false
    var handler: Handler? = null
    @JvmField
    var aktLevel = 8
    @JvmField
    var aktEbene = 1
    @JvmField
    var level: Array<Array<Array<Element>>>? = null
    @JvmField
    var levelo: Array<Array<Array<Element>>>? = null

    @JvmField
    var arrowTimerRunning = false
    @JvmField
    var iceTimerRunning = false

    @JvmField
    var OZ = 0

    @JvmField
    var OY = 0
    @JvmField
    var OX = 0

    @JvmField
    var playZ: Int = 0
    @JvmField
    var playX: Int = 0
    @JvmField
    var playY: Int = 0


    var listener: Listener? = null

    var timer_water: Runnable? = null
    var timer_arrow: Runnable? = null
    var timer_ice: Runnable? = null


    override fun npcTimerUpdate(npc: Int, type: Int) {

        if (NPC.getMapNpcTimerStatus(npc)) {

            val ObjectZ = NPC.getMapNpcPosZ(npc)
            val ObjectY = NPC.getMapNpcPosY(npc)
            val ObjectX = NPC.getMapNpcPosX(npc)

            checkMove(
                NPC.getMapNpcDirection(npc),
                ElementType.BACKGROUND, Coordinate(
                    aktEbene,
                    ObjectY, ObjectX
                ), Element.elementFactory(type, ObjectZ, ObjectY, ObjectX)
            )

            //  this.mainActivity.handler.postDelayed(this, 300);

            NPC.startTimer(npc, 200, this, type)

        } else {
            NPC.stopTimer(npc)
        }
    }

    fun onDrag(
        cord: Coordinate,
        dragElement: Element
    ) {

        if (getElementAt(cord).type == ElementType.BACKGROUND) {

            val newElement = Element.changeElement(
                dragElement.type,
                dragElement.elementGroup,
                dragElement
            )

            level!![cord.z][cord.y][cord.x] = newElement

            if (newElement is Removable) {
                levelo!![cord.z][cord.y][cord.x] =  Element.elementFactory(ElementType.BACKGROUND,cord)

            }else{
                levelo!![cord.z][cord.y][cord.x] = newElement

            }

            listener?.onRefresh()


        }
    }


    override fun iceTimerUpdate() {
        if (iceTimerRunning) {

            checkMove(
                Player.playerDirection, ElementType.ICE,

                Coordinate(
                    aktEbene,
                    Player.position.y,
                    Player.position.x
                ),
                Element.elementFactory(
                    ElementType.PLAYER, Player.position.z, Player.position.y,
                    Player.position.x

                )
            )

            handler?.postDelayed(timer_ice, 200)

        }

    }


    fun setPlayer(z: Int, y: Int, x: Int) {
        setPos(
            ElementType.PLAYER,
            Coordinate(z, y, x)
        )

    }

    override fun waterTimerUpdate() {

        val mapMoving = game.mapMoving
        val mwZ = game.moving_Wood[0]
        val mwY = game.moving_Wood[1]
        val mwX = game.moving_Wood[2]

        var dirX = 0

        if (mwX == mapMoving.get(mapMoving.size() - 1)[2]) {
            bMovingDirRight = false

        }

        if (mwX == mapMoving.get(0)[2]) {
            bMovingDirRight = true

        }

        dirX = if (bMovingDirRight) 1 else -1


        when (level!![mwZ][mwY][mwX].type) {
            ElementType.PLAYER -> {
                setPlayer(mwZ, mwY, mwX + dirX)
                setPos(
                    ElementType.MOVING_WATER,
                    Coordinate(mwZ, mwY, mwX)
                )


            }
            ElementType.CRATE_BLOCK -> {
                setPos(
                    ElementType.CRATE_BLOCK,
                    Coordinate(mwZ, mwY, mwX + dirX)
                )
                setPos(
                    ElementType.MOVING_WATER,
                    Coordinate(mwZ, mwY, mwX)
                )
                game.setMoving(ElementType.MOVING_WOOD, mwZ, mwY, mwX + dirX)

            }
            ElementType.CRATE_BLUE -> {
                setPos(
                    ElementType.CRATE_BLUE,
                    Coordinate(mwZ, mwY, mwX + dirX)
                )
                setPos(
                    ElementType.MOVING_WATER,
                    Coordinate(mwZ, mwY, mwX)
                )
                game.setMoving(ElementType.MOVING_WOOD, mwZ, mwY, mwX + dirX)

            }


            else -> {
                setPos(
                    ElementType.MOVING_WATER,
                    Coordinate(mwZ, mwY, mwX)
                )

                setPos(
                    ElementType.MOVING_WOOD,
                    Coordinate(mwZ, mwY, mwX + dirX)
                )
                game.setMoving(ElementType.MOVING_WOOD, mwZ, mwY, mwX + dirX)


            }


        }

        //refresh();

        /* and here comes the "trick" */
        handler?.postDelayed(timer_water, 1000)


    }


    override fun arrowTimerUpdate() {
        if (arrowTimerRunning) {

            checkMove(
                Player.playerDirection, ElementType.BACKGROUND,
                Coordinate(
                    aktEbene, Player.position.y, Player.position.x
                ),
                Element.elementFactory(
                    ElementType.PLAYER, Player.position.z, Player.position.y,
                    Player.position.x

                )
            )

            handler?.postDelayed(timer_arrow, 200)

        }
    }


    init {
        timer_water = Timer_Water(this)
        timer_arrow = Timer_Arrow(this)
        timer_ice = Timer_ice(this)
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

        if (getOldElementAt(z, y, x).elementGroup == ElementGroup.Arrow) {
            NPC.setMapNpcDirection(id, (levelo!![z][y][x] as Arrow).direction)

            if ((getOldElementAt(z, y, x) as Arrow).usedStatus == false) {
                changeLevel(levelo!!, BACKGROUND, Coordinate(z, y, x))
            }

            if (NPC.getMapNpcTimerStatus(id) == false) {

                NPC.setMapNpcTimerStatus(id, true)
                NPC.startTimer(id, 200, this, ElementType.NPC2)
            }
        }
    }

    fun activateArrow(z: Int, y: Int, x: Int) {
        /*Sorgt dafür das Arrows aktiviert werden, wenn der Player darauf tritt */
        if (levelo!![z][y][x].elementGroup == ElementGroup.Arrow) {

            Player.playerDirection = (levelo!![z][y][x] as Arrow).direction
            if ((levelo!![z][y][x] as Arrow).usedStatus == false) {
                changeLevel(levelo!!, BACKGROUND, Coordinate(z, y, x))
            }

            if (arrowTimerRunning == false) {
                handler?.removeCallbacks(timer_arrow)
                arrowTimerRunning = true
                handler?.postDelayed(timer_arrow, 200)
            }
        }
    }

    fun move_To_Collectable(y: Int, x: Int, aktObject: Int, callingCharacter: Element) {
        when (aktObject) {
            ElementType.FISH -> {
                setPos(callingCharacter.type, Coordinate(aktEbene, y, x))
                game.eatFish()
            }

            ElementType.KEY1 -> {
                setPos(callingCharacter.type, Coordinate(aktEbene, y, x))
                setPos(
                    BACKGROUND, Coordinate(
                        aktEbene, game.mapDoor.get(1).y,
                        game.mapDoor.get(1).x
                    )
                )
            }

            ElementType.KEY2 -> {
                setPos(callingCharacter.type, Coordinate(aktEbene, y, x))
                setPos(
                    BACKGROUND, Coordinate(
                        aktEbene, game.mapDoor.get(2).y,
                        game.mapDoor.get(2).x
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

        val oldType = getOldElementAt(newCoord).type//Prüfen ob was auf RedButton steht

        when (oldType) {
            ElementType.SWITCH -> if (ElementType.CRATE_BLOCK == newType) {
                setPos(ElementType.SWITCH_CRATE_BLOCK, Coordinate(aktEbene, y, x))
                if (Game.getGate() != null) {
                    setPos(
                        newType = BACKGROUND,
                        newCoord = Coordinate(
                            Game.getGate()!![0],
                            Game.getGate()!![1],
                            Game.getGate()!![2]
                        )
                    )
                }
                changeLevel(
                    levelo!!,
                    ElementType.SWITCH_CRATE_BLOCK,
                    newCoord
                )
            }
        }

        when (newType) {
            ElementType.NPC1 -> handleNPC(newCoord, 1)
            ElementType.NPC2 -> handleNPC(newCoord, 2)
            ElementType.NPC3 -> handleNPC(newCoord, 3)
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

                //MainActivity.getActivity().setImage(y, x, level!![aktEbene][y][x].image)
                listener?.onRefresh()
            }

            ElementType.MOVING_WOOD -> {
                level!![z][y][x] = Element.changeElement(
                    ElementType.MOVING_WOOD, ElementGroup.Destination,
                    level!![z][y][x]
                )

                game.setMoving(ElementType.MOVING_WOOD, z, y, x)
                listener?.onRefresh()
            }
            else -> {
                changeLevel(level!!, newType, newCoord)

                //Prüfen ob was auf RedButton steht
                checkIfRedButtonCovered(z, y, x)

                //imgGameField[y][x].setImageBitmap(level[aktEbene][y][x].getImage());
                //MainActivity.getActivity().setImage(y, x, level!![aktEbene][y][x].image)
                listener?.onRefresh()
            }
        }
    }

    fun screenTouched(touchY: Int, touchX: Int) {
        playZ = Player.position.z
        playX = Player.position.x
        playY = Player.position.y

        if (arrowTimerRunning == false) {
            val touchDirection = DirectionUtils.getTouchedDirection(touchY, touchX, playX, playY)
            checkMove(
                direction = touchDirection,
                Object = ElementType.PLAYER,
                newCoord = Coordinate(aktEbene, playY, playX),
                callingCharacter = level!![playZ][playY][playX]
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

        playZ = Player.position.z
        playX = Player.position.x
        playY = Player.position.y

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


        if (Element.collectableList.contains(getElementAt(z, y, x).type)) {
            move_To_Collectable(
                y,
                x,
                aktObject,
                callingCharacter
            )
        }

        if (Element.destinationsList.contains(getElementAt(z, y, x).type)) {
            move_to_destination(
                direction,
                Object,
                y,
                x,
                callingCharacter,
                dirX,
                dirY,
                aktObject
            )
        }

        if (Element.moveablesList.contains(getElementAt(z, y, x).type)) {
            move_moveable(
                direction,
                newCoord,
                callingCharacter,
                dirX,
                dirY,
                aktObject,
                nextObjectGroup
            )
        }




        when (getElementAt(z, y, x).elementGroup) {

            ElementGroup.Arrow -> move_to_arrow(
                direction,
                y,
                x,
                callingCharacter,
                dirX,
                dirY,
                aktObject
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
                    Coordinate(Game.getTeleOutPosZ(), y + dirY, x + dirX), callingCharacter
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

        val nexDes = getElementAt(z, y + dirY, x + dirX).type

        when (aktObject) {

            ElementType.CRATE_BLUE -> if (Crate_blue_can_move(nexDes)) {
                checkMove(direction, aktObject, Coordinate(z, y + dirY, x + dirX), callingCharacter)
            }

            ElementType.CRATE_BLOCK -> if (Crate_block_can_move(nexDes)) {
                checkMove(direction, aktObject, Coordinate(z, y + dirY, x + dirX), callingCharacter)
            }
        }
    }

    fun Crate_block_can_move(nextObjectDestination: Int): Boolean {
        return Element.destinationsList.contains(nextObjectDestination) || Element.arrowList.contains(
            nextObjectDestination
        )
        //return nextObjectDestination == ElementGroup.Destination || nextObjectDestination == ElementGroup.Arrow
    }


    fun Crate_blue_can_move(nextObjectDestination: Int): Boolean {
        return Element.moveablesList.contains(nextObjectDestination) || Element.destinationsList.contains(
            nextObjectDestination
        ) || Element.arrowList.contains(nextObjectDestination)
        // return nextObjectDestination == ElementGroup.Moveable || nextObjectDestination == ElementGroup.Destination || nextObjectDestination == ElementGroup.Arrow
    }


    private fun stopTimer(callingCharacter: Int) {

        when (callingCharacter) {
            ElementType.PLAYER -> arrowTimerRunning = false
            ElementType.NPC1 -> {
                NPC.setMapNpcTimerStatus(1, false)
                NPC.stopTimer(1)
            }

            ElementType.NPC2 -> {
                NPC.setMapNpcTimerStatus(2, false)
                NPC.stopTimer(2)
            }

            ElementType.NPC3 -> {
                NPC.setMapNpcTimerStatus(3, false)
                NPC.stopTimer(3)
            }

        }
    }

    private fun move_character(
        direction: Direction, z: Int, y: Int, x: Int, callingCharacter: Element,
        dirX: Int, dirY: Int, aktObject: Int, nextObjectGroup: ElementGroup
    ) {

        when (aktObject) {

            ElementType.NPC1 -> if (!WALL.equals(nextObjectGroup)) {
                NPC.setMapNpcDirection(1, direction)
                checkMove(direction, aktObject, Coordinate(z, y + dirY, x + dirX), callingCharacter)
            } else {
                NPC.stopTimer(1)
            }

            ElementType.NPC2 -> if (!WALL.equals(nextObjectGroup)) {
                NPC.setMapNpcDirection(2, direction)
                checkMove(direction, aktObject, Coordinate(z, y + dirY, x + dirX), callingCharacter)
            } else {
                NPC.stopTimer(2)
            }

            ElementType.NPC3 -> if (!WALL.equals(nextObjectGroup)) {
                NPC.setMapNpcDirection(3, direction)
                checkMove(direction, aktObject, Coordinate(z, y + dirY, x + dirX), callingCharacter)
            } else {
                NPC.stopTimer(3)
            }

            ElementType.PLAYER -> if (!WALL.equals(nextObjectGroup)) {
                Player.playerDirection = direction
                checkMove(direction, aktObject, Coordinate(z, y + dirY, x + dirX), callingCharacter)
            } else {
                handler?.removeCallbacks(timer_arrow)
            }
        }
    }


    fun setLevel(
        levelE: Array<Array<Array<Element>>>,
        levelEo: Array<Array<Array<Element>>>
    ) {
        this.level = levelE
        this.levelo = levelEo
    }


    fun move_to_destination(
        direction: Direction, Object: Int, y: Int, x: Int,
        callingCharacter: Element, dirX: Int, dirY: Int, aktObject: Int
    ) {

        when (aktObject) {

            ElementType.BACKGROUND ->

                if (teleport) {
                    move_teleport(direction, y, x, dirX, dirY)
                } else {
                    move(aktObject, direction, y, x, dirX, dirY, callingCharacter)
                }

            ElementType.GATE_HALF -> move(aktObject, direction, y, x, dirX, dirY, callingCharacter)

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
                        Game.getTeleOutPosZ(), Game.getTeleOutPosY(),
                        Game.getTeleOutPosX()
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

        val tOZ = Game.getTeleOutPosZ()
        val tOY = Game.getTeleOutPosY()
        val tOX = Game.getTeleOutPosX()

        val tIZ = Game.getTeleInPosZ()
        val tIY = Game.getTeleInPosY()
        val tIX = Game.getTeleInPosX()

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
        ||getElementAt(aktEbene, y + dirY, x + dirX).elementGroup==ElementGroup.WALL
                ||getElementAt(aktEbene, y + dirY, x + dirX).elementGroup==ElementGroup.WALL
    }

    fun getOldElementAt(cord: Coordinate): Element {
        return getOldElementAt(cord.z, cord.y, cord.x)
    }

    fun getOldElementAt(z: Int, y: Int, x: Int): Element {
        return levelo!![z][y][x]
    }

    fun getElementAt(cord: Coordinate): Element {
        return getElementAt(cord.z, cord.y, cord.x)
    }


    fun getElementAt(z: Int, y: Int, x: Int): Element {
        return level!![z][y][x]
    }

    fun checkIfRedButtonCovered(z: Int, y: Int, x: Int) {
        if (ElementType.RED_BUTTON == levelo!![z][y][x].type) {

            if (ElementType.RED_BUTTON == level!![z][y][x].type) {
                handler?.removeCallbacks(timer_water)
            } else {
                handler?.post(timer_water)
            }
        }
    }

    fun getElementTypeAt(z: Int, y: Int, x: Int): Int {
        return getElementAt(z, y, x).type
    }


    fun changeLevel(level: Array<Array<Array<Element>>>, newType: Int, newCoord: Coordinate) {
        val z = newCoord.z
        val y = newCoord.y
        val x = newCoord.x

        level[z][y][x] = Element.elementFactory(newType, z, y, x)
    }

    companion object {
        @JvmStatic
        val game = Game()

    }

}

interface Listener {
    fun onRefresh()
    // fun onSetPos()
}