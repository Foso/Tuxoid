package jensklingenberg.de.tuxoid;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
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
import jensklingenberg.de.tuxoid.utils.LoadGame;

public class MainActivity extends Activity implements Listener {
    private static final String TAG = MainActivity.class.getSimpleName();

    private static MainActivity activity;
    public boolean bMovingDirRight = false;
    private GridLayout grid;
    private ImageView[][] imgGameField;
    public LevelHelper levelHelper;
public static String ARG_LEVEL = "ARG_LEVEL";

    public static MainActivity getActivity() {
        return activity;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        new MyImage(this);

        Bundle extras = getIntent().getExtras();
        setContentView(R.layout.activity_main);
        levelHelper = new LevelHelper();
        levelHelper.setListener(this);
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


        for (int y = 0; y < imgGameField.length; y++) {
            for (int i = 0; i < imgGameField[y].length; i++) {
                grid.addView(imgGameField[y][i]);
            }
        }


    }

    private void refresh() {
        for (int y = 0; y < levelHelper.level[levelHelper.aktEbene].length; y++) {

            for (int x = 0; x < levelHelper.level[levelHelper.aktEbene][0].length; x++) {
                imgGameField[y][x].setImageBitmap(levelHelper.level[levelHelper.aktEbene][y][x].getImage());
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify getMapMovingWood parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void setImage(int y, int x, Bitmap image) {
        imgGameField[y][x].setImageBitmap(image);
    }

    public void screenTouched(int y, int x) {
        levelHelper.screenTouched(y,x);
    }

    public boolean getIceTimer() {
        return levelHelper.bTimer;
    }


    public boolean getbTimer() {
        return levelHelper.bTimer;
    }



    @Override
    public void onRefresh() {
        refresh();
    }
}