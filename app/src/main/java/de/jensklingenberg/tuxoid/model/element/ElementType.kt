package de.jensklingenberg.tuxoid.model.element

class ElementType {
    companion object{
        const val BACKGROUND = 0
        const val WALL = 1
        const val CRATE_BLUE = 2
        const val CRATE_BLOCK = 3
        const val FISH = 4
        const val TELEIN1 = 20
        const val TELEOUT1 = 21
        const val WATER = 30
        const val WOOD = 31
        const val WOOD_ON_WATER = 32
        const val Wood_on_WATER_horizontal = 33
        const val MOVING_WOOD = 34
        const val MOVING_WATER = 35
        const val RED_BUTTON = 40
        const val PLAYER = 48
        const val DOOR1 = 51
        const val DOOR2 = 52
        const val DOOR3 = 53
        const val DOOR4 = 54
        const val SWITCH = 55
        const val SWITCH_CRATE_BLOCK = 56
        const val KEY1 = 61
        const val KEY2 = 62
        const val KEY3 = 63
        const val KEY4 = 64
        const val LADDER_DOWN = 70
        const val LADDER_UP = 71
        const val NPC1 = 72
        const val NPC2 = 73
        const val NPC3 = 74
        const val NPC4 = 75
        const val HOLE1 = 81
        const val HOLE_EXIT = 82
        const val EXIT = 91
        const val GATE = 92
        const val GATE_HALF = 93
        const val ICE = 100
        const val ARROW_UP = 110
        const val ARROW_DOWN = 111
        const val ARROW_LEFT = 112
        const val ARROW_RIGHT = 113
        const val ARROW_UP_RED = 114
        const val ARROW_DOWN_RED = 115
        const val ARROW_LEFT_RED = 116
        const val ARROW_RIGHT_RED = 117
    }


    //Fish,keys
//Background,Hole,RedButton,Telein, Switch
//Crate_Block,CrateBlue
//Player, NPC
    enum class DestinationGroup

    enum class ArrowGroup(var value: Int, direction: String) {
        /**
         *
         */
        ARROW_UP(110, "up"),
        ARROW_DOWN(111, "down"), ARROW_LEFT(112, "left"), ARROW_RIGHT(113, "right");

        var direction = ""

        companion object {
            fun isInThisGroup(value: Int): Boolean {
                for (arrow in values()) {
                    if (arrow.value == value) {
                        return true
                    }
                }
                return false
            }

            fun getDirection(value: Int): String? {
                for (arrow in values()) {
                    if (arrow.value == value) {
                        return arrow.direction
                    }
                }
                return null
            }

            val group: String
                get() = "Arrow"
        }

        init {
            this.direction = direction
        }
    }
}