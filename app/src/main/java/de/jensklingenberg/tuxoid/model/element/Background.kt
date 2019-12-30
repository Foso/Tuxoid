package de.jensklingenberg.tuxoid.model.element

import de.jensklingenberg.tuxoid.R
import de.jensklingenberg.tuxoid.interfaces.IReachable

class Background : Element(ElementType.BACKGROUND){
    override val imageResId: Int = R.drawable.background
    override fun isReachable()=true
}