package de.jensklingenberg.tuxoid.model.element.timer

import de.jensklingenberg.tuxoid.model.element.Element

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