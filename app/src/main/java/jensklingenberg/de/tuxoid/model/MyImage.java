package jensklingenberg.de.tuxoid.model;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;

import de.jensklingenberg.tuxoid.R;
import jensklingenberg.de.tuxoid.MainActivity;
import jensklingenberg.de.tuxoid.model.Element.ElementType;

import static android.graphics.BitmapFactory.decodeResource;

public class MyImage {


    private static Bitmap imgArrowUp;
    private static Bitmap imgArrowRight;
    private static Bitmap imgArrowLeft;
    private static Bitmap imgArrowDown;

    private static Bitmap imgArrowUp_Red;
    private static Bitmap imgArrowRight_Red;
    private static Bitmap imgArrowLeft_Red;
    private static Bitmap imgArrowDown_Red;

    private static Bitmap imgBackground;
    private static Bitmap imgCrateBlue;
    private static Bitmap imgCrateBlock;
    private static Bitmap imgDoor1;
    private static Bitmap imgDoor2;
    private static Bitmap imgFish;
    private static Bitmap imgGate;
    private static Bitmap imgGate_HALF;
    private static Bitmap imgHole;
    private static Bitmap imgHoleExit;
    private static Bitmap imgIce;
    private static Bitmap imgKey1;
    private static Bitmap imgKey2;
    private static Bitmap imgLadder;
    private static Bitmap imgNPC1;

    private static Bitmap imgPlayer;
    private static Bitmap imgRedButton;
    private static Bitmap imgSwitch;
    private static Bitmap imgSwitch_Crate_Block;
    private static Bitmap imgTeleOut1;
    private static Bitmap imgTeleIn1;
    private static Bitmap imgWall;
    private static Bitmap imgWater;
    private static Bitmap imgWood;
    private static Bitmap imgWood_on_water;
    private static Bitmap imgWood_on_water_horizontal;
private Context context;



    public MyImage(Context context) {
        this.context=context;
      //  getResources();
        imgArrowUp = decodeResource(getResources(), R.drawable.arrowup);
        imgArrowDown = decodeResource(getResources(), R.drawable.arrow_down);
        imgArrowRight = decodeResource(getResources(), R.drawable.arrow_right);
        imgArrowLeft = decodeResource(getResources(), R.drawable.arrow_left);
        imgArrowUp_Red = decodeResource(getResources(), R.drawable.arrow_up_red);
        imgArrowDown_Red = decodeResource(getResources(), R.drawable.arrow_down_red);
        imgArrowRight_Red = decodeResource(getResources(), R.drawable.arrow_right_red);
        imgArrowLeft_Red = decodeResource(getResources(), R.drawable.arrow_left_red);
        imgBackground = decodeResource(getResources(), R.drawable.background);
        imgCrateBlock = decodeResource(getResources(), R.drawable.crate_blockp);
        imgCrateBlue = decodeResource(getResources(), R.drawable.crate_blue2p);
        imgDoor1 = decodeResource(getResources(), R.drawable.door1p);
        //imgDoor2 = BitmapFactory.decodeResource(MainActivity.getActivity().getResources(), R.drawable.door2);
        imgFish = decodeResource(getResources(), R.drawable.fish32);
        imgGate = decodeResource(getResources(), R.drawable.gate);
        imgGate_HALF = decodeResource(getResources(), R.drawable.gate_half);
        imgHole = decodeResource(getResources(), R.drawable.hole4p);
        imgHoleExit = decodeResource(getResources(), R.drawable.background2);
        imgIce = decodeResource(getResources(), R.drawable.ice32);
        imgKey1 = decodeResource(getResources(), R.drawable.key1p);
        //  imgKey2 = BitmapFactory.decodeResource(MainActivity.getActivity().getResources(), R.drawable.key2);
        imgLadder = decodeResource(getResources(), R.drawable.ladder32);
        //  imgNPC1 = BitmapFactory.decodeResource(MainActivity.getActivity().getResources(), R.drawable.background32);
        imgPlayer = decodeResource(getResources(), R.drawable.player2p);
        imgRedButton = decodeResource(getResources(), R.drawable.button);
        imgSwitch = decodeResource(getResources(), R.drawable.switch1);
        imgSwitch_Crate_Block = decodeResource(getResources(), R.drawable.switch_crate_block);
        imgTeleIn1 = decodeResource(getResources(), R.drawable.portal_red);
        imgTeleOut1 = decodeResource(getResources(), R.drawable.portal_blue);
        imgWall = decodeResource(getResources(), R.drawable.wallp);
        imgWater = decodeResource(getResources(), R.drawable.water3p);
        imgWood = decodeResource(getResources(), R.drawable.wood);
        imgWood_on_water = decodeResource(getResources(), R.drawable.wood_on_water);
        imgWood_on_water_horizontal = decodeResource(getResources(), R.drawable.wood_on_water_horizontal);
    }

