package jensklingenberg.de.tuxoid.model.Element.Collectable;

import android.graphics.Bitmap;

import jensklingenberg.de.tuxoid.interfaces.ICollectable;
import jensklingenberg.de.tuxoid.model.Game;
import jensklingenberg.de.tuxoid.model.MyImage;

/**
 * Created by jens on 18.04.17.
 */

public class Fish extends Collectable implements ICollectable {
    private Bitmap image;
    private int type;

    public Fish(int type, int z, int y, int x) {
        super(type, z, y, x);
        this.type = type;
        this.image = MyImage.getImage(type);
        Game.INSTANCE.addFish();
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
