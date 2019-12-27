package de.jensklingenberg.tuxoid.data

import de.jensklingenberg.tuxoid.model.element.Element

interface RefreshListener {
    fun onRefresh(arrayOfArrays: Array<Array<Element>>)
}