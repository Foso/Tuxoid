package de.jensklingenberg.tuxoid.model.element.timer

class Timer_ice
/**
 * @param timerClock
 */
    (
    /**
     *
     */
    private val timerClock: TimerClock
) : Runnable {

    override fun run() {
timerClock.iceTimerUpdate()

    }

    interface TimerClock {

        fun iceTimerUpdate()

    }
}