    public static Bitmap getImage(int type) {
        switch (type) {

            case ElementType.ARROW_UP:
                return imgArrowUp;

            case ElementType.ARROW_DOWN:
                return imgArrowDown;


            case ElementType.ARROW_RIGHT:
                return imgArrowRight;

            case ElementType.ARROW_LEFT:
                return imgArrowLeft;


            case ElementType.ARROW_UP_RED:
                return imgArrowUp_Red;

            case ElementType.ARROW_DOWN_RED:
                return imgArrowDown_Red;

            case ElementType.ARROW_RIGHT_RED:
                return imgArrowRight_Red;

            case ElementType.ARROW_LEFT_RED:
                return imgArrowLeft_Red;


            case ElementType.BACKGROUND:
                return imgBackground;
//
            case ElementType.CRATE_BLUE:
                return imgCrateBlue;
//
            case ElementType.CRATE_BLOCK:
                return imgCrateBlock;
//
            case ElementType.DOOR1:
                return imgDoor1;
//
            case ElementType.DOOR2:
                return imgDoor2;

            case ElementType.EXIT:
                return imgWall;
//
            case ElementType.FISH:
                return imgFish;
//
            case ElementType.ICE:
                return imgIce;
//
            case ElementType.LADDER_DOWN:
                return imgLadder;
//
            case ElementType.LADDER_UP:
                return imgLadder;
//
            case ElementType.GATE:
                return imgGate;

            case ElementType.GATE_HALF:
                return imgGate_HALF;
//
            case ElementType.HOLE1:
                return imgHole;
//
            case ElementType.HOLE_EXIT:
                return imgBackground;
//
            case ElementType.KEY1:
                return imgKey1;
//
            case ElementType.KEY2:
                return imgKey2;

            case ElementType.MOVING_WATER:
                return imgWater;

            case ElementType.MOVING_WOOD:
                return imgWood_on_water;

            case ElementType.NPC1:
                return imgNPC1;

            case ElementType.NPC2:
                return imgNPC1;

            case ElementType.NPC3:
                return imgNPC1;

//
            case ElementType.PLAYER:
                return imgPlayer;
//
            case ElementType.RED_BUTTON:
                return imgRedButton;
//
            case ElementType.SWITCH:
                return imgSwitch;

            case ElementType.SWITCH_CRATE_BLOCK:
                return imgSwitch_Crate_Block;
//
            case ElementType.TELEIN1:
                return imgTeleIn1;
//
            case ElementType.TELEOUT1:
                return imgTeleOut1;
//
            case ElementType.WALL:
                return imgWall;

            case ElementType.WATER:
                return imgWater;

            case ElementType.WOOD:
                return imgWood;

            case ElementType.WOOD_ON_WATER:
                return imgWood_on_water;

            case ElementType.Wood_on_WATER_horizontal:
                return imgWood_on_water_horizontal;

            default:
                return imgBackground;

        }

    }

    private Resources getResources() {
        return context.getResources();
    }
}
