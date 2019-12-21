package jensklingenberg.de.tuxoid.model.element;

public final class ElementType {
    public static final int BACKGROUND = 0;
    public static final int WALL = 1;
    public static final int CRATE_BLUE = 2;
    public static final int CRATE_BLOCK = 3;
    public static final int FISH = 4;

    public static final int TELEIN1 = 20;
    public static final int TELEOUT1 = 21;
    public static final int WATER = 30;
    public static final int WOOD = 31;
    public static final int WOOD_ON_WATER = 32;
    public static final int Wood_on_WATER_horizontal = 33;
    public static final int MOVING_WOOD = 34;
    public static final int MOVING_WATER = 35;
    public static final int RED_BUTTON = 40;
    public static final int PLAYER = 48;
    public static final int DOOR1 = 51;
    public static final int DOOR2 = 52;
    public static final int DOOR3 = 53;
    public static final int DOOR4 = 54;
    public static final int SWITCH = 55;
    public static final int SWITCH_CRATE_BLOCK = 56;
    public static final int KEY1 = 61;
    public static final int KEY2 = 62;
    public static final int KEY3 = 63;
    public static final int KEY4 = 64;
    public static final int LADDER_DOWN = 70;
    public static final int LADDER_UP = 71;
    public static final int NPC1 = 72;
    public static final int NPC2 = 73;
    public static final int NPC3 = 74;
    public static final int NPC4 = 75;
    public static final int HOLE1 = 81;
    public static final int HOLE_EXIT = 82;
    public static final int EXIT = 91;
    public static final int GATE = 92;
    public static final int GATE_HALF = 93;
    public static final int ICE = 100;

    public static final int ARROW_UP = 110;
    public static final int ARROW_DOWN = 111;
    public static final int ARROW_LEFT = 112;
    public static final int ARROW_RIGHT = 113;

    public static final int ARROW_UP_RED = 114;
    public static final int ARROW_DOWN_RED = 115;
    public static final int ARROW_LEFT_RED = 116;
    public static final int ARROW_RIGHT_RED = 117;


    //Fish,keys

    //Background,Hole,RedButton,Telein, Switch

    //Crate_Block,CrateBlue

    //Player, NPC

    public enum DestinationGroup {


    }


    public enum ArrowGroup {
        /**
         *
         */

        ARROW_UP(110, "up"),
        ARROW_DOWN(111, "down"),
        ARROW_LEFT(112, "left"),
        ARROW_RIGHT(113, "right");

        int value;
        String direction = "";

        ArrowGroup(int value, String direction) {
            this.value = value;
            this.direction = direction;

        }

        public static boolean isInThisGroup(int value) {

            for (ArrowGroup arrow : ArrowGroup.values()) {

                if (arrow.getValue() == value) {
                    return true;
                }

            }
            return false;
        }

        public static String getDirection(int value) {

            for (ArrowGroup arrow : ArrowGroup.values()) {

                if (arrow.getValue() == value) {
                    return arrow.getDirection();
                }


            }
            return null;
        }

        public static String getGroup() {
            return "Arrow";
        }

        public String getDirection() {
            return direction;
        }

        public int getValue() {
            return value;
        }


    }


}
