package de.jensklingenberg.tuxoid.model.element.character

import android.os.Handler
import android.util.Log
import android.util.SparseArray
import de.jensklingenberg.tuxoid.interfaces.Moveable
import de.jensklingenberg.tuxoid.interfaces.Playable
import de.jensklingenberg.tuxoid.model.Direction
import de.jensklingenberg.tuxoid.model.element.Element
import de.jensklingenberg.tuxoid.model.element.ElementGroup
import de.jensklingenberg.tuxoid.model.element.timer.Timer_npc

/**
 * Created by jens on 11.02.16.
 */
class NPC(type: Int, z: Int, y: Int, x: Int, val npcNumber: Int) : Element(type), Moveable, Playable {
    private val group: ElementGroup = ElementGroup.charNPC
    private val direction = ""
    private val type: Int

    init {
        mapNpc.put(npcNumber, intArrayOf(z, y, x))
        this.type = type
        if (mapNpcTimerStatus[npcNumber] == null) {
            setMapNpcTimerStatus(npcNumber, false)
        }
    }

    override val elementGroup: ElementGroup
        get() = ElementGroup.charNPC

    companion object {
        val handler = Handler()
        private val TAG = Element::class.java.simpleName
        private val mapNpc = SparseArray<IntArray>()
        private val mapNpcDirection = SparseArray<Direction>()
        private val mapNpcTimerStatus = SparseArray<Boolean>()
        private val mapNpcTimerRunnable = SparseArray<Timer_npc>()
        fun setMapNpcCoordinates(i: Int, z: Int, y: Int, x: Int) {
            mapNpc.put(i, intArrayOf(z, y, x))
        }

        fun getMapNpcPosZ(i: Int): Int {
            return mapNpc[i][0]
        }

        fun getMapNpcPosY(i: Int): Int {
            return mapNpc[i][1]
        }

        fun getMapNpcPosX(i: Int): Int {
            return mapNpc[i][2]
        }

        fun getMapNpcDirection(i: Int): Direction {
            return mapNpcDirection[i]
        }

        fun getMapNpcTimerStatus(i: Int): Boolean {
            return mapNpcTimerStatus[i]
        }

        fun getMapNpcTimerRunnable(npcNumber: Int): Timer_npc {
            return mapNpcTimerRunnable[npcNumber]
        }

        fun setMapNpcTimerStatus(i: Int, TimerStatus: Boolean) {
            mapNpcTimerStatus.put(i, TimerStatus)
        }

        fun setMapNpcDirection(i: Int, direction: Direction) {
            mapNpcDirection.put(i, direction)
        }

        fun setMapNpcTimerRunnable(npcNumber: Int, timer_npc: Timer_npc) {
            mapNpcTimerRunnable.put(npcNumber, timer_npc)
        }

        fun startTimer(npcNumber: Int, time: Int, activity: Timer_npc.TimerClock?, type: Int) {
            setMapNpcTimerRunnable(npcNumber, Timer_npc(activity!!, npcNumber, type))
            handler.postDelayed(getMapNpcTimerRunnable(npcNumber), time.toLong())
        }

        fun stopTimer(npcNumber: Int) {
            Log.i(TAG, "ddddddd")
            handler.removeCallbacks(getMapNpcTimerRunnable(npcNumber))
        }
    }


}