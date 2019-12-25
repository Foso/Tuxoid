package de.jensklingenberg.tuxoid.ui.menu

interface MenuContract{
    interface View{
            fun setData()
    }
    interface Presenter{
        fun onCreate()
    }
}

