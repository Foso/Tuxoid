package de.jensklingenberg.tuxoid.data

import de.jensklingenberg.tuxoid.model.element.Element

interface LevelLoadListener {

    fun onIntLevelLoaded(
            levelEint: Array<Array<Array<Int>>>,
            oldLevelint: Array<Array<Array<Int>>>
    )
}