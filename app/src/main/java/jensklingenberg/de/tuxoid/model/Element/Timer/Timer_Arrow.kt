package jensklingenberg.de.tuxoid.model.Element.Timer

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

