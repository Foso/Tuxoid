package jensklingenberg.de.tuxoid.model.Element.Character;

import android.graphics.Bitmap;
import android.util.Log;

import jensklingenberg.de.tuxoid.interfaces.Moveable;
import jensklingenberg.de.tuxoid.interfaces.Playable;
import jensklingenberg.de.tuxoid.model.Coordinate;
import jensklingenberg.de.tuxoid.model.Direction;
import jensklingenberg.de.tuxoid.model.Element.Element;
import jensklingenberg.de.tuxoid.model.Element.ElementGroup;
import jensklingenberg.de.tuxoid.model.MyImage;

/**
 * Created by jens on 09.02.16.
 */
public class Player extends Element implements Moveable, Playable {

    private static int[] playPos = new int[3]; //x,y,z
    private static Direction playDirection;
    private static Coordinate position;
    private Bitmap image;
    private int type;



    public Player(int type, int z, int y, int x) {
        super(type, z, y, x);
        this.type = type;
        this.image = MyImage.getImage(type);
        setPlayPos(z, y, x);

    }

    public static int getPlayPosX() {
        return playPos[2];
    }

    public static int getPlayPosY() {
        return playPos[1];
    }

    public static int getPlayPosZ() {
        return playPos[0];
    }

    public static void setPlayPos(int z, int y, int x) {
        playPos = new int[]{z, y, x};
        Log.i("TAG", "setPlayPos: " + z + " " + y + " " + x);
        position = new Coordinate(z, y, x);
    }

    public static Direction getPlayerDirection() {
        return playDirection;
    }

    public static void setPlayerDirection(Direction direction) {
        playDirection = direction;
    }

    public static Coordinate getPosition() {
        return position;
    }

    @Override
    public boolean isRemovable() {
        boolean reMoveable = true;
        return reMoveable;
    }

    @Override
    public ElementGroup getElementGroup() {
        return ElementGroup.charPlayer;
    }
}
