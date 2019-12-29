package de.jensklingenberg.tuxoid.data

import android.os.Handler
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
import de.jensklingenberg.tuxoid.model.element.player.PlayerState
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

   // var playX: Int = 0

   // var playY: Int = 0


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

    }

    fun screenTouched(touchY: Int, touchX: Int) {

        playZ = gameState.getPlayerPosition().z


        if (!arrowTimerRunning) {
            val touchDirection = DirectionUtils.getTouchedDirection(touchY, touchX, gameState.getPlayerPosition().x, gameState.getPlayerPosition().y)

            if (touchDirection != Direction.STAY) {
                checkMove(
                        direction = touchDirection,
                        prevObjectTypeId = ElementType.PLAYER,
                        prevCoord = Coordinate(gameState.aktEbene, gameState.getPlayerPosition().y, gameState.getPlayerPosition().x),
                        callingCharacter = gameState.levelData!![gameState.getPlayerPosition()]
                )

                teleport = false
            }

        }
    }

    override fun npcTimerUpdate(npc: Int, type: Int) {

        if (NPC.getMapNpcTimerStatus(npc)) {

            val (ObjectZ, ObjectY, ObjectX) = NPC.getMapNpcPos(npc)

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
        val (mwZ, mwY, mwX) = gameState.moving_Wood

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
            ElementType.DIAMOND_CRATE -> {
                setPos(
                        ElementType.DIAMOND_CRATE,
                        Coordinate(mwZ, mwY, mwX + dirX)
                )
                setPos(
                        ElementType.MOVING_WATER,
                        Coordinate(mwZ, mwY, mwX)
                )
                gameState.setMoving(ElementType.MOVING_WOOD, Coordinate(mwZ, mwY, mwX + dirX))

            }
            ElementType.CROSS_CRATE -> {
                setPos(
                        ElementType.CROSS_CRATE,
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
                    prevObjectTypeId = BACKGROUND,
                    prevCoord = Coordinate(
                            gameState.aktEbene, PlayerState.position.y, PlayerState.position.x
                    ),
                    callingCharacter = elementDataSource.createElement(
                            ElementType.PLAYER, PlayerState.position)
            )

            handler.postDelayed(timer_arrow, 200)

        }
    }


    fun handleNPC(npcCoord: Coordinate, id: Int) {
        OZ = NPC.getMapNpcPos(id).z
        OY = NPC.getMapNpcPos(id).y
        OX = NPC.getMapNpcPos(id).x

        val (z, y, x) = npcCoord

        setPos(
                getOldElementAt(NPC.getMapNpcPos(id)).typeId,
                NPC.getMapNpcPos(id)
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

    /**
     * int newType = Type des neuen Elements
     * int z,x,y = Koordinaten des neuen Elements
     *
     */
    fun setPos(newType: Int, newCoord: Coordinate) {


        val (z, y, x) = newCoord

        val oldType = getOldElementAt(newCoord).typeId//Prüfen ob was auf RedButton steht

        when (oldType) {
            ElementType.SWITCH -> if (ElementType.DIAMOND_CRATE == newType) {
                setPos(ElementType.SWITCH_CRATE_BLOCK, Coordinate(gameState.aktEbene, y, x))
                if (GameState.getGate() != null) {
                    setPos(
                            newType = BACKGROUND,
                            newCoord = GameState.getGate()
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
                OY = gameState.getPlayerPosition().y
                OX = gameState.getPlayerPosition().x
                setPos(
                        getOldElementAt(
                                Coordinate(OZ,
                                        OY,
                                        OX)
                        ).typeId, Coordinate(playZ, gameState.getPlayerPosition().y, gameState.getPlayerPosition().x)
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

                refreshListener?.onRefresh(gameState.levelData!![gameState.aktEbene])
            }
        }
    }


    // Prueft ob Bewegung/Verschiebung mglich ist
    /**
     *
     * direction=Richtung
     * Object= Das Element das die Funktion aufruft
     * z= Z Wert von Object
     * y= Y Wer von Object
     * x= X Wert von Object
     * callingCharacter = Der Character(Player/NPC) der die Funktion gestart
     * hat.
     */
    fun checkMove(
            direction: Direction, prevObjectTypeId: Int, prevCoord: Coordinate,
            callingCharacter: Element
    ) {

        val (z, y, x) = prevCoord

        var dirX = 0
        var dirY = 0
        val aktObject = gameState.levelData!![prevCoord].typeId
        var nextObjectGroup = ElementGroup.EMPTY

        val (playZ1, playY1, playX1) = PlayerState.position


        if (ElementGroup.charNPC == callingCharacter.elementGroup) {

            val npc = (callingCharacter as NPC).npcNumber

            OZ = NPC.getMapNpcPos(npc).z
            OY = NPC.getMapNpcPos(npc).y
            OX = NPC.getMapNpcPos(npc).x
        } else {
            OZ = playZ1
            OY = playY1
            OX = playX1
        }

        when (direction) {

            Direction.LEFT -> dirX = -1

            Direction.RIGHT -> dirX = 1

            Direction.UP -> dirY = -1

            Direction.DOWN -> dirY = 1
        }

        if (isaWall(y, x, dirX, dirY)) {

            stopTimer(callingCharacter.typeId)
        } else {
            nextObjectGroup = getElementAt(Coordinate(z, y + dirY, x + dirX)).elementGroup
        }


        if (getElementAt(prevCoord).isCollectable()) {
            move_To_Collectable(
                    y,
                    x,
                    aktObject,
                    callingCharacter
            )
        }

        if (ElementFactory.destinationsList.contains(getElementAt(prevCoord).typeId)) {

            move_to_destination(
                    direction,
                    prevObjectTypeId,
                    y,
                    x,
                    callingCharacter,
                    dirX,
                    dirY,
                    aktObject
            )
        }

        if (ElementFactory.moveablesList.contains(getElementAt(prevCoord).typeId)) {
            move_moveable(
                    direction,
                    prevCoord,
                    callingCharacter,
                    dirX,
                    dirY,
                    aktObject
            )
        }

        when (getElementAt(prevCoord).elementGroup) {

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


            ElementGroup.TeleportOut -> if (prevObjectTypeId == ElementType.TELEIN1) {
                checkMove(
                        direction, aktObject,
                        Coordinate(GameState.getTeleOutPos().z, y + dirY, x + dirX), callingCharacter
                )
            }
        }
    }

    private fun move_moveable(
            direction: Direction, newCoord: Coordinate, callingCharacter: Element,
            dirX: Int, dirY: Int, aktObject: Int
    ) {

        val (z,y,x) = newCoord

        val nexDes = getElementAt(Coordinate(z, y + dirY, x + dirX)).typeId

        when (aktObject) {

            ElementType.CROSS_CRATE -> {
                val ele = getElementAt(newCoord)

                if ( ele.moveRule.canMove(nexDes)) {
                    checkMove(direction, aktObject, Coordinate(z, y + dirY, x + dirX), callingCharacter)
                }
            }

            ElementType.DIAMOND_CRATE -> {
                val ele = getElementAt(newCoord)
                if ( ele.moveRule.canMove(nexDes)) {
                    checkMove(direction, aktObject, Coordinate(z, y + dirY, x + dirX), callingCharacter)
                }
            }
        }
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

            ElementType.SWITCH -> if (ElementType.DIAMOND_CRATE == Object) {
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
                charX = gameState.getPlayerPosition().x
                charY = gameState.getPlayerPosition().y
            }

            ElementGroup.charNPC -> {
                val npc = (callingCharacter as NPC).npcNumber

                charX = NPC.getMapNpcPos(npc).x
                charY = NPC.getMapNpcPos(npc).y
            }
        }

        // LEFT AND RIGHT
        if (DirectionUtils.DirectionLeftOrRight(direction)) {

            var i = x
            while (i != charX) {

                if (ElementType.HOLE1 == aktObject) {

                    if (i == x) {
                        setPos(
                                getElementAt(
                                       Coordinate( gameState.aktEbene,
                                               y,
                                               x - dirX)
                                ).typeId, Coordinate(gameState.aktEbene + 1, y, x)
                        )
                    } else {
                        setPos(
                                getElementAt(
                                        Coordinate(gameState.aktEbene,
                                                y,
                                                i - dirX)
                                ).typeId, Coordinate(gameState.aktEbene, y, i)
                        )
                    }
                } else {
                    setPos(
                            getElementAt(
                                   Coordinate( gameState.aktEbene,
                                           y,
                                           i - dirX)
                            ).typeId, Coordinate(gameState.aktEbene, y, i)
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
                                getElementAt(
                                       Coordinate( gameState.aktEbene,
                                               y - dirY,
                                               x)
                                ).typeId, Coordinate(gameState.aktEbene + 1, y, x)
                        )
                    } else {
                        setPos(
                                getElementTypeAt(
                                       Coordinate( gameState.aktEbene,
                                               i - dirY,
                                               x)
                                ), Coordinate(gameState.aktEbene, i, x)
                        )
                    }
                } else {
                    setPos(
                            getElementTypeAt(
                                   Coordinate( gameState.aktEbene,
                                           i - dirY,
                                           x)
                            ), Coordinate(gameState.aktEbene, i, x)
                    )
                }
                i = i - dirY - dirX
            }
        }
    }


    // Called when teleport found
    fun move_teleport(direction: Direction, y: Int, x: Int, dirX: Int, dirY: Int) {

        val (tOZ, tOY, tOX) = GameState.getTeleOutPos()


        val (tIZ, tIY, tIX) = GameState.getTeleInPos()


        // Left Right
        if (DirectionUtils.DirectionLeftOrRight(direction)) {

            run {
                var i = x
                while (i != tOX) {

                    if (i == tOX + dirX) {

                        setPos(
                                getElementAt(Coordinate(tIZ, tIY, tIX - dirX)).typeId,
                                Coordinate(tOZ, tOY, tOX + dirX)
                        )
                    } else {
                        setPos(
                                getElementTypeAt(Coordinate(tOZ, tOY, i - dirX)),
                                Coordinate(tOZ, tOY, i)
                        )
                    }
                    i = i - dirX
                }
            }

            var i = tIX - dirX
            while (i != gameState.getPlayerPosition().x) {

                if (gameState.levelData!![tIZ][tIY][i].typeId != 0) {

                    setPos(
                            getElementTypeAt(Coordinate(tIZ, tIY, i - dirX)),
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
                                getElementTypeAt(Coordinate(tIZ, tIY - dirY, tIX)),
                                Coordinate(tOZ, tOY + dirY, tOX)
                        )
                    } else {
                        setPos(
                                getElementTypeAt(Coordinate(tOZ, i - dirY, tOX)),
                                Coordinate(tOZ, i, tOX)
                        )
                    }
                    i = i - dirY
                }
            }

            var i = tIY - dirY
            while (i != gameState.getPlayerPosition().y) {

                if (gameState.levelData!![tIZ][i][tIX].typeId != 0) {
                    setPos(
                            getElementTypeAt(Coordinate(tIZ, i - dirY, tIX)),
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
        if (ElementType.RED_BUTTON == gameState.levelo!![coordinate].typeId) {

            if (ElementType.RED_BUTTON == gameState.levelData!![coordinate].typeId) {
                handler.removeCallbacks(timer_water)
            } else {
                handler.post(timer_water)
            }
        }
    }



    fun getElementTypeAt(coordinate: Coordinate): Int {
        return getElementAt(coordinate).typeId
    }


    fun changeLevel(level: Array3D<Element>, newType: Int, newCoord: Coordinate) {
        level[newCoord] = elementDataSource.createElement(newType, newCoord)
    }


}

private operator fun <Element> Array3D<Element>.get(cord: Coordinate): Element {
    return this[cord.z][cord.y][cord.x]


}

private operator fun <Element> Array3D<Element>.set(cord: Coordinate, value: Element) {
    this[cord.z][cord.y][cord.x] = value
}



