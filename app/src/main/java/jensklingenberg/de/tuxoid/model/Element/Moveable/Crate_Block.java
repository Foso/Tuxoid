package jensklingenberg.de.tuxoid.model.Element.Moveable;

import android.graphics.Bitmap;

import jensklingenberg.de.tuxoid.interfaces.Moveable;
import jensklingenberg.de.tuxoid.interfaces.Removable;
import jensklingenberg.de.tuxoid.model.Element.Element;
import jensklingenberg.de.tuxoid.model.Element.ElementGroup;
import jensklingenberg.de.tuxoid.model.MyImage;

/**
 * Created by jens on 11.04.17.
 */

public class Crate_Block extends Element implements Moveable, Removable {


    private Bitmap image;

    public Crate_Block(int type, int z, int y, int x) {
        super(type, z, y, x);
        this.image = MyImage.getImage(type);
    }

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
