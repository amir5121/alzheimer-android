package com.amir.alzheimer.activities.games

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.AdapterView
import com.amir.alzheimer.R
import com.amir.alzheimer.base.BaseActivity
import com.amir.alzheimer.infrastructure.dto.DuplicateItem
import com.amir.alzheimer.infrastructure.adapter.DuplicateAdapter
import kotlinx.android.synthetic.main.activity_duplicate.*
import kotlinx.android.synthetic.main.include_score_timer.*
import java.util.*

class DuplicateActivity : BaseActivity(), View.OnClickListener, AdapterView.OnItemClickListener {


    private var count: Int = 0

    private val timer = Timer()
    private lateinit var duplicateAdapter: DuplicateAdapter
    private var lastClickedItem: Pair<DuplicateItem, Int>? = null
    private var pauseCounter: Boolean = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_duplicate)
        activity_duplicate_grid.onItemClickListener = this
        activity_duplicate_button_ready.setOnClickListener(this)
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    activity_text_view_timer.text = count.toString()
                    if (count > 0 && !pauseCounter) count++
                }
            }
        }, 1000, 1000)


        val hardness: Int
        val mode = intent.getIntExtra(DUPLICATE_MODE, DuplicateAdapter.IMAGE)
        if (mode == DuplicateAdapter.TEXT) {
            activity_duplicate_grid.numColumns = 6
            hardness = HARDNESS * 2 + 2
        } else {
            hardness = HARDNESS
            activity_duplicate_grid.numColumns = 4
        }
        duplicateAdapter = DuplicateAdapter(this, hardness, mode)
        activity_duplicate_grid.adapter = duplicateAdapter
    }


    override fun onClick(v: View?) {
        when (v) {
            activity_duplicate_button_ready -> {
                duplicateAdapter.setCoverVisibility(View.VISIBLE)
                activity_duplicate_button_ready.visibility = View.GONE
            }
        }
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val clickedItem = duplicateAdapter.getItemByIndex(position)
        if (!clickedItem.first.isFound()) {
            clickedItem.first.visibility[clickedItem.second] = View.GONE
            lastClickedItem?.let {
                if (it.first.image != clickedItem.first.image || it.first.text != clickedItem.first.text) {
                    Handler().postDelayed({
                        it.first.setCoverVisible()
                        clickedItem.first.setCoverVisible()
                        duplicateAdapter.notifyDataSetChanged()

                    }, 500)
                }
                lastClickedItem = null

            } ?: run {
                lastClickedItem = clickedItem
            }
            duplicateAdapter.notifyDataSetChanged()
        }

    }

    companion object {
        private const val HARDNESS = 8
        private const val TAG = "DuplicateActivity"
        const val DUPLICATE_MODE = "DUPLICATE_MODE"
    }
}

