package jensklingenberg.de.tuxoid.utils;

import jensklingenberg.de.tuxoid.model.Direction;

/**
 * Created by jens on 22/7/17.
 */

public class DirectionUtils {
    public static Direction getTouchedDirection(int y, int x, int playX, int playY) {

        if ((x < playX) && ((y == playY) || (y == playY - 1) || (y == playY + 1))) {
            return Direction.LEFT;
        }

        if ((x > playX) && ((y == playY) || (y == playY - 1) || (y == playY + 1))) {
            return Direction.RIGHT;
        }

        if (((x == playX) || (x == playX - 1) || (x == playX + 1)) && (y < playY)) {
            return Direction.UP;
        }

        if (((x == playX) || (x == playX - 1) || (x == playX + 1)) && (y > playY)) {
            return Direction.DOWN;
        }

        return Direction.STAY;
    }

    public static boolean DirectionLeftOrRight(Direction direction) {
        return direction.equals(Direction.LEFT) || direction.equals(Direction.RIGHT);
    }
}
