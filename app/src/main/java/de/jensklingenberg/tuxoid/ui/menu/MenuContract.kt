package de.jensklingenberg.tuxoid.ui.menu

import android.widget.Button
import android.widget.LinearLayout
import java.util.*

interface MenuContract{
    interface View{
            fun setData()
    }
    interface Presenter{
        fun onCreate()
    }
}

class MenuPresenter(val view:MenuContract.View) : MenuContract.Presenter{

    var btnarr: ArrayList<Button>? = null
    var btnAnleitung: Button? = null
    var ll: LinearLayout? = null

    override fun onCreate() {
            view.setData()

        }

}