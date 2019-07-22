package com.amir.alzheimer.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.amir.alzheimer.R
import com.amir.alzheimer.activities.games.DuplicateActivity
import com.amir.alzheimer.activities.games.NumberGameActivity
import com.amir.alzheimer.activities.games.OneToFifty
import com.amir.alzheimer.activities.games.SquareMatchActivity
import com.amir.alzheimer.androidpuzzlegame.MainActivityPuzzle
import com.amir.alzheimer.base.BaseFragment
import kotlinx.android.synthetic.main.game_fragment.view.*

class GameFragment : BaseFragment(), AdapterView.OnItemClickListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.game_fragment, container, false)
        val context = context
        if (context != null) {
            val adapter = ArrayAdapter(
                    context,
                    R.layout.red_list_item,
                    listOf(
                            context.getString(R.string.puzzle),
                            context.getString(R.string.missing_number),
                            context.getString(R.string.match),
                            context.getString(R.string.count),
                            context.getString(R.string.duplicate)
                    )
            )
            view.game_fragment_list_view.adapter = adapter
            view.game_fragment_list_view.onItemClickListener = this
        }

        return view
    }

    override fun onItemClick(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
        val activity = activity
        if (activity != null) {
            when (i) {
                0 -> activity.startActivity(Intent(context, MainActivityPuzzle::class.java))
                1 -> activity.startActivity(Intent(context, NumberGameActivity::class.java))
                2 -> activity.startActivity(Intent(context, SquareMatchActivity::class.java))
                3 -> activity.startActivity(Intent(context, OneToFifty::class.java))
                4 -> activity.startActivity(Intent(context, DuplicateActivity::class.java))
            }
        }
    }
}
