package jensklingenberg.de.tuxoid;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.IOException;

import de.jensklingenberg.tuxoid.R;
import jensklingenberg.de.tuxoid.model.GameImageView;
import jensklingenberg.de.tuxoid.model.MyImage;
import jensklingenberg.de.tuxoid.ui.GView;
import jensklingenberg.de.tuxoid.utils.LoadGame;

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


        levelHelper = new LevelHelper();
        levelHelper.setListener(this);
        levelHelper.setHandler(new Handler());
        grid = findViewById(R.id.glGame);

        if (extras != null) {
            levelHelper.aktLevel = extras.getInt(ARG_LEVEL);
        }
        createLevel(levelHelper.aktLevel);

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
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        grid.getLayoutParams().height = height;
        grid.getLayoutParams().width = width;

        int lvlHeight = levelHelper.level[levelHelper.aktEbene].length;
        int lvlWidht = levelHelper.level[levelHelper.aktEbene][0].length;

        grid.setRowCount(lvlHeight);
        grid.setColumnCount(lvlWidht);

        imgGameField = new ImageView[lvlHeight][lvlWidht];

      //  imgGameField = GameImageView.initGameField(levelHelper.level);

        gView.setWidthL(width / lvlWidht);
        gView.setHeightL(height / lvlHeight);

        for (int y = 0; y < lvlHeight; y++) {

            for (int x = 0; x < levelHelper.level[levelHelper.aktEbene][y].length; x++) {

                imgGameField[y][x] = new GameImageView(this, levelHelper.level[levelHelper.aktEbene][y][x]);
                imgGameField[y][x].setId(x);
                imgGameField[y][x].setTag(new int[]{y, x});
                imgGameField[y][x].setLayoutParams(new LinearLayout.LayoutParams(width / lvlWidht, height / lvlHeight));
                imgGameField[y][x].setOnClickListener(v -> {
                    int[] coord = (int[]) v.getTag();
                    screenTouched(coord[0], coord[1]);
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