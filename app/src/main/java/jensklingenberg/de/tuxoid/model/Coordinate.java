package jensklingenberg.de.tuxoid.model;

/**
 * Created by jens on 22/7/17.
 */

public class Coordinate {
    private int z;
    private int y;
    private int x;

    public Coordinate(int z, int y, int x) {
        this.z = z;
        this.y = y;
        this.x = x;
    }

    public int getZ() {
        return z;
    }


    public int getY() {
        return y;
    }


    public int getX() {
        return x;
    }


}
