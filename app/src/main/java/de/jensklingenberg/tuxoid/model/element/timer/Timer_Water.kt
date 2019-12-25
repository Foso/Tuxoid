package de.jensklingenberg.tuxoid.model.element.timer

class Timer_Water
/**
 * @param mainActivity
 */
    (
    /**
     *
     */
    private val mainActivity: TimerClock?
) : Runnable {


    override fun run() {
        /* do what you need to do */

        mainActivity?.waterTimerUpdate()

    }

    interface TimerClock {

        fun waterTimerUpdate()

    }
}