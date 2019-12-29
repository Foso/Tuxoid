package de.jensklingenberg.tuxoid.ui.common

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import de.jensklingenberg.tuxoid.App
import de.jensklingenberg.tuxoid.data.Array2D
import de.jensklingenberg.tuxoid.data.ImageSource
import de.jensklingenberg.tuxoid.model.element.Element
import javax.inject.Inject

class GView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    init {
        App.appComponent.inject(this)
    }

    @Inject
    lateinit var imageSource: ImageSource

    private var level: Array2D<Element>? = null
    var widthL = 50
    var heightL = 50


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        level?.let {
            for (y in level!!.indices) {
                for (x in level!![0].indices) {

                    canvas?.drawBitmap(imageSource.loadBitmap(level!![y][x].typeId), null, Rect(x * widthL, y * heightL, (x + 1) * widthL, (y + 1) * heightL), null);
                }
            }
        }


    }


    fun refresh(levelDatum: Array<Array<Element>>) {
        this.level = levelDatum
        invalidate()

    }
}