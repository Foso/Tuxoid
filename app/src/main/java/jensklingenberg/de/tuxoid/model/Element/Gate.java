package jensklingenberg.de.tuxoid.model.Element;

import jensklingenberg.de.tuxoid.interfaces.Removable;

/**
 * Created by jens on 19.04.17.
 */

public class Gate extends Element implements Removable {

    static int[] Gate;

    public Gate(int type, int z, int y, int x) {
        super(type, z, y, x);
        Gate = new int[]{z, y, x};
    }

    public static int[] getGate() {
        return Gate;
    }

    @Override
    public boolean isRemovable() {
        return true;
    }
}
