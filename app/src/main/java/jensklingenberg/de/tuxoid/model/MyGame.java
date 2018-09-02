package jensklingenberg.de.tuxoid.model;

import android.util.SparseArray;

/**
 * Created by jens on 7/9/17.
 */

public class MyGame {
    private static int[] Moving_Wood = new int[3];
    private static SparseArray<int[]> mapMoving = new SparseArray<int[]>();

    //MOVING WOOD
    public static int[] getMoving_Wood() {
        return Moving_Wood;
    }

    public static void setMoving_Wood(int z, int y, int x) {

        Moving_Wood[0] = z;
        Moving_Wood[1] = y;
        Moving_Wood[2] = x;
    }

    public static SparseArray<int[]> getMapMoving() {
        return mapMoving;
    }

}
