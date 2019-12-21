package jensklingenberg.de.tuxoid.model.Element.Timer

import android.util.Log

import jensklingenberg.de.tuxoid.MainActivity
import jensklingenberg.de.tuxoid.model.Coordinate
import jensklingenberg.de.tuxoid.model.Element.Character.NPC
import jensklingenberg.de.tuxoid.model.Element.Element
import jensklingenberg.de.tuxoid.model.Element.ElementType

class Timer_npc
/**
 * @param mainActivity
 */
    (private val mainActivity: TimerClock, private val npc: Int, private val type: Int) :
    Runnable {


    override fun run() {
mainActivity.npcTimerUpdate(npc,type)


    }

    companion object {

        private val TAG = Element::class.java.simpleName
    }

    interface TimerClock {

        fun npcTimerUpdate(npc: Int,type: Int)

    }

}