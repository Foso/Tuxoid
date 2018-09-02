package jensklingenberg.de.tuxoid.model.Element.Destination;

import android.graphics.Bitmap;

import jensklingenberg.de.tuxoid.model.MyImage;

/**
 * Created by jens on 18.04.17.
 */

public class Ladder_Down extends Destination {

    private Bitmap image;

    public Ladder_Down(int type, int z, int y, int x) {
        super(type, z, y, x);
        this.image = MyImage.getImage(type);
    }


    @Override
    public Bitmap getImage() {
        return image;
    }

}
