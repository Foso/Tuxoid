package de.jensklingenberg.tuxoid.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.Fragment
import de.jensklingenberg.tuxoid.App
import de.jensklingenberg.tuxoid.R
import de.jensklingenberg.tuxoid.data.Array2D
import de.jensklingenberg.tuxoid.data.Array3D
import de.jensklingenberg.tuxoid.model.element.Element
import de.jensklingenberg.tuxoid.ui.GameFragmentArgs.Companion.fromBundle
import de.jensklingenberg.tuxoid.ui.common.GView
import de.jensklingenberg.tuxoid.ui.common.SidebarImageView
import kotlinx.android.synthetic.main.fragment_main.*
import kotlin.math.floor

class GameFragment : Fragment(R.layout.fragment_main), GameContract.View {
    private var presenter: GameContract.Presenter = GamePresenter(this)
    private var gView: GView? = null

    init {
        App.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //ImageRepository(context)
        gView = canvas

        arguments?.let {
            val level = fromBundle(it).levelId
            presenter.createLevel(level.toInt())
        }
    }

    override fun onRefresh(levelData: Array2D<Element>) {
        gView?.refresh(levelData)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun setGameData(levelData1: Array2D<Element>) {

        val width = canvas.width
        val height = canvas.height
        val lvlRows = levelData1.size
        val lvlColums: Int = levelData1[0].size


        gView?.widthL = width / lvlColums
        gView?.heightL = height / lvlRows
        gView?.setOnTouchListener { v, event ->
            (v as GView)
            if (event.action == MotionEvent.ACTION_DOWN) {
                val cellWidth = (v).widthL
                val cellHeight = (v).heightL
                val rawX = event.x
                val rawY = event.y
                val rowIndex = floor(rawX/cellWidth.toDouble()).toInt()
                val colIndex = floor(rawY/cellHeight.toDouble()).toInt()
                presenter.screenTouched(colIndex, rowIndex)
            }

            true
        }

    }

    override fun setSidebarData(sidebar: Array3D<Element>) {
        val rel = sidebarLayout
        for (elements in sidebar[0]) {
            val side = SidebarImageView(context, elements[0])
            rel.addView(side)
        }
    }
}