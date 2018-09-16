package jensklingenberg.de.tuxoid.model.Element.Timer

import jensklingenberg.de.tuxoid.MainActivity
import jensklingenberg.de.tuxoid.model.Coordinate
import jensklingenberg.de.tuxoid.model.Element.Character.Player
import jensklingenberg.de.tuxoid.model.Element.Element
import jensklingenberg.de.tuxoid.model.Element.ElementType

class Timer_ice
/**
 * @param mainActivity
 */
    (
    /**
     *
     */
    private val mainActivity: TimerClock
) : Runnable {

    override fun run() {
mainActivity.iceTimerUpdate()

    }

    interface TimerClock {

        fun iceTimerUpdate()

    }
}