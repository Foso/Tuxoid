package jensklingenberg.de.tuxoid.model.element.Timer

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