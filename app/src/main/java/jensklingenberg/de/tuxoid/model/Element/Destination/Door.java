package jensklingenberg.de.tuxoid.model.Element.Destination;

import android.graphics.Bitmap;
import android.util.SparseArray;

import jensklingenberg.de.tuxoid.model.Coordinate;
import jensklingenberg.de.tuxoid.model.Element.Element;
import jensklingenberg.de.tuxoid.model.MyImage;

/**
 * Created by jens on 18.04.17.
 */

public class Door extends Element {

    //  public static SparseArray<int[]> mapDoor = new SparseArray<int[]>();
    public static SparseArray<Coordinate> mapDoor = new SparseArray<Coordinate>();
    private Bitmap image;
    private int type;
    private Coordinate position;

    public Door(int id, int type, int z, int y, int x) {
        super(type, z, y, x);
        this.type = type;
        this.image = MyImage.getImage(type);
        position = new Coordinate(z, y, x);
        mapDoor.put(id, position);

    }

    @Override
    public boolean isRemovable() {
        return true;
    }

    @Override
    public Bitmap getImage() {
        return image;
    }

    @Override
    public int getType() {
        return type;
    }

    public Coordinate getPosition() {
        return position;
    }
}
