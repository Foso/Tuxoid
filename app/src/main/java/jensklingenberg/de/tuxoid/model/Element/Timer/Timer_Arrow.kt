package jensklingenberg.de.tuxoid.model.Element.Timer

class Timer_Arrow
/**
 * @param clock
 */
    (
    /**
     *
     */
    private val mainActivity: TimerClock
) : Runnable {

    override fun run() {
        mainActivity.arrowTimerUpdate()


    }

    interface TimerClock {

        fun arrowTimerUpdate()

    }
}

