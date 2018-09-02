//Enthält alle Eigenschaften von einem ImageView aus der Sidebar

package jensklingenberg.de.tuxoid.model;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TableRow;

import jensklingenberg.de.tuxoid.model.Element.Element;
import jensklingenberg.de.tuxoid.ui.Sidebar;

public class SidebarImageView extends android.support.v7.widget.AppCompatImageView {

    public Sidebar sidebar;
    TableRow.LayoutParams sidebarLayout = new TableRow.LayoutParams(40, 40);

    public SidebarImageView(Context context, Element Tag, Bitmap bmp) {
        super(context);
        this.setLayoutParams(sidebarLayout);
        this.setTag(Tag);
        this.setImageBitmap(bmp);

        this.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    ClipData data = ClipData.newPlainText("",
                            "");

                    DragShadowBuilder shadowBuilder = new DragShadowBuilder(
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

            }


        });

    }


}
