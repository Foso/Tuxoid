//Enthält alle Eigenschaften von einem ImageView aus der Sidebar

package jensklingenberg.de.tuxoid.model;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Bitmap;
import android.text.Layout;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableRow;

import jensklingenberg.de.tuxoid.model.Element.Element;
import jensklingenberg.de.tuxoid.ui.Sidebar;

public class SidebarImageView extends android.support.v7.widget.AppCompatImageView {


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
