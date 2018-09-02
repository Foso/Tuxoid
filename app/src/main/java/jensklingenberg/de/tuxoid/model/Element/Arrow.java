package jensklingenberg.de.tuxoid.model.Element;

import android.graphics.Bitmap;

import jensklingenberg.de.tuxoid.interfaces.IReachable;
import jensklingenberg.de.tuxoid.model.Direction;
import jensklingenberg.de.tuxoid.model.MyImage;

/**
 * Created by jens on 09.02.16.
 */
public class Arrow extends Element implements IReachable {

    private String group = "";
    private Bitmap image;
    private boolean used;
    private Direction direction;

    public Arrow(int type, int z, int y, int x, boolean used, Direction direction) {
        super(type, z, y, x);
        this.group = ElementType.ArrowGroup.getGroup();
        this.image = MyImage.getImage(type);
        this.used = used;
        this.direction = direction;

    }

    public static Arrow newArrowRed(int type, int z, int y, int x) {

        Direction direction = null;

        switch (type) {
            case ElementType.ARROW_UP_RED:
                direction = Direction.UP;
                break;

            case ElementType.ARROW_DOWN_RED:
                direction = Direction.DOWN;
                break;

            case ElementType.ARROW_LEFT_RED:
                direction = Direction.LEFT;
                break;
            case ElementType.ARROW_RIGHT_RED:
                direction = Direction.RIGHT;
                break;
        }

        return new Arrow(type, z, y, x, false, direction);


    }

    public static Arrow newArrow(int type, int z, int y, int x) {

        Direction direction = null;

        switch (type) {
            case ElementType.ARROW_UP:
                direction = Direction.UP;
                break;

            case ElementType.ARROW_DOWN:
                direction = Direction.DOWN;
                break;

            case ElementType.ARROW_LEFT:
                direction = Direction.LEFT;
                break;
            case ElementType.ARROW_RIGHT:
                direction = Direction.RIGHT;
                break;
        }

        return new Arrow(type, z, y, x, true, direction);


    }


    @Override
    public Bitmap getImage() {
        return image;
    }

    public boolean getUsedStatus() {
        return used;
    }

    public Direction getDirection() {
        return direction;
    }

    @Override
    public ElementGroup getElementGroup() {
        return ElementGroup.Arrow;
    }
}
