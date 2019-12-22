package de.jensklingenberg.tuxoid.model

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import de.jensklingenberg.tuxoid.model.element.Element

/**
 * Created by jens on 22/7/17.
 */
class GameImageView : AppCompatImageView {
    constructor(context: Context?, bit: Element) : super(context) {
        setImageBitmap(bit.image)
    }

    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}
}