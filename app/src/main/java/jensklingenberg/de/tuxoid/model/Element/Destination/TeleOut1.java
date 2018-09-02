package jensklingenberg.de.tuxoid.model.Element.Destination;

import android.graphics.Bitmap;

import jensklingenberg.de.tuxoid.model.Element.Element;
import jensklingenberg.de.tuxoid.model.Element.ElementGroup;
import jensklingenberg.de.tuxoid.model.Element.TeleOut;
import jensklingenberg.de.tuxoid.model.MyImage;

/**
 * Created by jens on 18.04.17.
 */

public class TeleOut1 extends Element {


    private Bitmap image;
    private int type;

    public TeleOut1(int type, int z, int y, int x) {
        super(type, z, y, x);
        this.type = type;
        this.image = MyImage.getImage(type);
        TeleOut.setTeleOutPos(z, y, x);
    }


    @Override
    public Bitmap getImage() {
        return image;
    }

    @Override
    public int getType() {
        return type;
    }

    @Override
    public ElementGroup getElementGroup() {
        return ElementGroup.TeleportOut;
    }
}
