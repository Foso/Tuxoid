package de.jensklingenberg.tuxoid.model.element.timer

class Timer_Water
/**
 * @param timerClock
 */
(
        /**
         *
         */

) : Runnable {

    private var timerClock: TimerClock? = null

    fun setListener(timerClock: TimerClock) {
        this.timerClock = timerClock
    }


    override fun run() {
        /* do what you need to do */

        timerClock?.waterTimerUpdate()

    }

    interface TimerClock {

        fun waterTimerUpdate()

    }
}