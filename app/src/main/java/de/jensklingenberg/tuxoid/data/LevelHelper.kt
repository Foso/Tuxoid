package de.jensklingenberg.tuxoid.data

import android.os.Handler
import android.util.Log
import de.jensklingenberg.tuxoid.interfaces.IArrow
import de.jensklingenberg.tuxoid.model.Coordinate
import de.jensklingenberg.tuxoid.model.Direction
import de.jensklingenberg.tuxoid.model.element.Arrow
import de.jensklingenberg.tuxoid.model.element.Element
import de.jensklingenberg.tuxoid.model.element.ElementGroup
import de.jensklingenberg.tuxoid.model.element.ElementType
import de.jensklingenberg.tuxoid.model.element.ElementType.Companion.BACKGROUND
import de.jensklingenberg.tuxoid.model.element.ElementType.Companion.WALL
import de.jensklingenberg.tuxoid.model.element.character.NPC
import de.jensklingenberg.tuxoid.model.element.character.Player
import de.jensklingenberg.tuxoid.model.element.character.PlayerState
import de.jensklingenberg.tuxoid.model.element.timer.Timer_Arrow
import de.jensklingenberg.tuxoid.model.element.timer.Timer_Water
import de.jensklingenberg.tuxoid.model.element.timer.Timer_ice
import de.jensklingenberg.tuxoid.model.element.timer.Timer_npc
import de.jensklingenberg.tuxoid.utils.DirectionUtils

