package jensklingenberg.de.tuxoid.model.Element.Destination;

import android.graphics.Bitmap;

import jensklingenberg.de.tuxoid.model.MyImage;

/**
 * Created by jens on 18.04.17.
 */

public class Switch_Crate_Block extends Destination {


    private Bitmap image;
    private int type;

    public Switch_Crate_Block(int type, int z, int y, int x) {
        super(type, z, y, x);
        this.type = type;
        this.image = MyImage.getImage(type);
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
