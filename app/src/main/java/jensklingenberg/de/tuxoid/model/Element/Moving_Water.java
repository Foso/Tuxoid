package jensklingenberg.de.tuxoid.model.Element;

import jensklingenberg.de.tuxoid.model.Game;

/**
 * Created by jens on 19.04.17.
 */

public class Moving_Water extends Element {

    public Moving_Water(int type, int z, int y, int x) {
        super(type, z, y, x);
        Game.INSTANCE.getMapMoving().put(Moving.MovingCount, new int[]{z, y, x});
        Moving.MovingCount++;
    }


}
