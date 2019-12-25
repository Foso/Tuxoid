package de.jensklingenberg.tuxoid.data

import de.jensklingenberg.tuxoid.model.element.Element

interface LevelLoadListener {
    fun onLevelLoaded(
            levelE: Array<Array<Array<Element>>>,
            oldLevel: Array<Array<Array<Element>>>
    )
}