package jensklingenberg.de.tuxoid.model.Element.Collectable;

import android.graphics.Bitmap;
import android.util.SparseArray;

import jensklingenberg.de.tuxoid.interfaces.ICollectable;
import jensklingenberg.de.tuxoid.model.MyImage;

/**
 * Created by jens on 18.04.17.
 */

public class Key extends Collectable implements ICollectable {

    public static SparseArray<int[]> mapKey = new SparseArray<>();
    private Bitmap image;
    private int type;

    public Key(int id, int type, int z, int y, int x) {
        super(type, z, y, x);
        this.type = type;
        this.image = MyImage.getImage(type);

        mapKey.put(id, new int[]{y, x});

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
}
