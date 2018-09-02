package jensklingenberg.de.tuxoid.model.Element.Collectable;

import jensklingenberg.de.tuxoid.interfaces.IReachable;
import jensklingenberg.de.tuxoid.model.Element.Element;
import jensklingenberg.de.tuxoid.model.Element.ElementGroup;

/**
 * Created by jens on 18.04.17.
 */

public class Collectable extends Element implements IReachable {


    Collectable(int type, int z, int y, int x) {
        super(type, z, y, x);
    }


    @Override
    public ElementGroup getElementGroup() {
        return ElementGroup.Collectable;
    }
}
