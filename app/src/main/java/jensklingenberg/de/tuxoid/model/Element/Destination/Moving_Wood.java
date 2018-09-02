package jensklingenberg.de.tuxoid.model.Element.Destination;

import android.graphics.Bitmap;

import jensklingenberg.de.tuxoid.model.Element.Moving;
import jensklingenberg.de.tuxoid.model.Game;
import jensklingenberg.de.tuxoid.model.MyImage;

/**
 * Created by jens on 18.04.17.
 */

public class Moving_Wood extends Destination {


    private Bitmap image;
    private int type;

    public Moving_Wood(int type, int z, int y, int x) {
        super(type, z, y, x);
        this.type = type;
        this.image = MyImage.getImage(type);

        Moving.setMoving_Wood(z, y, x);
        Game.INSTANCE.setMoving_Wood(new int[]{z, y, x});
        Game.INSTANCE.getMapMoving().put(Moving.MovingCount, new int[]{z, y, x});
        Moving.MovingCount++;
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
