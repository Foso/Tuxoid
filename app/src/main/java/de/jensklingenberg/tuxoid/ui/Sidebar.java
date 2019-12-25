package de.jensklingenberg.tuxoid.ui;

import android.content.Context;
import android.widget.TableLayout;
import android.widget.TableRow;

import de.jensklingenberg.tuxoid.model.element.Element;
import de.jensklingenberg.tuxoid.model.SidebarImageView;
import de.jensklingenberg.tuxoid.utils.LoadSidebar;

public class Sidebar extends TableLayout {

    public static final String TAG = Sidebar.class.getSimpleName();
    public static Element DragElement;
    SidebarImageView[][] sidebarImage;
    TableRow[] tableRow;
    MainActivity activity = new MainActivity();
    private Element[][][] sidebarElement;
    public Sidebar(Context context) {
        super(context);

    }

    public static void setDragElement(Element drag) {
        DragElement = drag;
    }

    public void createLevel(int Level,LoadSidebar loadSidebar,Context context) {

        loadSidebar.createLevel(Level);
        sidebarElement = loadSidebar.getLevelE();

        tableRow = new TableRow[sidebarElement[0].length];
        sidebarImage = new SidebarImageView[sidebarElement[0].length][sidebarElement[0][0].length];
        for (int y = 0; y < sidebarElement[0].length; y++) {

            tableRow[y] = new TableRow(context);
            this.addView(tableRow[y]);

            for (int x = 0; x < sidebarElement[0][y].length; x++) {
                sidebarImage[y][x] = new SidebarImageView(context, sidebarElement[0][y][x]);
                tableRow[y].addView(sidebarImage[y][x]);
            }

        }

    }
}
