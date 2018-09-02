package jensklingenberg.de.tuxoid.utils

import jensklingenberg.de.tuxoid.model.Element.Element

interface LevelLoadListener {
    fun onLevelLoaded(
        levelE: Array<Array<Array<Element>>>,
        levelEo: Array<Array<Array<Element>>>
    )
}