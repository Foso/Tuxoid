package de.jensklingenberg.tuxoid.data

import android.os.Handler
import android.util.Log
import de.jensklingenberg.tuxoid.interfaces.Removable
import de.jensklingenberg.tuxoid.model.Coordinate
import de.jensklingenberg.tuxoid.model.Direction
import de.jensklingenberg.tuxoid.model.ElementFactory
import de.jensklingenberg.tuxoid.model.element.*
import de.jensklingenberg.tuxoid.model.element.character.NPC
import de.jensklingenberg.tuxoid.model.element.character.Player
import de.jensklingenberg.tuxoid.model.element.timer.Timer_Arrow
import de.jensklingenberg.tuxoid.model.element.timer.Timer_Water
import de.jensklingenberg.tuxoid.model.element.timer.Timer_ice
import de.jensklingenberg.tuxoid.model.element.timer.Timer_npc
import de.jensklingenberg.tuxoid.model.element.ElementType.Companion.BACKGROUND
import de.jensklingenberg.tuxoid.model.element.ElementType.Companion.WALL
import de.jensklingenberg.tuxoid.utils.DirectionUtils

class LevelHelper() : Timer_Arrow.TimerClock, Timer_Water.TimerClock, Timer_ice.TimerClock,
        Timer_npc.TimerClock {


    var bMovingDirRight = false
    var teleport = false
    var handler: Handler = Handler()
    @JvmField
    var aktLevel = 8
    @JvmField
    var aktEbene = 1
    @JvmField
    var levelData: Array<Array<Array<Element>>>? = null
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


    var refreshListener: RefreshListener? = null

    var timer_water: Timer_Water? = null
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
            ), ElementFactory.elementFactory(type, ObjectZ, ObjectY, ObjectX)
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

        if (getElementAt(cord).typeId == ElementType.BACKGROUND) {

            val newElement = ElementFactory.changeElement(
                    dragElement.typeId,
                    dragElement.elementGroup,
                    dragElement
            )

            levelData!![cord.z][cord.y][cord.x] = newElement

            if (newElement is Removable) {
                levelo!![cord.z][cord.y][cord.x] = ElementFactory.elementFactory(ElementType.BACKGROUND, cord)

            } else {
                levelo!![cord.z][cord.y][cord.x] = newElement

            }

            refreshListener?.onRefresh()


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
                    ElementFactory.elementFactory(
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


        when (levelData!![mwZ][mwY][mwX].typeId) {
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
                    ElementFactory.elementFactory(
                            ElementType.PLAYER, Player.position.z, Player.position.y,
                            Player.position.x

                    )
            )

            handler?.postDelayed(timer_arrow, 200)

        }
    }


    init {
        timer_water = Timer_Water().also {
            it.setListener(this)
        }
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
                getOldElementAt(OZ, OY, OX).typeId,
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
                setPos(callingCharacter.typeId, Coordinate(aktEbene, y, x))
                game.fishData.eatFish()
            }

            ElementType.KEY1 -> {
                setPos(callingCharacter.typeId, Coordinate(aktEbene, y, x))
                setPos(
                        BACKGROUND, Coordinate(
                        aktEbene, game.mapDoor.get(1).y,
                        game.mapDoor.get(1).x
                )
                )
            }

            ElementType.KEY2 -> {
                setPos(callingCharacter.typeId, Coordinate(aktEbene, y, x))
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

        val oldType = getOldElementAt(newCoord).typeId//Prüfen ob was auf RedButton steht

        when (oldType) {
            ElementType.SWITCH -> if (ElementType.CRATE_BLOCK == newType) {
                setPos(ElementType.SWITCH_CRATE_BLOCK, Coordinate(aktEbene, y, x))
                if (GameState.getGate() != null) {
                    setPos(
                            newType = BACKGROUND,
                            newCoord = Coordinate(
                                    GameState.getGate()!![0],
                                    GameState.getGate()!![1],
                                    GameState.getGate()!![2]
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
                        ).typeId, Coordinate(playZ, playY, playX)
                )
                aktEbene = z

                activateArrow(z, y, x)
            }
        }

        when (newType) {
            ElementType.MOVING_WATER -> {
                levelData!![z][y][x] = ElementFactory.changeElement(
                        ElementType.MOVING_WATER,
                        ElementGroup.EMPTY,
                        levelData!![z][y][x]
                )

                //MainActivity.getActivity().setImage(y, x, level!![aktEbene][y][x].image)
                refreshListener?.onRefresh()
            }

            ElementType.MOVING_WOOD -> {
                levelData!![z][y][x] = ElementFactory.changeElement(
                        ElementType.MOVING_WOOD, ElementGroup.Destination,
                        levelData!![z][y][x]
                )

                game.setMoving(ElementType.MOVING_WOOD, z, y, x)
                refreshListener?.onRefresh()
            }
            else -> {
                changeLevel(levelData!!, newType, newCoord)

                //Prüfen ob was auf RedButton steht
                checkIfRedButtonCovered(z, y, x)

                //imgGameField[y][x].setImageBitmap(level[aktEbene][y][x].getImage());
                //MainActivity.getActivity().setImage(y, x, level!![aktEbene][y][x].image)
                refreshListener?.onRefresh()
            }
        }
    }

    fun screenTouched(touchY: Int, touchX: Int) {
        playZ = Player.position.z
        playX = Player.position.x
        playY = Player.position.y

        if (!arrowTimerRunning) {
            val touchDirection = DirectionUtils.getTouchedDirection(touchY, touchX, playX, playY)
            checkMove(
                    direction = touchDirection,
                    Object = ElementType.PLAYER,
                    newCoord = Coordinate(aktEbene, playY, playX),
                    callingCharacter = levelData!![playZ][playY][playX]
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
        val aktObject = levelData!![z][y][x].typeId
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
            Log.i(TAG, callingCharacter.typeId.toString())
            stopTimer(callingCharacter.typeId)
        } else {
            nextObjectGroup = getElementAt(z, y + dirY, x + dirX).elementGroup
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
                        Coordinate(GameState.getTeleOutPosZ(), y + dirY, x + dirX), callingCharacter
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

        val nexDes = getElementAt(z, y + dirY, x + dirX).typeId

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
        this.levelData = levelE
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
                refreshListener?.onRefresh()
            }

            ElementType.LADDER_DOWN -> if (ElementType.PLAYER == Object) {
                aktEbene++
                setPos(ElementType.PLAYER, Coordinate(aktEbene, y, x))
                refreshListener?.onRefresh()
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
                                GameState.getTeleOutPosZ(), GameState.getTeleOutPosY(),
                                GameState.getTeleOutPosX()
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

        val tOZ = GameState.getTeleOutPosZ()
        val tOY = GameState.getTeleOutPosY()
        val tOX = GameState.getTeleOutPosX()

        val tIZ = GameState.getTeleInPosZ()
        val tIY = GameState.getTeleInPosY()
        val tIX = GameState.getTeleInPosX()

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

                if (levelData!![tIZ][tIY][i].typeId != 0) {

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

                if (levelData!![tIZ][i][tIX].typeId != 0) {
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

        refreshListener?.onRefresh()

    }


    fun isaWall(y: Int, x: Int, dirX: Int, dirY: Int): Boolean {
        return (x + dirX == -1
                || x + dirX >= levelData!![aktEbene][0].size
                || y + dirY == -1
                || y + dirY >= levelData!![aktEbene].size)
                || getElementAt(aktEbene, y + dirY, x + dirX).elementGroup == ElementGroup.WALL
                || getElementAt(aktEbene, y + dirY, x + dirX).elementGroup == ElementGroup.WALL
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
        return levelData!![z][y][x]
    }

    fun checkIfRedButtonCovered(z: Int, y: Int, x: Int) {
        if (ElementType.RED_BUTTON == levelo!![z][y][x].typeId) {

            if (ElementType.RED_BUTTON == levelData!![z][y][x].typeId) {
                handler?.removeCallbacks(timer_water)
            } else {
                handler?.post(timer_water)
            }
        }
    }

    fun getElementTypeAt(z: Int, y: Int, x: Int): Int {
        return getElementAt(z, y, x).typeId
    }


    fun changeLevel(level: Array<Array<Array<Element>>>, newType: Int, newCoord: Coordinate) {
        val z = newCoord.z
        val y = newCoord.y
        val x = newCoord.x

        level[z][y][x] = ElementFactory.elementFactory(newType, z, y, x)
    }

    companion object {
        @JvmStatic
        val game = GameState()

    }

}

interface RefreshListener {
    fun onRefresh()
}