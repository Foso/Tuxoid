package de.jensklingenberg.tuxoid.ui;

import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import javax.inject.Inject;

import de.jensklingenberg.tuxoid.App;
import de.jensklingenberg.tuxoid.R;
import de.jensklingenberg.tuxoid.data.LevelDataSource;
import de.jensklingenberg.tuxoid.model.Coordinate;
import de.jensklingenberg.tuxoid.model.GameImageView;
import de.jensklingenberg.tuxoid.model.SidebarImageView;
import de.jensklingenberg.tuxoid.model.element.Element;
import de.jensklingenberg.tuxoid.utils.LevelHelper;
import de.jensklingenberg.tuxoid.utils.LoadGame;
import de.jensklingenberg.tuxoid.utils.LoadSidebar;

public class MainFragment extends Fragment implements MainContract.View {

    private static final String TAG = MainActivity.class.getSimpleName();

    private GridLayout grid;
    private ImageView[][] imgGameField;
    public static String ARG_LEVEL = "ARG_LEVEL";

    MainContract.Presenter presenter;


    @Inject
    LevelHelper levelHelper;

    @Inject
    LevelDataSource levelDataSource;



    GView gView;
    View myView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        App.appComponent.inject(this);

        return inflater.inflate(R.layout.fragment_main, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        myView = view;
        presenter = new MainPresenter(this);

        gView = view.findViewById(R.id.canvas);
        LinearLayout rel = myView.findViewById(R.id.sidebarLayout);


        levelHelper.setListener(presenter);
        grid = myView.findViewById(R.id.glGame);
        if(getArguments()!=null){
            String level = getArguments().getString(ARG_LEVEL);


        }
        presenter.createLevel(Integer.parseInt("1"));





        Element[][][] sidebar = new LoadSidebar().createLevel(levelHelper.aktLevel);

        if (null != sidebar) {
            for (Element[] elements : sidebar[0]) {
                SidebarImageView side = new SidebarImageView(getContext(), elements[0]);

                rel.addView(side);
            }
        }

    }




    private void initImgGameField(Element[][][] level) {

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x - 100;
        int height = size.y;
        // grid.getLayoutParams().height = height;
        //grid.getLayoutParams().width = width;

        int lvlRows = level[levelHelper.aktEbene].length;
        int lvlColums = level[levelHelper.aktEbene][0].length;

        grid.setRowCount(lvlRows);
        grid.setColumnCount(lvlColums);

        imgGameField = new ImageView[lvlRows][lvlColums];

        //  imgGameField = GameImageView.initGameField(levelHelper.level);

        gView.setWidthL(width / lvlColums);
        gView.setHeightL(height / lvlRows);

        for (int y = 0; y < lvlRows; y++) {

            for (int x = 0; x < level[levelHelper.aktEbene][y].length; x++) {

                imgGameField[y][x] = new GameImageView(getContext(), levelHelper.level[levelHelper.aktEbene][y][x]);
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
                        levelHelper.onDrag(new Coordinate(levelHelper.aktEbene, cord[0], cord[1]), Sidebar.DragElement);
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
        GView gView = myView.findViewById(R.id.canvas);
        gView.refresh(levelHelper.level, levelHelper.aktEbene);
    }

    public void screenTouched(int y, int x) {
        levelHelper.screenTouched(y, x);
    }

    @Override
    public void onRefresh() {
        refresh();
    }

    @Override
    public void setGameData(@NotNull Element[][][] level) {
        initImgGameField(level);
    }
}
