package jensklingenberg.de.tuxoid.model.Element;

import android.graphics.Bitmap;

import jensklingenberg.de.tuxoid.model.MyImage;

/**
 * Created by jens on 18.04.17.
 */

public class Wall extends Element {

    private String group = "";
    private Bitmap image;

    public Wall(int type, int z, int y, int x) {
        super(type, z, y, x);
        this.group = "Wall";
        this.image = MyImage.getImage(type);
    }

    @Override
    public ElementGroup getElementGroup() {
        return ElementGroup.WALL;
    }

    @Override
    public Bitmap getImage() {
        return image;
    }
}
