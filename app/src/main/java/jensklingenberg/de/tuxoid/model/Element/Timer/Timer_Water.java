package jensklingenberg.de.tuxoid.model.Element.Timer;

import android.util.SparseArray;

import jensklingenberg.de.tuxoid.MainActivity;
import jensklingenberg.de.tuxoid.model.Coordinate;
import jensklingenberg.de.tuxoid.model.Element.ElementType;
import jensklingenberg.de.tuxoid.model.MyGame;

public final class Timer_Water implements Runnable {
    /**
     *
     */
    private final MainActivity mainActivity;

    /**
     * @param mainActivity
     */
    public Timer_Water(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    static Coordinate newCoordinates(int z, int y, int x) {
        return new Coordinate(z, y, x);
    }

    @Override
    public void run() {
          /* do what you need to do */

        SparseArray<int[]> mapMoving = MyGame.getMapMoving();
        int mwZ = MyGame.getMoving_Wood()[0];
        int mwY = MyGame.getMoving_Wood()[1];
        int mwX = MyGame.getMoving_Wood()[2];

        int dirX = 0;

        if (mwX == mapMoving.get(mapMoving.size() - 1)[2]) {
            this.mainActivity.bMovingDirRight = false;

        }

        if (mwX == mapMoving.get(0)[2]) {
            this.mainActivity.bMovingDirRight = true;

        }

        if (this.mainActivity.bMovingDirRight == true) {
            dirX = 1;
        } else if (this.mainActivity.bMovingDirRight == false) {
            dirX = -1;
        }


        if (this.mainActivity.levelHelper.level[mwZ - 1][mwY][mwX + dirX].getType() == ElementType.HOLE1) {
            System.out.println("hole");
        }

        if (this.mainActivity.levelHelper.level[mwZ][mwY][mwX].getType() == ElementType.PLAYER) {
            this.mainActivity.levelHelper.setPos(ElementType.PLAYER, newCoordinates(mwZ, mwY, mwX + dirX));
            this.mainActivity.levelHelper.setPos(ElementType.MOVING_WATER, newCoordinates(mwZ, mwY, mwX));
            MyGame.setMoving_Wood(mwZ, mwY, mwX + dirX);

        } else if (this.mainActivity.levelHelper.level[mwZ][mwY][mwX].getType() == ElementType.CRATE_BLOCK) {
            this.mainActivity.levelHelper.setPos(ElementType.CRATE_BLOCK, newCoordinates(mwZ, mwY, mwX + dirX));
            this.mainActivity.levelHelper.setPos(ElementType.MOVING_WATER, newCoordinates(mwZ, mwY, mwX));
            MyGame.setMoving_Wood(mwZ, mwY, mwX + dirX);

        } else if (this.mainActivity.levelHelper.level[mwZ][mwY][mwX].getType() == ElementType.CRATE_BLUE) {
            this.mainActivity.levelHelper.setPos(ElementType.CRATE_BLUE, newCoordinates(mwZ, mwY, mwX + dirX));
            this.mainActivity.levelHelper.setPos(ElementType.MOVING_WATER, newCoordinates(mwZ, mwY, mwX));
            MyGame.setMoving_Wood(mwZ, mwY, mwX + dirX);

        } else {
            this.mainActivity.levelHelper.setPos(ElementType.MOVING_WATER, newCoordinates(mwZ, mwY, mwX));
            this.mainActivity.levelHelper.setPos(ElementType.MOVING_WOOD, newCoordinates(mwZ, mwY, mwX + dirX));
        }

        //this.mainActivity.refresh();

	      /* and here comes the "trick" */
        //this.mainActivity.levelHelper.getHandler().postDelayed(this, 1000);
    }
}