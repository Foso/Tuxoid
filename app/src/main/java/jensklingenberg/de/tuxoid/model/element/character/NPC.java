package jensklingenberg.de.tuxoid.model.element.character;

import android.os.Handler;
import android.util.Log;
import android.util.SparseArray;

import jensklingenberg.de.tuxoid.interfaces.Moveable;
import jensklingenberg.de.tuxoid.interfaces.Playable;
import jensklingenberg.de.tuxoid.model.Direction;
import jensklingenberg.de.tuxoid.model.element.Element;
import jensklingenberg.de.tuxoid.model.element.ElementGroup;
import jensklingenberg.de.tuxoid.model.element.Timer.Timer_npc;

/**
 * Created by jens on 11.02.16.
 */
public class NPC extends Element implements Moveable, Playable {

    public static final Handler handler = new Handler();
    private static final String TAG = Element.class.getSimpleName();
    private static SparseArray<int[]> mapNpc = new SparseArray<int[]>();
    private static SparseArray<Direction> mapNpcDirection = new SparseArray<>();
    private static SparseArray<Boolean> mapNpcTimerStatus = new SparseArray<>();
    private static SparseArray<Timer_npc> mapNpcTimerRunnable = new SparseArray<>();
    private ElementGroup group;
    private String direction = "";
    private int NpcNumber;
    private int type;

    public NPC(int type, int z, int y, int x, int npcNumber) {
        super(type, z, y, x);
        this.NpcNumber = npcNumber;
        this.group = ElementGroup.charNPC;
        mapNpc.put(npcNumber, new int[]{z, y, x});
        this.type = type;
        if (mapNpcTimerStatus.get(npcNumber) == null) {
            setMapNpcTimerStatus(npcNumber, false);
        }

    }

    public static void setMapNpcCoordinates(int i, int z, int y, int x) {
        mapNpc.put(i, new int[]{z, y, x});
    }

    public static int getMapNpcPosZ(int i) {
        return mapNpc.get(i)[0];
    }

    public static int getMapNpcPosY(int i) {
        return mapNpc.get(i)[1];
    }

    public static int getMapNpcPosX(int i) {
        return mapNpc.get(i)[2];
    }

    public static Direction getMapNpcDirection(int i) {
        return mapNpcDirection.get(i);
    }

    public static boolean getMapNpcTimerStatus(int i) {
        return mapNpcTimerStatus.get(i);
    }

    public static Timer_npc getMapNpcTimerRunnable(int npcNumber) {
        return mapNpcTimerRunnable.get(npcNumber);
    }

    public static void setMapNpcTimerStatus(int i, boolean TimerStatus) {
        mapNpcTimerStatus.put(i, TimerStatus);

    }

    public static void setMapNpcDirection(int i, Direction direction) {
        mapNpcDirection.put(i, direction);
    }

    public static void setMapNpcTimerRunnable(int npcNumber, Timer_npc timer_npc) {
        mapNpcTimerRunnable.put(npcNumber, timer_npc);
    }

    public static void startTimer(int npcNumber, int time, Timer_npc.TimerClock activity, int type) {

        setMapNpcTimerRunnable(npcNumber, new Timer_npc(activity, npcNumber, type));
        handler.postDelayed(getMapNpcTimerRunnable(npcNumber), time);
    }

    public static void stopTimer(int npcNumber) {
        Log.i(TAG, String.valueOf("ddddddd"));

        handler.removeCallbacks(getMapNpcTimerRunnable(npcNumber));

    }

    public int getNpcNumber() {
        return NpcNumber;
    }


    @Override
    public ElementGroup getElementGroup() {
        return ElementGroup.charNPC;
    }
}
