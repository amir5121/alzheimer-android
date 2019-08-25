package com.amir.alzheimer.activities.games

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import com.amir.alzheimer.R
import com.amir.alzheimer.base.BaseActivity
import com.amir.alzheimer.infrastructure.adapter.ClockAdapter
import kotlinx.android.synthetic.main.activity_find.*

class ClockActivity : BaseActivity(), AdapterView.OnItemClickListener, View.OnClickListener {
    private lateinit var adapter: ClockAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find)
        adapter = ClockAdapter(this)
        activity_find_text_help.text = getString(R.string.clock_help)
        activity_find_image_grid.adapter = adapter
        activity_find_image_grid.numColumns = 2
        activity_find_image_grid.onItemClickListener = this
        activity_find_button_ready.setOnClickListener(this)

    }


    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        Log.wtf(javaClass.simpleName, "ASasdasdasd")
        if (adapter.initiated) {
            Log.wtf(javaClass.simpleName, adapter.picked)
            if (adapter.items[position] == adapter.picked) {
                Toast.makeText(this, getString(R.string.well_done), Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, getString(R.string.try_again), Toast.LENGTH_SHORT).show()
            }
        }
    }


    override fun onClick(v: View?) {
        when (v) {
            activity_find_button_ready -> {
                adapter.initiate()
                activity_find_button_ready.visibility = View.GONE
            }
        }

    }
}
