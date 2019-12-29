package de.jensklingenberg.tuxoid.data

/**
 * Created by jens on 18.04.17.
 */


class FishData {

    var fishCount = 0
        internal set


    fun addFish() {
        fishCount++
    }

    //FISCH
    fun eatFish() {
        fishCount--
    }

    fun resetFish() {
        fishCount = 0
    }

}