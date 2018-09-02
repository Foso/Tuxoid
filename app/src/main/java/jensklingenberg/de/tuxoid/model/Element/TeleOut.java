package jensklingenberg.de.tuxoid.model.Element;

/**
 * Created by jens on 11.02.16.
 */
public class TeleOut extends Element {

    private static int[] TeleOutPos = new int[3];


    TeleOut() {

    }

    public static void setTeleOutPos(int z, int y, int x) {
        TeleOutPos = new int[]{z, y, x};
    }


    //TELEIN
    public static int getTeleOutPosZ() {
        return TeleOutPos[0];
    }

    public static int getTeleOutPosY() {
        return TeleOutPos[1];
    }

    public static int getTeleOutPosX() {
        return TeleOutPos[2];
    }
}
