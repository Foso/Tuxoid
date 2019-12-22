package de.jensklingenberg.tuxoid.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import de.jensklingenberg.tuxoid.model.element.Element
import de.jensklingenberg.tuxoid.model.MyImage

class GView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    init {
        MyImage(context)

    }

    private var level: Array<Array<Array<Element>>>? = null
    var widthL = 50
    var heightL = 50

    private var aktEbene = 0

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        level?.let {
            for (y in 0 until level!![aktEbene].size) {
                for (x in 0 until level!![aktEbene][0].size) {
                    canvas?.drawBitmap(level!![aktEbene][y][x].image, null, Rect(x * widthL, y * heightL, (x + 1) * widthL, (y + 1) * heightL), null);
                }
            }
        }


    }


    public fun refresh(
            level: Array<Array<Array<Element>>>,
            aktEbene: Int
    ) {
        this.level = level
        this.aktEbene = aktEbene
        invalidate()
    }
}