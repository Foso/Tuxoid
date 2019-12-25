package de.jensklingenberg.tuxoid.model.element

import de.jensklingenberg.tuxoid.R

class Background(z: Int, y: Int, x: Int) : Element(ElementType.BACKGROUND, z, y, x){

    override val imageResId: Int = R.drawable.background

}