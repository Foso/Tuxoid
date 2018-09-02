package jensklingenberg.de.tuxoid.model.Element.Timer;

import jensklingenberg.de.tuxoid.MainActivity;
import jensklingenberg.de.tuxoid.model.Coordinate;
import jensklingenberg.de.tuxoid.model.Element.Character.Player;
import jensklingenberg.de.tuxoid.model.Element.Element;
import jensklingenberg.de.tuxoid.model.Element.ElementType;

public final class Timer_ice implements Runnable {
    /**
     *
     */
    private final MainActivity mainActivity;
    int Object;

    /**
     * @param mainActivity
     */
    public Timer_ice(MainActivity mainActivity, int Object) {
        this.mainActivity = mainActivity;
        this.Object = Object;
    }

    @Override
    public void run() {


        if (this.mainActivity.getIceTimer()) {

            this.mainActivity.levelHelper.checkMove(Player.getPlayerDirection(), ElementType.ICE,

                    new Coordinate(
                            this.mainActivity.levelHelper.aktEbene, Player.getPlayPosY(), Player.getPlayPosX()),
                    Element.Companion.elementFactory(ElementType.PLAYER, Player.getPlayPosZ(), Player.getPlayPosY(),
                            Player.getPlayPosX()));

       //     this.mainActivity.levelHelper.getHandler().postDelayed(this, 200);

        }

    }
}