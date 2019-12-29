package de.jensklingenberg.tuxoid.model.element.character

import android.os.Handler
import android.util.Log
import android.util.SparseArray
import de.jensklingenberg.tuxoid.interfaces.Pushable
import de.jensklingenberg.tuxoid.interfaces.Playable
import de.jensklingenberg.tuxoid.model.Coordinate
import de.jensklingenberg.tuxoid.model.Direction
import de.jensklingenberg.tuxoid.model.element.Element
import de.jensklingenberg.tuxoid.model.element.ElementGroup
import de.jensklingenberg.tuxoid.model.element.timer.Timer_npc

/**
 * Created by jens on 11.02.16.
 */
class NPC(type: Int, val npcNumber: Int) : Element(type), Pushable, Playable {
    private val group: ElementGroup = ElementGroup.charNPC
    private val direction = ""
    private val type: Int = type

    override val elementGroup: ElementGroup
        get() = ElementGroup.charNPC

    companion object {
        val handler = Handler()
        private val TAG = Element::class.java.simpleName
        val mapNpc = SparseArray<Coordinate>()
        private val mapNpcDirection = SparseArray<Direction>()
        val mapNpcTimerStatus = SparseArray<Boolean>()
        private val mapNpcTimerRunnable = SparseArray<Timer_npc>()
        fun setMapNpcCoordinates(i: Int, coordinate: Coordinate) {
            mapNpc.put(i, coordinate)
        }

        fun getMapNpcPos(i: Int): Coordinate {
            return mapNpc[i]
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