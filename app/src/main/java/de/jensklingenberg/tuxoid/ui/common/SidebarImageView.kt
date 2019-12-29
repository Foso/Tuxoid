//Enthält alle Eigenschaften von einem ImageView aus der Sidebar
package de.jensklingenberg.tuxoid.ui.common

import android.content.ClipData
import android.content.Context
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatImageView
import de.jensklingenberg.tuxoid.App
import de.jensklingenberg.tuxoid.data.ImageSource
import de.jensklingenberg.tuxoid.model.element.Element
import javax.inject.Inject

class SidebarImageView(context: Context?, element: Element) : AppCompatImageView(context) {


    init {
        App.appComponent.inject(this)
    }

    @Inject
    lateinit var imageSource: ImageSource

    init {
        this.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 100)
        this.tag = element
        setImageBitmap(imageSource.loadBitmap(element.typeId))
        setOnTouchListener { v: View, event: MotionEvent ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                val data = ClipData.newPlainText("",
                        "")
                val shadowBuilder = DragShadowBuilder(
                        v)
                v.startDrag(data, shadowBuilder, v, 0)
                val ele = v.tag as Element
                //DragElement = ele;
                Sidebar.setDragElement(ele)
                // v.setVisibility(View.INVISIBLE);
                return@setOnTouchListener true
            } else {
                return@setOnTouchListener false
            }
        }
    }
}