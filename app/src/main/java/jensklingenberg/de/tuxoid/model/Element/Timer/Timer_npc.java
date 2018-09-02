package jensklingenberg.de.tuxoid.model.Element.Timer;

import android.util.Log;

import jensklingenberg.de.tuxoid.MainActivity;
import jensklingenberg.de.tuxoid.model.Coordinate;
import jensklingenberg.de.tuxoid.model.Element.Character.NPC;
import jensklingenberg.de.tuxoid.model.Element.Element;
import jensklingenberg.de.tuxoid.model.Element.ElementType;

public class Timer_npc implements Runnable {

    private static final String TAG = Element.class.getSimpleName();
    private MainActivity mainActivity;
    private int npc;
    private int type;

    /**
     * @param mainActivity
     */
    public Timer_npc(MainActivity mainActivity, int npc, int type) {
        this.mainActivity = mainActivity;
        this.npc = npc;
        this.type = type;
    }


    public void run() {

        Log.i(TAG, String.valueOf(npc));
        if (NPC.getMapNpcTimerStatus(npc)) {

            int ObjectZ = NPC.getMapNpcPosZ(npc);
            int ObjectY = NPC.getMapNpcPosY(npc);
            int ObjectX = NPC.getMapNpcPosX(npc);

            this.mainActivity.levelHelper.checkMove(
                    NPC.getMapNpcDirection(npc),
                    ElementType.BACKGROUND, new Coordinate(this.mainActivity.levelHelper.aktEbene,
                            ObjectY, ObjectX), Element.Companion.elementFactory(type, ObjectZ, ObjectY, ObjectX)
            );

            //  this.mainActivity.handler.postDelayed(this, 300);

            NPC.startTimer(npc, 200, mainActivity, type);

        } else {
            NPC.stopTimer(npc);
        }

    }

}