class LevelHelper(private val gameState: GameState, private val elementDataSource: ElementDataSource) : Timer_Arrow.TimerClock, Timer_Water.TimerClock, Timer_ice.TimerClock,
        Timer_npc.TimerClock {


    var bMovingDirRight = false
    var teleport = false
    var handler: Handler = Handler()


    var arrowTimerRunning = false

    var iceTimerRunning = false

    var OZ = 0

    var OY = 0

    var OX = 0


    var playZ: Int = 0

    var playX: Int = 0

    var playY: Int = 0


    var refreshListener: RefreshListener? = null

    var timer_water: Timer_Water? = null
    var timer_arrow: Runnable? = null
    var timer_ice: Runnable? = null

    init {
        timer_water = Timer_Water().also {
            it.setListener(this)
        }
        timer_arrow = Timer_Arrow(this)
        timer_ice = Timer_ice(this)
        gameState.aktEbene = 0

    }

    override fun npcTimerUpdate(npc: Int, type: Int) {

        if (NPC.getMapNpcTimerStatus(npc)) {

            val ObjectZ = NPC.getMapNpcPosZ(npc)
            val ObjectY = NPC.getMapNpcPosY(npc)
            val ObjectX = NPC.getMapNpcPosX(npc)

            checkMove(
                    NPC.getMapNpcDirection(npc),
                    BACKGROUND, Coordinate(
                    gameState.aktEbene,
                    ObjectY, ObjectX
            ), elementDataSource.createElement(type, Coordinate(ObjectZ, ObjectY, ObjectX))
            )

            NPC.startTimer(npc, 200, this, type)

        } else {
            NPC.stopTimer(npc)
        }
    }


    override fun iceTimerUpdate() {
        if (iceTimerRunning) {

            checkMove(
                    PlayerState.playerDirection, ElementType.ICE,

                    Coordinate(
                            gameState.aktEbene,
                            PlayerState.position.y,
                            PlayerState.position.x
                    ),
                    elementDataSource.createElement(
                            ElementType.PLAYER, PlayerState.position

                    )
            )

            handler.postDelayed(timer_ice, 200)

        }

    }


    private fun setPlayer(coord: Coordinate) {
        setPos(
                ElementType.PLAYER,
                coord
        )

    }

    override fun waterTimerUpdate() {

        val mapMoving = gameState.mapMoving
        val (mwZ,mwY,mwX) = gameState.moving_Wood

        var dirX = 0

        if (mwX == mapMoving.get(mapMoving.size() - 1)[2]) {
            bMovingDirRight = false

        }

        if (mwX == mapMoving.get(0)[2]) {
            bMovingDirRight = true

        }

        dirX = if (bMovingDirRight) 1 else -1


        when (gameState.levelData!![mwZ][mwY][mwX].typeId) {
            ElementType.PLAYER -> {
                setPlayer(Coordinate(mwZ, mwY, mwX + dirX))
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
                gameState.setMoving(ElementType.MOVING_WOOD, Coordinate(mwZ, mwY, mwX + dirX))

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
                gameState.setMoving(ElementType.MOVING_WOOD, Coordinate(mwZ, mwY, mwX + dirX))

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
                gameState.setMoving(ElementType.MOVING_WOOD, Coordinate(mwZ, mwY, mwX + dirX))


            }


        }

        //refresh();

        /* and here comes the "trick" */
        handler.postDelayed(timer_water, 1000)


    }


    override fun arrowTimerUpdate() {
        if (arrowTimerRunning) {

            checkMove(
                    direction = PlayerState.playerDirection,
                    Object = BACKGROUND,
                    newCoord = Coordinate(
                            gameState.aktEbene, PlayerState.position.y, PlayerState.position.x
                    ),
                    callingCharacter = elementDataSource.createElement(
                            ElementType.PLAYER, PlayerState.position)
            )

            handler.postDelayed(timer_arrow, 200)

        }
    }


    fun handleNPC(npcCoord: Coordinate, id: Int) {
        OZ = NPC.getMapNpcPosZ(id)
        OY = NPC.getMapNpcPosY(id)
        OX = NPC.getMapNpcPosX(id)

        val (z, y, x) = npcCoord

        setPos(
                getOldElementAt(Coordinate(OZ, OY, OX)).typeId,
                Coordinate(OZ, OY, OX)
        )
        gameState.aktEbene = z

        if (getOldElementAt(Coordinate(z, y, x)) is IArrow) {
            NPC.setMapNpcDirection(id, (gameState.levelo!![z][y][x] as Arrow).direction)

            if ((getOldElementAt(Coordinate(z, y, x)) as Arrow).usedStatus == false) {
                changeLevel(gameState.levelo!!, BACKGROUND, Coordinate(z, y, x))
            }

            if (NPC.getMapNpcTimerStatus(id) == false) {

                NPC.setMapNpcTimerStatus(id, true)
                NPC.startTimer(id, 200, this, ElementType.NPC2)
            }
        }
    }

    fun activateArrow(coordinate: Coordinate) {
        val (z, y, x) = coordinate

        /*Sorgt dafür das Arrows aktiviert werden, wenn der Player darauf tritt */
        if (gameState.levelo!![coordinate] is IArrow) {

            PlayerState.playerDirection = (gameState.levelo!![coordinate] as Arrow).direction
            if ((gameState.levelo!![z][y][x] as Arrow).usedStatus == false) {
                changeLevel(gameState.levelo!!, BACKGROUND, Coordinate(z, y, x))
            }

            if (arrowTimerRunning == false) {
                handler.removeCallbacks(timer_arrow)
                arrowTimerRunning = true
                handler.postDelayed(timer_arrow, 200)
            }
        }
    }

    fun move_To_Collectable(y: Int, x: Int, aktObject: Int, callingCharacter: Element) {
        when (aktObject) {
            ElementType.FISH -> {
                setPos(callingCharacter.typeId, Coordinate(gameState.aktEbene, y, x))
                gameState.fishData.eatFish()
            }

            ElementType.KEY1 -> {
                setPos(callingCharacter.typeId, Coordinate(gameState.aktEbene, y, x))
                setPos(
                        BACKGROUND, Coordinate(
                        gameState.aktEbene, gameState.mapDoor.get(1).y,
                        gameState.mapDoor.get(1).x
                )
                )
            }

            ElementType.KEY2 -> {
                setPos(callingCharacter.typeId, Coordinate(gameState.aktEbene, y, x))
                setPos(
                        BACKGROUND, Coordinate(
                        gameState.aktEbene, gameState.mapDoor.get(2).y,
                        gameState.mapDoor.get(2).x
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

        val (z, y, x) = newCoord

        val oldType = getOldElementAt(newCoord).typeId//Prüfen ob was auf RedButton steht

        when (oldType) {
            ElementType.SWITCH -> if (ElementType.CRATE_BLOCK == newType) {
                setPos(ElementType.SWITCH_CRATE_BLOCK, Coordinate(gameState.aktEbene, y, x))
                if (GameState.getGate() != null) {
                    setPos(
                            newType = BACKGROUND,
                            newCoord =  GameState.getGate()
                    )
                }
                changeLevel(
                        gameState.levelo!!,
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
                                Coordinate(OZ,
                                        OY,
                                        OX)
                        ).typeId, Coordinate(playZ, playY, playX)
                )
                gameState.aktEbene = z

                activateArrow(Coordinate(z, y, x))
            }
        }

        when (newType) {
            ElementType.MOVING_WATER -> {
                gameState.levelData!![z][y][x] = elementDataSource.changeElement(
                        ElementType.MOVING_WATER,
                        ElementGroup.EMPTY,
                        gameState.levelData!![z][y][x]
                )

                //MainActivity.getActivity().setImage(y, x, level!![gameState.aktEbene][y][x].image)
                refreshListener?.onRefresh(gameState.levelData!![gameState.aktEbene])
            }

            ElementType.MOVING_WOOD -> {
                gameState.levelData!![z][y][x] = elementDataSource.changeElement(
                        ElementType.MOVING_WOOD, ElementGroup.Destination,
                        gameState.levelData!![z][y][x]
                )

                gameState.setMoving(ElementType.MOVING_WOOD, Coordinate(z, y, x))
                refreshListener?.onRefresh(gameState.levelData!![gameState.aktEbene])
            }
            else -> {
                changeLevel(gameState.levelData!!, newType, newCoord)

                //Prüfen ob was auf RedButton steht
                checkIfRedButtonCovered(Coordinate(z, y, x))

                //imgGameField[y][x].setImageBitmap(level[gameState.aktEbene][y][x].getImage());
                //MainActivity.getActivity().setImage(y, x, level!![gameState.aktEbene][y][x].image)
                refreshListener?.onRefresh(gameState.levelData!![gameState.aktEbene])
            }
        }
    }

    fun screenTouched(touchY: Int, touchX: Int) {
        playZ = PlayerState.position.z
        playX = PlayerState.position.x
        playY = PlayerState.position.y

        if (!arrowTimerRunning) {
            val touchDirection = DirectionUtils.getTouchedDirection(touchY, touchX, playX, playY)
            checkMove(
                    direction = touchDirection,
                    Object = ElementType.PLAYER,
                    newCoord = Coordinate(gameState.aktEbene, playY, playX),
                    callingCharacter = gameState.levelData!![playZ][playY][playX]
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
        val aktObject = gameState.levelData!![z][y][x].typeId
        var nextObjectGroup = ElementGroup.EMPTY

        playZ = PlayerState.position.z
        playX = PlayerState.position.x
        playY = PlayerState.position.y

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
            Log.i(TAG, callingCharacter.typeId.toString())
            stopTimer(callingCharacter.typeId)
        } else {
            nextObjectGroup = getElementAt(Coordinate(z, y + dirY, x + dirX)).elementGroup
        }


        if (ElementFactory.collectableList.contains(getElementAt(z, y, x).typeId)) {
            move_To_Collectable(
                    y,
                    x,
                    aktObject,
                    callingCharacter
            )
        }

        if (ElementFactory.destinationsList.contains(getElementAt(z, y, x).typeId)) {

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

        if (ElementFactory.moveablesList.contains(getElementAt(z, y, x).typeId)) {
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


            ElementGroup.charNPC, ElementGroup.charPlayer -> move_character(
                    direction, z, y, x, callingCharacter, dirX, dirY, aktObject,
                    nextObjectGroup
            )


            ElementGroup.TeleportOut -> if (Object == ElementType.TELEIN1) {
                checkMove(
                        direction, aktObject,
                        Coordinate(GameState.getTeleOutPos().z, y + dirY, x + dirX), callingCharacter
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

        val nexDes = getElementAt(Coordinate(z, y + dirY, x + dirX)).typeId

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
        return ElementFactory.destinationsList.contains(nextObjectDestination) || ElementFactory.arrowList.contains(
                nextObjectDestination
        )
        //return nextObjectDestination == ElementGroup.Destination || nextObjectDestination == ElementGroup.Arrow
    }


    fun Crate_blue_can_move(nextObjectDestination: Int): Boolean {
        return ElementFactory.moveablesList.contains(nextObjectDestination) || ElementFactory.destinationsList.contains(
                nextObjectDestination
        ) || ElementFactory.arrowList.contains(nextObjectDestination)
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
                PlayerState.playerDirection = direction
                checkMove(direction, aktObject, Coordinate(z, y + dirY, x + dirX), callingCharacter)
            } else {
                handler.removeCallbacks(timer_arrow)
            }
        }
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

            ElementType.GATE_HALF -> move(aktObject, direction, y, x, dirX, dirY, callingCharacter)

            ElementType.HOLE1 -> if (ElementType.PLAYER != Object) {
                move(aktObject, direction, y, x, dirX, dirY, callingCharacter)
            }

            ElementType.ICE ->
                // setPos(ElementType.PLAYER, gameState.aktEbene, y, x);
                move(aktObject, direction, y, x, dirX, dirY, callingCharacter)

            ElementType.LADDER_UP -> if (ElementType.PLAYER == Object) {
                gameState.aktEbene--
                setPos(ElementType.PLAYER, Coordinate(gameState.aktEbene, y, x))
                refreshListener?.onRefresh(gameState.levelData!![gameState.aktEbene])
            }

            ElementType.LADDER_DOWN -> if (ElementType.PLAYER == Object) {
                gameState.aktEbene++
                setPos(ElementType.PLAYER, Coordinate(gameState.aktEbene, y, x))
                refreshListener?.onRefresh(gameState.levelData!![gameState.aktEbene])
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
                        GameState.getTeleOutPos(), callingCharacter
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
                                        gameState.aktEbene,
                                        y,
                                        x - dirX
                                ), Coordinate(gameState.aktEbene + 1, y, x)
                        )
                    } else {
                        setPos(
                                getElementTypeAt(
                                        gameState.aktEbene,
                                        y,
                                        i - dirX
                                ), Coordinate(gameState.aktEbene, y, i)
                        )
                    }
                } else {
                    setPos(
                            getElementTypeAt(
                                    gameState.aktEbene,
                                    y,
                                    i - dirX
                            ), Coordinate(gameState.aktEbene, y, i)
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
                                        gameState.aktEbene,
                                        y - dirY,
                                        x
                                ), Coordinate(gameState.aktEbene + 1, y, x)
                        )
                    } else {
                        setPos(
                                getElementTypeAt(
                                        gameState.aktEbene,
                                        i - dirY,
                                        x
                                ), Coordinate(gameState.aktEbene, i, x)
                        )
                    }
                } else {
                    setPos(
                            getElementTypeAt(
                                    gameState.aktEbene,
                                    i - dirY,
                                    x
                            ), Coordinate(gameState.aktEbene, i, x)
                    )
                }
                i = i - dirY - dirX
            }
        }
    }


    // Called when teleport found
    fun move_teleport(direction: Direction, y: Int, x: Int, dirX: Int, dirY: Int) {

        val (tOZ,tOY,tOX) = GameState.getTeleOutPos()


        val (tIZ,tIY,tIX) = GameState.getTeleInPos()



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

                if (gameState.levelData!![tIZ][tIY][i].typeId != 0) {

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

                if (gameState.levelData!![tIZ][i][tIX].typeId != 0) {
                    setPos(
                            getElementTypeAt(tIZ, i - dirY, tIX),
                            Coordinate(tIZ, i, tIX)
                    )
                } else {
                    break
                }
                i -= dirY
            }
        }

        refreshListener?.onRefresh(gameState.levelData!![gameState.aktEbene])

    }


    fun isaWall(y: Int, x: Int, dirX: Int, dirY: Int): Boolean {
        return (x + dirX == -1
                || x + dirX >= gameState.levelData!![gameState.aktEbene][0].size
                || y + dirY == -1
                || y + dirY >= gameState.levelData!![gameState.aktEbene].size)
                || getElementAt(gameState.aktEbene, y + dirY, x + dirX).elementGroup == ElementGroup.WALL
                || getElementAt(gameState.aktEbene, y + dirY, x + dirX).elementGroup == ElementGroup.WALL
    }

    fun getOldElementAt(cord: Coordinate): Element {
        return gameState.levelo!![cord]
    }


    fun getElementAt(coordinate: Coordinate): Element {
        return getElementAt(coordinate.z, coordinate.y, coordinate.x)
    }


    fun getElementAt(z: Int, y: Int, x: Int): Element {
        return gameState.levelData!![z][y][x]
    }

    fun checkIfRedButtonCovered(coordinate: Coordinate) {
        if (ElementType.RED_BUTTON == gameState.levelo!![coordinate.z][coordinate.y][coordinate.x].typeId) {

            if (ElementType.RED_BUTTON == gameState.levelData!![coordinate.z][coordinate.y][coordinate.x].typeId) {
                handler.removeCallbacks(timer_water)
            } else {
                handler.post(timer_water)
            }
        }
    }

    fun getElementTypeAt(z: Int, y: Int, x: Int): Int {
        return getElementAt(z, y, x).typeId
    }


    fun changeLevel(level: Array<Array<Array<Element>>>, newType: Int, newCoord: Coordinate) {
        level[newCoord] = elementDataSource.createElement(newType, newCoord)
    }


}

private operator fun <Element> Array3D<Element>.get(cord: Coordinate): Element {
    return this[cord.z][cord.y][cord.x]


}

private operator fun <Element> Array3D<Element>.set(cord: Coordinate, value: Element) {
    this[cord.z][cord.y][cord.x] = value
}



