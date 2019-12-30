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
import de.jensklingenberg.tuxoid.model.element.crosscrate.CrossCrate
import de.jensklingenberg.tuxoid.model.element.diamondcrate.DiamondCrate
import de.jensklingenberg.tuxoid.model.element.player.Player
import de.jensklingenberg.tuxoid.model.element.player.PlayerState
import de.jensklingenberg.tuxoid.model.element.teleport.TeleIn1
import de.jensklingenberg.tuxoid.model.element.teleport.TeleOut1
import de.jensklingenberg.tuxoid.model.element.timer.Timer_Water
import de.jensklingenberg.tuxoid.model.element.timer.Timer_ice
import de.jensklingenberg.tuxoid.utils.DirectionUtils

class LevelHelper(private val gameState: GameState, private val elementDataSource: ElementDataSource) : Timer_Water.TimerClock, Timer_ice.TimerClock {


    var bMovingDirRight = false
    var teleport = false
    var handler: Handler = Handler()

    var arrowTimerRunning = false
    var iceTimerRunning = false

    var refreshListener: RefreshListener? = null

    var timer_water: Timer_Water? = null
    var timer_arrow: Runnable? = null
    var timer_ice: Runnable? = null

    init {
        timer_water = Timer_Water().also {
            it.setListener(this)
        }
        timer_arrow = Runnable {

            run {
                if (arrowTimerRunning == true) {
                    this.arrowTimerUpdate()
                }
            }
        }
        timer_ice = Timer_ice(this)

    }

