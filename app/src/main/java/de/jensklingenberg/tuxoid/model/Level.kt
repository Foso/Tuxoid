package de.jensklingenberg.tuxoid.model

import de.jensklingenberg.tuxoid.data.Array3D
import de.jensklingenberg.tuxoid.model.element.Element

data class Level(val foregroundlevelData: Array3D<Element>, val backgroundlevelData: Array3D<Element>)