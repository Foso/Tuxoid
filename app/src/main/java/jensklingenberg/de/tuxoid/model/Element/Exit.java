package jensklingenberg.de.tuxoid.model.Element;

/**
 * Created by jens on 11.02.16.
 */
public class Exit extends Element {

    private static int[] exitPos = new int[3];


    public Exit(int type, int z, int y, int x) {
        super(type, z, y, x);
        Exit.setExitPos(z, y, x);
    }

    public static void setExitPos(int z, int y, int x) {
        exitPos = new int[]{z, y, x};
    }


    //TELEIN
    public static int getExitPosZ() {
        return exitPos[0];
    }

    public static int getExitPosY() {
        return exitPos[1];
    }

    public static int getExitPosX() {
        return exitPos[2];
    }
}