    fun screenTouched(touchY: Int, touchX: Int) {

        if (!arrowTimerRunning) {
            val touchDirection = DirectionUtils.getTouchedDirection(touchY, touchX, gameState.getPlayerPosition().x, gameState.getPlayerPosition().y)

            if (touchDirection != Direction.STAY) {
                val player = gameState.levelData[gameState.getPlayerPosition()]

                var dirX = 0
                var dirY = 0


                when (touchDirection) {
                    Direction.LEFT -> dirX = -1
                    Direction.RIGHT -> dirX = 1
                    Direction.UP -> dirY = -1
                    Direction.DOWN -> dirY = 1
                }


                val nextEle = getElementAt(Coordinate(gameState.aktEbene, gameState.getPlayerPosition().y + dirY, gameState.getPlayerPosition().x + dirX))

                if (player.moveRule.canMove(nextEle.typeId)) {
                    checkMove(
                            direction = touchDirection,
                            prevObjectTypeId = ElementType.PLAYER,
                            prevCoord = Coordinate(gameState.aktEbene, gameState.getPlayerPosition().y, gameState.getPlayerPosition().x),
                            firstElement = gameState.levelData[gameState.getPlayerPosition()]
                    )

                    teleport = false
                }


            }

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


        when (gameState.levelData[mwZ][mwY][mwX].typeId) {
            ElementType.PLAYER -> {
                moveElement(
                        ElementType.PLAYER,
                        Coordinate(mwZ, mwY, mwX + dirX)
                )
                moveElement(
                        ElementType.MOVING_WATER,
                        Coordinate(mwZ, mwY, mwX)
                )


            }
            ElementType.DIAMOND_CRATE -> {
                moveElement(
                        ElementType.DIAMOND_CRATE,
                        Coordinate(mwZ, mwY, mwX + dirX)
                )
                moveElement(
                        ElementType.MOVING_WATER,
                        Coordinate(mwZ, mwY, mwX)
                )
                gameState.setMoving(ElementType.MOVING_WOOD, Coordinate(mwZ, mwY, mwX + dirX))

            }
            ElementType.CROSS_CRATE -> {
                moveElement(
                        ElementType.CROSS_CRATE,
                        Coordinate(mwZ, mwY, mwX + dirX)
                )
                moveElement(
                        ElementType.MOVING_WATER,
                        Coordinate(mwZ, mwY, mwX)
                )
                gameState.setMoving(ElementType.MOVING_WOOD, Coordinate(mwZ, mwY, mwX + dirX))

            }


            else -> {
                moveElement(
                        ElementType.MOVING_WATER,
                        Coordinate(mwZ, mwY, mwX)
                )

                moveElement(
                        ElementType.MOVING_WOOD,
                        Coordinate(mwZ, mwY, mwX + dirX)
                )
                gameState.setMoving(ElementType.MOVING_WOOD, Coordinate(mwZ, mwY, mwX + dirX))


            }


        }

        handler.postDelayed(timer_water, 1000)


    }


    fun arrowTimerUpdate() {
        if (arrowTimerRunning) {

            checkMove(
                    direction = PlayerState.playerDirection,
                    prevObjectTypeId = BACKGROUND,
                    prevCoord = Coordinate(
                            gameState.aktEbene, PlayerState.position.y, PlayerState.position.x
                    ),
                    firstElement = elementDataSource.createElement(
                            ElementType.PLAYER, PlayerState.position)
            )

            handler.postDelayed(timer_arrow, 200)

        }
    }


    fun activateArrow(coordinate: Coordinate) {
        val (z, y, x) = coordinate

        /*Sorgt dafür das Arrows aktiviert werden, wenn der Player darauf tritt */
        if (gameState.levelo[coordinate] is IArrow) {

            PlayerState.playerDirection = (gameState.levelo[coordinate] as Arrow).direction
            if ((gameState.levelo[z][y][x] as Arrow).usedStatus == false) {
                changeLevel(gameState.levelo, BACKGROUND, Coordinate(z, y, x))
            }

            if (arrowTimerRunning == false) {
                handler.removeCallbacks(timer_arrow)
                arrowTimerRunning = true
                handler.postDelayed(timer_arrow, 200)
            }
        }
    }

    fun move_To_Collectable(y: Int, x: Int, aktObject: Int, firstElement: Element) {
        when (aktObject) {
            ElementType.FISH -> {
                moveElement(firstElement.typeId, Coordinate(gameState.aktEbene, y, x))
                gameState.fishData.eatFish()
            }

            ElementType.KEY1 -> {
                moveElement(firstElement.typeId, Coordinate(gameState.aktEbene, y, x))
                moveElement(
                        BACKGROUND, Coordinate(
                        gameState.aktEbene, gameState.mapDoor.get(1).y,
                        gameState.mapDoor.get(1).x
                )
                )
            }

            ElementType.KEY2 -> {
                moveElement(firstElement.typeId, Coordinate(gameState.aktEbene, y, x))
                moveElement(
                        BACKGROUND, Coordinate(
                        gameState.aktEbene, gameState.mapDoor.get(2).y,
                        gameState.mapDoor.get(2).x
                )
                )
            }
        }
    }

    /**
     * @param newType = Type des neuen Elements
     * @param newCoord  Koordinaten des neuen Elements
     *
     */
    fun moveElement(newType: Int, newCoord: Coordinate) {


        // val (z, y, x) = newCoord

        val oldType = getOldElementAt(newCoord).typeId//Prüfen ob was auf RedButton steht

        when (oldType) {
            ElementType.SWITCH -> {
                if (ElementType.DIAMOND_CRATE == newType) {
                    moveElement(ElementType.SWITCH_CRATE_BLOCK, Coordinate(gameState.aktEbene, newCoord.y, newCoord.x))
                    if (gameState.getGate() != null) {
                        moveElement(
                                newType = BACKGROUND,
                                newCoord = gameState.getGate()
                        )
                    }
                    changeLevel(
                            gameState.levelo,
                            ElementType.SWITCH_CRATE_BLOCK,
                            newCoord
                    )
                }
            }
        }

        when (newType) {

            ElementType.PLAYER -> {
                moveElement(
                        getOldElementAt(
                                gameState.getPlayerPosition()
                        ).typeId, Coordinate(gameState.getPlayerPosition().z, gameState.getPlayerPosition().y, gameState.getPlayerPosition().x)
                )
                gameState.aktEbene = newCoord.z

                activateArrow(newCoord)
            }
        }

        when (newType) {
            ElementType.MOVING_WATER -> {
                gameState.levelData[newCoord] = elementDataSource.changeElement(
                        ElementType.MOVING_WATER,
                        ElementGroup.EMPTY,
                        gameState.levelData[newCoord]
                )

                refreshListener?.onRefresh(gameState.levelData[gameState.aktEbene])
            }

            ElementType.MOVING_WOOD -> {
                gameState.levelData[newCoord] = elementDataSource.changeElement(
                        ElementType.MOVING_WOOD, ElementGroup.Destination,
                        gameState.levelData[newCoord]
                )

                gameState.setMoving(ElementType.MOVING_WOOD, newCoord)
                refreshListener?.onRefresh(gameState.levelData[gameState.aktEbene])
            }
            else -> {
                changeLevel(gameState.levelData, newType, newCoord)

                //Prüfen ob was auf RedButton steht
                checkIfRedButtonCovered(newCoord)

                refreshListener?.onRefresh(gameState.levelData[gameState.aktEbene])
            }
        }
    }


    //
    /**
     * Prueft ob Bewegung/Verschiebung mglich ist
     * @param direction where the items are getting moved
     * @param firstElement the Element(Player/NPC) that originally started the function
     */
    fun checkMove(
            direction: Direction, prevObjectTypeId: Int, prevCoord: Coordinate,
            firstElement: Element
    ) {

        val prevElement = gameState.levelData[prevCoord]
        val (z, y, x) = prevCoord

        var dirX = 0
        var dirY = 0
        val aktObjectTypeId = prevElement.typeId
        var nextObjectGroup = ElementGroup.EMPTY

        when (direction) {

            Direction.LEFT -> dirX = -1
            Direction.RIGHT -> dirX = 1
            Direction.UP -> dirY = -1
            Direction.DOWN -> dirY = 1
        }

        if (isaWall(y, x, dirX, dirY)) {
            stopTimer(firstElement.typeId)
        } else {
            nextObjectGroup = getElementAt(Coordinate(z, y + dirY, x + dirX)).elementGroup
        }


        if (getElementAt(prevCoord).isCollectable()) {
            move_To_Collectable(
                    y,
                    x,
                    aktObjectTypeId,
                    firstElement
            )
        }


        if (getElementAt(prevCoord).isReachable()) {
            move_to_destination(
                    direction,
                    prevObjectTypeId,
                    y,
                    x,
                    dirX,
                    dirY,
                    aktObjectTypeId,
                    firstElement
            )
        }

        if (getElementAt(prevCoord).isPushable()) {
            move_moveable(
                    direction,
                    prevCoord,
                    dirX,
                    dirY,
                    prevElement,
                    firstElement
            )
        }

        when (getElementAt(prevCoord)) {
            is Player -> {

                if (!WALL.equals(nextObjectGroup)) {
                    PlayerState.playerDirection = direction
                    checkMove(direction, aktObjectTypeId, Coordinate(prevCoord.z, prevCoord.y + dirY, prevCoord.x + dirX), firstElement)
                } else {
                    handler.removeCallbacks(timer_arrow)
                }

            }
            is TeleOut1 -> {
                if (prevObjectTypeId == ElementType.TELEIN1) {
                    checkMove(
                            direction, aktObjectTypeId,
                            Coordinate(GameState.getTeleOutPos().z, y + dirY, x + dirX), firstElement
                    )
                }
            }
        }
    }

    private fun move_moveable(
            direction: Direction, newCoord: Coordinate, dirX: Int,
            dirY: Int, prevElement: Element, firstElement: Element
    ) {

        val (z, y, x) = newCoord

        val nexDes = getElementAt(Coordinate(z, y + dirY, x + dirX)).typeId

        when (prevElement) {

            is CrossCrate, is DiamondCrate -> {

                if (prevElement.moveRule.canMove(nexDes)) {
                    checkMove(direction, prevElement.typeId, Coordinate(z, y + dirY, x + dirX), firstElement)
                }
            }
        }
    }


    private fun stopTimer(firstElementType: Int) {

        when (firstElementType) {
            ElementType.PLAYER -> arrowTimerRunning = false

        }
    }


    fun move_to_destination(
            direction: Direction, prevObjectTypeId: Int, y: Int, x: Int,
            dirX: Int, dirY: Int, aktObjectTypeId: Int, firstElement: Element
    ) {
        val aktElement = ElementFactory.elementFactory(aktObjectTypeId)

        when (aktElement) {
            is Arrow -> {
                move_to_arrow(
                        direction,
                        y,
                        x,
                        dirX,
                        dirY,
                        aktObjectTypeId,
                        firstElement
                )
            }
        }

        when (aktObjectTypeId) {

            BACKGROUND ->

                if (teleport) {
                    move_teleport(direction, y, x, dirX, dirY)
                } else {
                    move(aktObjectTypeId, direction, y, x, dirX, dirY, firstElement)
                }

            ElementType.GATE_HALF -> move(aktObjectTypeId, direction, y, x, dirX, dirY, firstElement)

            ElementType.HOLE1 -> if (ElementType.PLAYER != prevObjectTypeId) {
                move(aktObjectTypeId, direction, y, x, dirX, dirY, firstElement)
            }

            ElementType.ICE ->
                move(aktObjectTypeId, direction, y, x, dirX, dirY, firstElement)

            ElementType.LADDER_UP -> if (ElementType.PLAYER == prevObjectTypeId) {
                gameState.aktEbene--
                moveElement(ElementType.PLAYER, Coordinate(gameState.aktEbene, y, x))
                refreshListener?.onRefresh(gameState.levelData[gameState.aktEbene])
            }

            ElementType.LADDER_DOWN -> if (ElementType.PLAYER == prevObjectTypeId) {
                gameState.aktEbene++
                moveElement(ElementType.PLAYER, Coordinate(gameState.aktEbene, y, x))
                refreshListener?.onRefresh(gameState.levelData[gameState.aktEbene])
            }

            ElementType.MOVING_WOOD -> move(
                    aktObjectTypeId,
                    direction,
                    y,
                    x,
                    dirX,
                    dirY,
                    firstElement
            )

            ElementType.TELEIN1 -> {
                teleport = true
                checkMove(
                        direction, aktObjectTypeId,
                        GameState.getTeleOutPos(), firstElement
                )
            }

            ElementType.RED_BUTTON -> if (teleport) {
                move_teleport(direction, y, x, dirX, dirY)
            } else {
                move(aktObjectTypeId, direction, y, x, dirX, dirY, firstElement)
            }

            ElementType.SWITCH -> if (ElementType.DIAMOND_CRATE == prevObjectTypeId) {
                move(aktObjectTypeId, direction, y, x, dirX, dirY, firstElement)
            }

            ElementType.SWITCH_CRATE_BLOCK -> move(
                    aktObjectTypeId,
                    direction,
                    y,
                    x,
                    dirX,
                    dirY,
                    firstElement
            )
        }
    }


    fun move_to_arrow(
            direction: Direction, y: Int, x: Int, dirX: Int, dirY: Int,
            aktObject: Int, firstElement: Element
    ) {

        if (teleport) {
            move_teleport(direction, y, x, dirX, dirY)
        } else {
            move(aktObject, direction, y, x, dirX, dirY, firstElement)
        }
    }

    // MOVE BACKGROUND, HOLE, RED_BUTTON
    fun move(
            aktObject: Int, direction: Direction, y: Int, x: Int, dirX: Int, dirY: Int,
            firstElement: Element
    ) {

        var charX = 0
        var charY = 0

        when (firstElement.elementGroup) {

            ElementGroup.charPlayer -> {
                charX = gameState.getPlayerPosition().x
                charY = gameState.getPlayerPosition().y
            }
        }

        // LEFT AND RIGHT
        if (DirectionUtils.directionLeftOrRight(direction)) {

            var i = x
            while (i != charX) {

                if (ElementType.HOLE1 == aktObject) {

                    if (i == x) {
                        moveElement(
                                getElementAt(
                                        Coordinate(gameState.aktEbene,
                                                y,
                                                x - dirX)
                                ).typeId, Coordinate(gameState.aktEbene + 1, y, x)
                        )
                    } else {
                        moveElement(
                                getElementAt(
                                        Coordinate(gameState.aktEbene,
                                                y,
                                                i - dirX)
                                ).typeId, Coordinate(gameState.aktEbene, y, i)
                        )
                    }
                } else {
                    moveElement(
                            getElementAt(
                                    Coordinate(gameState.aktEbene,
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
                        moveElement(
                                getElementAt(
                                        Coordinate(gameState.aktEbene,
                                                y - dirY,
                                                x)
                                ).typeId, Coordinate(gameState.aktEbene + 1, y, x)
                        )
                    } else {
                        moveElement(
                                getElementAt(Coordinate(gameState.aktEbene,
                                        i - dirY,
                                        x)
                                ).typeId, Coordinate(gameState.aktEbene, i, x)
                        )
                    }
                } else {
                    moveElement(
                            getElementAt(Coordinate(gameState.aktEbene,
                                    i - dirY,
                                    x)
                            ).typeId, Coordinate(gameState.aktEbene, i, x)
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
        if (DirectionUtils.directionLeftOrRight(direction)) {

            run {
                var i = x
                while (i != tOX) {

                    if (i == tOX + dirX) {

                        moveElement(
                                getElementAt(Coordinate(tIZ, tIY, tIX - dirX)).typeId,
                                Coordinate(tOZ, tOY, tOX + dirX)
                        )
                    } else {
                        moveElement(
                                getElementAt(Coordinate(tOZ, tOY, i - dirX)).typeId,
                                Coordinate(tOZ, tOY, i)
                        )
                    }
                    i = i - dirX
                }
            }

            var i = tIX - dirX
            while (i != gameState.getPlayerPosition().x) {

                if (gameState.levelData[tIZ][tIY][i].typeId != 0) {

                    moveElement(
                            getElementAt(Coordinate(tIZ, tIY, i - dirX)).typeId,
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
                        moveElement(
                                getElementAt(Coordinate(tIZ, tIY - dirY, tIX)).typeId,
                                Coordinate(tOZ, tOY + dirY, tOX)
                        )
                    } else {
                        moveElement(
                                getElementAt(Coordinate(tOZ, i - dirY, tOX)).typeId,
                                Coordinate(tOZ, i, tOX)
                        )
                    }
                    i = i - dirY
                }
            }

            var i = tIY - dirY
            while (i != gameState.getPlayerPosition().y) {

                if (gameState.levelData[tIZ][i][tIX].typeId != 0) {
                    moveElement(
                            getElementAt(Coordinate(tIZ, i - dirY, tIX)).typeId,
                            Coordinate(tIZ, i, tIX)
                    )
                } else {
                    break
                }
                i -= dirY
            }
        }

        refreshListener?.onRefresh(gameState.levelData[gameState.aktEbene])

    }


    fun isaWall(y: Int, x: Int, dirX: Int, dirY: Int): Boolean {
        return (x + dirX == -1
                || x + dirX >= gameState.levelData[gameState.aktEbene][0].size
                || y + dirY == -1
                || y + dirY >= gameState.levelData[gameState.aktEbene].size)
                || getElementAt(Coordinate(gameState.aktEbene, y + dirY, x + dirX)).elementGroup == ElementGroup.WALL
    }

    fun getOldElementAt(cord: Coordinate): Element {
        return gameState.levelo[cord]
    }


    fun getElementAt(coordinate: Coordinate): Element {
        return gameState.levelData[coordinate]
    }


    fun checkIfRedButtonCovered(coordinate: Coordinate) {
        if (ElementType.RED_BUTTON == gameState.levelo[coordinate].typeId) {

            if (ElementType.RED_BUTTON == gameState.levelData[coordinate].typeId) {
                handler.removeCallbacks(timer_water)
            } else {
                handler.post(timer_water)
            }
        }
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



