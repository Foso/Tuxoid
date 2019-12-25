//Enthält alle Eigenschaften von einem ImageView aus der Sidebar

package de.jensklingenberg.tuxoid.ui.common;

import android.content.ClipData;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatImageView;

import de.jensklingenberg.tuxoid.model.element.Element;

public class SidebarImageView extends AppCompatImageView {


    public SidebarImageView(Context context, Element element) {
        super(context);
        this.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 100));
        this.setTag(element);
        this.setImageBitmap(element.getImage());

        this.setOnTouchListener((v, event) -> {

            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData data = ClipData.newPlainText("",
                        "");

                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
                        v);
                v.startDrag(data, shadowBuilder, v, 0);
                Element ele = (Element) v.getTag();
                //DragElement = ele;
                Sidebar.setDragElement(ele);
                // v.setVisibility(View.INVISIBLE);
                return true;
            } else {
                return false;
            }

        });

    }


}
