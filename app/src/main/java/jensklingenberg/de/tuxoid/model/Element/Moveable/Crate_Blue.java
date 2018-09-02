package jensklingenberg.de.tuxoid.model.Element.Moveable;

import android.graphics.Bitmap;

import jensklingenberg.de.tuxoid.model.Element.Element;
import jensklingenberg.de.tuxoid.model.Element.ElementGroup;
import jensklingenberg.de.tuxoid.model.MyImage;

/**
 * Created by jens on 18.04.17.
 */

public class Crate_Blue extends Element {


    private Bitmap image;

    public Crate_Blue(int type, int z, int y, int x) {
        super(type, z, y, x);
        this.image = MyImage.getImage(type);
    }

    @Override
    public ElementGroup getElementGroup() {
        return ElementGroup.Moveable;
    }

    @Override
    public Bitmap getImage() {
        return image;
    }

    @Override
    public boolean isRemovable() {
        return true;
    }


}
