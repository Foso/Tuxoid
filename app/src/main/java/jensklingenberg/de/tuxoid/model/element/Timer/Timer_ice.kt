package jensklingenberg.de.tuxoid.model.element.Timer

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