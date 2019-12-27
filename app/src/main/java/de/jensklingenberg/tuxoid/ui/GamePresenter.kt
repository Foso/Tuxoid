package de.jensklingenberg.tuxoid.ui

import de.jensklingenberg.tuxoid.App
import de.jensklingenberg.tuxoid.data.Array3D
import de.jensklingenberg.tuxoid.data.LevelDataSource
import de.jensklingenberg.tuxoid.model.Coordinate
import de.jensklingenberg.tuxoid.model.element.Element
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GamePresenter(private val view: GameContract.View) : GameContract.Presenter {
val compositeDisposable = CompositeDisposable()

    @Inject
    lateinit var levelDataSource: LevelDataSource

    init {
        App.appComponent.inject(this)
    }

    override fun createLevel(levelId: Int) {
        levelDataSource.setRefreshListener(this)

        levelDataSource.loadLevel(levelId)
                .subscribeOn(Schedulers.io())
                .observeOn(
                        AndroidSchedulers.mainThread()
                ).subscribeBy(onSuccess = {level->
                    view.setGameData(level.foregroundlevelData[levelDataSource.getAktEbene()])
                }).addTo(compositeDisposable)


        val sidebar: Array3D<Element>? = levelDataSource.loadSidebar(levelId)
        sidebar?.let {
            { sidebar: Array3D<Element> -> view.setSidebarData(sidebar) }
        }

    }

    override fun screenTouched(touchY: Int, touchX: Int) {
        levelDataSource.screenTouched(touchY, touchX)
    }

    override fun onDrag(coordinate: Coordinate, dragElement: Element) {
        levelDataSource.onDrag(coordinate, dragElement)
    }


    override fun onRefresh(arrayOfArrays: Array<Array<Element>>) {
        view.onRefresh(arrayOfArrays)

    }

    override fun onCreate() {

    }

    override fun onDestroy() {
        compositeDisposable.dispose()
    }


}