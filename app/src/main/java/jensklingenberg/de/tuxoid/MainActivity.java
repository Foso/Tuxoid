package jensklingenberg.de.tuxoid;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.DragEvent;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.io.IOException;

import de.jensklingenberg.tuxoid.R;
import jensklingenberg.de.tuxoid.model.Coordinate;
import jensklingenberg.de.tuxoid.model.element.Element;
import jensklingenberg.de.tuxoid.model.GameImageView;
import jensklingenberg.de.tuxoid.model.SidebarImageView;
import jensklingenberg.de.tuxoid.ui.GView;
import jensklingenberg.de.tuxoid.ui.Sidebar;
import jensklingenberg.de.tuxoid.utils.LoadGame;
import jensklingenberg.de.tuxoid.utils.LoadSidebar;

public class MainActivity extends Activity implements Listener {
    private static final String TAG = MainActivity.class.getSimpleName();

    private static MainActivity activity;
    private GridLayout grid;
    private ImageView[][] imgGameField;
    public LevelHelper levelHelper;
public static String ARG_LEVEL = "ARG_LEVEL";

    public static MainActivity getActivity() {
        return activity;
    }
    GView gView ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;

        Bundle extras = getIntent().getExtras();
        setContentView(R.layout.activity_main);

        gView = findViewById(R.id.canvas);
        LinearLayout rel = findViewById(R.id.sidebarLayout);


        levelHelper = new LevelHelper();
        levelHelper.setListener(this);
        levelHelper.setHandler(new Handler());
        grid = findViewById(R.id.glGame);

        if (extras != null) {
            levelHelper.aktLevel = extras.getInt(ARG_LEVEL);
        }
        createLevel(levelHelper.aktLevel);


        Element[][][]  sidebar = new LoadSidebar().createLevel(levelHelper.aktLevel);

        if(null != sidebar){
            for (Element[] elements : sidebar[0]) {
                SidebarImageView side = new SidebarImageView(this,elements[0]);

                  rel.addView(side);
            }
        }




    }


    // Laedt das aktLevel
    private void createLevel(int Level) {
        levelHelper.aktLevel = Level;

        LoadGame loadgame = new LoadGame(this);
        loadgame.setListener((levelE, levelEo) -> levelHelper.setLevel(loadgame.getLevelE(),loadgame.getLevelEo()));

        try {
            loadgame.createLevel(levelHelper.aktLevel);
        } catch (IOException e) {
            e.printStackTrace();
        }

        initImgGameField();
    }

    private void initImgGameField() {
        RelativeLayout ggView = findViewById(R.id.rel1);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x-100;
        int height = size.y;
       // grid.getLayoutParams().height = height;
        //grid.getLayoutParams().width = width;

        int lvlRows = levelHelper.level[levelHelper.aktEbene].length;
        int lvlColums = levelHelper.level[levelHelper.aktEbene][0].length;

        grid.setRowCount(lvlRows);
        grid.setColumnCount(lvlColums);

        imgGameField = new ImageView[lvlRows][lvlColums];

      //  imgGameField = GameImageView.initGameField(levelHelper.level);

        gView.setWidthL(width / lvlColums);
        gView.setHeightL(height / lvlRows);

        for (int y = 0; y < lvlRows; y++) {

            for (int x = 0; x < levelHelper.level[levelHelper.aktEbene][y].length; x++) {

                imgGameField[y][x] = new GameImageView(this, levelHelper.level[levelHelper.aktEbene][y][x]);
                imgGameField[y][x].setId(x);
                imgGameField[y][x].setTag(new int[]{y, x});
                imgGameField[y][x].setLayoutParams(new LinearLayout.LayoutParams(width / lvlColums, height / lvlRows));
                imgGameField[y][x].setOnClickListener(v -> {
                    int[] coord = (int[]) v.getTag();
                    screenTouched(coord[0], coord[1]);
                });

                imgGameField[y][x].setOnDragListener((v, event) -> {

                    if (event.getAction() == DragEvent.ACTION_DROP) {
                        int[] cord = (int[]) v.getTag();
                        levelHelper.onDrag(new Coordinate(levelHelper.aktEbene,cord[0],cord[1]),Sidebar.DragElement);
                    }
                    return true;
                });

            }
        }


        for (ImageView[] anImgGameField : imgGameField) {
            for (ImageView anAnImgGameField : anImgGameField) {
                grid.addView(anAnImgGameField);
            }
        }


    }

    private void refresh() {
        GView gView = findViewById(R.id.canvas);
        gView.refresh(levelHelper.level,levelHelper.aktEbene);
        gView.invalidate();
    }

    public void screenTouched(int y, int x) {
        levelHelper.screenTouched(y,x);
    }

    @Override
    public void onRefresh() {
        refresh();
    }


}