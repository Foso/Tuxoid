package de.jensklingenberg.tuxoid.data

import android.graphics.Bitmap

interface ImageSource {
    fun loadBitmap(type: Int): Bitmap
    fun loadImage(type: Int): Bitmap

}
