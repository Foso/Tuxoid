package de.jensklingenberg.tuxoid.ui;

import android.graphics.Point;
import android.os.Bundle;
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

import de.jensklingenberg.tuxoid.App;
import de.jensklingenberg.tuxoid.R;
import de.jensklingenberg.tuxoid.model.Coordinate;
import de.jensklingenberg.tuxoid.model.GameImageView;
import de.jensklingenberg.tuxoid.model.MyImage;
import de.jensklingenberg.tuxoid.model.SidebarImageView;
import de.jensklingenberg.tuxoid.model.element.Element;

public class MainFragment extends Fragment implements MainContract.View {

    private GridLayout grid;
    private ImageView[][] imgGameField;

    private MainContract.Presenter presenter;

    private GView gView;
    private View myView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        App.appComponent.inject(this);

        return inflater.inflate(R.layout.fragment_main, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       new MyImage(getContext());

        myView = view;
        presenter = new MainPresenter(this);

        gView = view.findViewById(R.id.canvas);


        grid = myView.findViewById(R.id.glGame);
        if(getArguments()!=null){
            String level =  MainFragmentArgs.fromBundle(getArguments()).component1();
            presenter.createLevel(Integer.parseInt(level));

        }

    }



    @Override
    public void onRefresh(@org.jetbrains.annotations.Nullable Element[][][] levelData, int aktEbene) {
        gView.refresh(levelData, aktEbene);
    }

    @Override
    public void setGameData(@NotNull Element[][] levelData1, int aktEbene) {

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x - 100;
        int height = size.y;
        int lvlRows = levelData1.length;
        int lvlColums = levelData1[0].length;

        grid.setRowCount(lvlRows);
        grid.setColumnCount(lvlColums);

        imgGameField = new ImageView[lvlRows][lvlColums];

        gView.setWidthL(width / lvlColums);
        gView.setHeightL(height / lvlRows);

        for (int y = 0; y < lvlRows; y++) {

            for (int x = 0; x < levelData1[y].length; x++) {

                imgGameField[y][x] = new GameImageView(getContext(), levelData1[y][x]);
                imgGameField[y][x].setId(x);
                imgGameField[y][x].setTag(new int[]{y, x});
                imgGameField[y][x].setLayoutParams(new LinearLayout.LayoutParams(width / lvlColums, height / lvlRows));
                imgGameField[y][x].setOnClickListener(v -> {
                    int[] coord = (int[]) v.getTag();
                    presenter.screenTouched(coord[0], coord[1]);
                });

                imgGameField[y][x].setOnDragListener((v, event) -> {

                    if (event.getAction() == DragEvent.ACTION_DROP) {
                        int[] cord = (int[]) v.getTag();
                        presenter.onDrag(new Coordinate(aktEbene, cord[0], cord[1]), Sidebar.DragElement);
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

    @Override
    public void setSidebarData(@NotNull Element[][][] sidebar) {
        LinearLayout rel = getView().findViewById(R.id.sidebarLayout);

        if (null != sidebar) {
            for (Element[] elements : sidebar[0]) {
                SidebarImageView side = new SidebarImageView(getContext(), elements[0]);

                rel.addView(side);
            }
        }
    }
}
