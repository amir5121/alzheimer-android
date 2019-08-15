package com.amir.alzheimer.activities.games

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import com.amir.alzheimer.R
import com.amir.alzheimer.infrastructure.adapter.FindAdapter
import kotlinx.android.synthetic.main.activity_find.*

class FindActivity : AppCompatActivity(), AdapterView.OnItemClickListener, View.OnClickListener {
    private lateinit var adapter: FindAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find)
        val mode = intent.getIntExtra(FIND_MODE, FindAdapter.IMAGE)
        if (mode == FindAdapter.NUMBER) {
            activity_find_text_help.text = getString(R.string.tap_evens)
            activity_find_button_ready.visibility = View.GONE
        }
        adapter = FindAdapter(this, mode)
        activity_find_button_ready.setOnClickListener(this)
        activity_find_image_grid.numColumns = 5
        activity_find_image_grid.onItemClickListener = this
        activity_find_image_grid.adapter = adapter
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (adapter.items[position] in adapter.toFind) {
            adapter.found[position] = true
            adapter.notifyDataSetChanged()
        }
    }

    override fun onClick(v: View?) {
        when (v) {
            activity_find_button_ready -> {
                adapter.initiated = true
                adapter.notifyDataSetChanged()
                adapter.items.shuffle()
                activity_find_button_ready.visibility = View.GONE
            }
        }

    }

    companion object {
        const val FIND_MODE = "FIND_MODE"
    }

}
