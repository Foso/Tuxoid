package de.jensklingenberg.tuxoid.model.element.timer

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