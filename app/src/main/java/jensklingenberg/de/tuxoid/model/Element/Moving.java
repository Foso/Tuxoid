package jensklingenberg.de.tuxoid.model.Element;

import android.util.SparseArray;

import jensklingenberg.de.tuxoid.interfaces.Moveable;

/**
 * Created by jens on 12.02.16.
 */
public class Moving implements Moveable {

    public static int MovingCount = 0;
    private static int[] Moving_Wood = new int[3];
    private static int MovCount = 0;
    private static SparseArray<int[]> mapMoving = new SparseArray<int[]>();

    Moving() {


    }


    public static int[] getMoving_Wood() {
        return Moving_Wood;
    }

    public static void setMoving_Wood(int z, int y, int x) {

        Moving_Wood[0] = z;
        Moving_Wood[1] = y;
        Moving_Wood[2] = x;
    }


}
