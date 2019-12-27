package de.jensklingenberg.tuxoid.data


interface LevelLoadListener {

    fun onIntLevelLoaded(
            levelEint: Array3D<Int>,
            oldLevelint: Array3D<Int>
    )
}