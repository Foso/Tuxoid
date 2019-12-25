package de.jensklingenberg.tuxoid.ui.menu

class MenuPresenter(val view: MenuContract.View) : MenuContract.Presenter {


    override fun onCreate() {
            view.setData()

        }

}