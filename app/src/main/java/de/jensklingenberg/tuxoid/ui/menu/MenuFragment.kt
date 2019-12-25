package de.jensklingenberg.tuxoid.ui.menu

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import de.jensklingenberg.tuxoid.R
import de.jensklingenberg.tuxoid.ui.AnleitungActivity
import de.jensklingenberg.tuxoid.ui.MainActivity
import de.jensklingenberg.tuxoid.ui.MainFragmentArgs
import java.util.*

class MenuFragment : Fragment(R.layout.menu),MenuContract.View{

    var btnarr: ArrayList<Button>? = null
    var btnAnleitung: Button? = null
    var ll: LinearLayout? = null


    val presenter : MenuContract.Presenter = MenuPresenter(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onCreate()
    }

    override fun setData() {

        ll = view?.findViewById<View>(R.id.ll2) as LinearLayout

        btnarr = ArrayList()
        btnAnleitung = Button(requireContext())
        btnAnleitung!!.text = "Anleitung"
        btnAnleitung!!.setOnClickListener { v: View ->
            val intent = Intent(v.context, AnleitungActivity::class.java)
            startActivity(intent)
        }
        ll!!.addView(btnAnleitung)
        for (i in 0..19) {
            val btnEntry = Button(requireContext())
            btnEntry.text = "Level$i"
            btnEntry.setOnClickListener { v: View ->


                findNavController().navigate(R.id.mainFragment,MainFragmentArgs(i.toString()).toBundle())
            }
            ll!!.addView(btnEntry)
        }

    }

}