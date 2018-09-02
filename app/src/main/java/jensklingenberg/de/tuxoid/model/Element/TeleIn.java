package jensklingenberg.de.tuxoid.model.Element;

/**
 * Created by jens on 11.02.16.
 */
public class TeleIn extends Element {

    private static int[] TeleInPos = new int[3];


    TeleIn() {
        super();
    }

    public static void setTeleInPos(int z, int y, int x) {
        TeleInPos = new int[]{z, y, x};
    }


    //TELEIN
    public static int getTeleInPosZ() {
        return TeleInPos[0];
    }

    public static int getTeleInPosY() {
        return TeleInPos[1];
    }

    public static int getTeleInPosX() {
        return TeleInPos[2];
    }
}
