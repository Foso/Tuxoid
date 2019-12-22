package de.jensklingenberg.tuxoid.utils;

import java.util.ArrayList;
import java.util.List;

import de.jensklingenberg.tuxoid.model.Coordinate;

/**
 * Created by jens on 23/7/17.
 */

public class Utils {
    public static Coordinate newCoordinates(int z, int y, int x) {
        List<Integer> coord = new ArrayList<>();
        coord.add(z);
        coord.add(y);
        coord.add(x);

        return new Coordinate(z, y, x);
    }
}
