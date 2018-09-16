package jensklingenberg.de.tuxoid.model.Element

import android.graphics.Bitmap

import jensklingenberg.de.tuxoid.model.MyImage

/**
 * Created by jens on 18.04.17.
 */

class Wall(type: Int, z: Int, y: Int, x: Int) : Element(type, z, y, x) {

    override val elementGroup= ElementGroup.WALL


}
