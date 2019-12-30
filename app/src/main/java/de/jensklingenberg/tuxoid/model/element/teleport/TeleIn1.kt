package de.jensklingenberg.tuxoid.model.element.teleport

import de.jensklingenberg.tuxoid.model.element.Element

class TeleIn1(type: Int) : Element(type){
    override fun isReachable(): Boolean =true
}