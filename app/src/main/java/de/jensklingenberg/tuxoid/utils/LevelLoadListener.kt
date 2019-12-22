package de.jensklingenberg.tuxoid.utils

import de.jensklingenberg.tuxoid.model.element.Element

interface LevelLoadListener {
    fun onLevelLoaded(
        levelE: Array<Array<Array<Element>>>,
        levelEo: Array<Array<Array<Element>>>
    )
}