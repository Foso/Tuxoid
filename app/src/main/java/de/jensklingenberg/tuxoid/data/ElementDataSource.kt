package de.jensklingenberg.tuxoid.data

import de.jensklingenberg.tuxoid.model.Coordinate
import de.jensklingenberg.tuxoid.model.element.Element
import de.jensklingenberg.tuxoid.model.element.ElementGroup

interface ElementDataSource{

    fun createElement(type: Int, coordinate: Coordinate): Element
    fun changeElement(type: Int, group: ElementGroup, element: Element): Element
}