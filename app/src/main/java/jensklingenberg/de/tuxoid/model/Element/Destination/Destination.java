package jensklingenberg.de.tuxoid.model.Element.Destination;

import jensklingenberg.de.tuxoid.interfaces.IReachable;
import jensklingenberg.de.tuxoid.model.Element.Element;
import jensklingenberg.de.tuxoid.model.Element.ElementGroup;

/**
 * Created by jens on 18.04.17.
 */

public class Destination extends Element implements IReachable {


    Destination(int type, int z, int y, int x) {
        super(type, z, y, x);
    }


    public ElementGroup getElementGroup() {
        return ElementGroup.Destination;
    }
}
