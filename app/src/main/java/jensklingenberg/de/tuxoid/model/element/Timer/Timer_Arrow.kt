package jensklingenberg.de.tuxoid.model.element.Timer

class Timer_Arrow
/**
 * @param clock
 */
    (
    /**
     *
     */
    private val timerClock: TimerClock
) : Runnable {

    override fun run() {
        timerClock.arrowTimerUpdate()


    }

    interface TimerClock {

        fun arrowTimerUpdate()

    }
}

