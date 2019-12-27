package de.jensklingenberg.tuxoid.ui

import android.graphics.Point
import android.os.Bundle
import android.view.DragEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.Fragment
import de.jensklingenberg.tuxoid.App
import de.jensklingenberg.tuxoid.R
import de.jensklingenberg.tuxoid.data.Array2D
import de.jensklingenberg.tuxoid.data.Array3D
import de.jensklingenberg.tuxoid.data.ImageRepository
import de.jensklingenberg.tuxoid.model.element.Element
import de.jensklingenberg.tuxoid.ui.GameFragmentArgs.Companion.fromBundle
import de.jensklingenberg.tuxoid.ui.common.GView
import de.jensklingenberg.tuxoid.ui.common.SidebarImageView
import kotlinx.android.synthetic.main.fragment_main.*

class GameFragment : Fragment(R.layout.fragment_main), GameContract.View {
    private var presenter: GameContract.Presenter = GamePresenter(this)
    private var gView: GView? = null

    init {
        App.appComponent.inject(this)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ImageRepository(context)
        gView = canvas

        arguments?.let {
            val level = fromBundle(arguments!!).levelId
            presenter.createLevel(level.toInt())
        }
    }

    override fun onRefresh(levelData: Array2D<Element>) {
        gView?.refresh(levelData)
    }

    override fun setGameData(levelData1: Array2D<Element>) {
        var imgGameField: Array<Array<ImageView?>>?=null
         var grid: GridLayout? = glGame

        val display = requireActivity().windowManager?.defaultDisplay
        val size = Point()
        display?.getSize(size)
        val width = size.x - 100
        val height = size.y
        val lvlRows = levelData1.size
        val lvlColums: Int = levelData1[0].size
        grid?.rowCount = lvlRows
        grid!!.columnCount = lvlColums
        imgGameField = Array(lvlRows) { arrayOfNulls<ImageView>(lvlColums) }
        gView!!.widthL = width / lvlColums
        gView!!.heightL = height / lvlRows
        for (y in 0 until lvlRows) {
            for (x in 0 until levelData1[y].size) {
                val giv = AppCompatImageView(requireContext())
                // giv.setImageBitmap(levelData1[y][x].getImage());
                giv.id = x
                giv.tag = intArrayOf(y, x)
                giv.layoutParams = LinearLayout.LayoutParams(width / lvlColums, height / lvlRows)
                giv.setOnClickListener { v: View ->
                    val coord = v.tag as IntArray
                    presenter.screenTouched(coord[0], coord[1])
                }
                giv.setOnDragListener { v: View, event: DragEvent ->
                    if (event.action == DragEvent.ACTION_DROP) {
                        val cord = v.tag as IntArray
                        // presenter.onDrag(new Coordinate(aktEbene, cord[0], cord[1]), Sidebar.DragElement);
                    }
                    true
                }
                imgGameField[y][x] = giv
            }
        }
        for (anImgGameField in imgGameField) {
            for (anAnImgGameField in anImgGameField) {
                grid.addView(anAnImgGameField)
            }
        }

        onRefresh(levelData1)
    }

    override fun setSidebarData(sidebar: Array3D<Element>) {
        val rel = sidebarLayout
        for (elements in sidebar[0]) {
            val side = SidebarImageView(context, elements[0])
            rel.addView(side)
        }
    }
}