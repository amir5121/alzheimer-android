package com.amir.alzheimer.activities.games

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.RelativeLayout
import com.amir.alzheimer.R
import com.amir.alzheimer.base.BaseActivity
import com.amir.alzheimer.infrastructure.adapter.DuplicateAdapter
import kotlinx.android.synthetic.main.activity_duplicate.*
import kotlinx.android.synthetic.main.include_score_timer.*
import java.lang.Math.sqrt
import java.util.*

class DuplicateActivity : BaseActivity(), View.OnClickListener, AdapterView.OnItemClickListener {


    private var count: Int = 0

    private val timer = Timer()
    private lateinit var duplicateAdapter: DuplicateAdapter
    private var pauseCounter: Boolean = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_duplicate)
        activity_duplicate_grid.onItemClickListener = this
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    activity_text_view_timer.text = count.toString()
                    if (count > 0 && !pauseCounter) count++
                }
            }
        }, 1000, 1000)


        activity_duplicate_grid.numColumns = 4
        duplicateAdapter = DuplicateAdapter(this, HARDNESS)
        activity_duplicate_grid.adapter = duplicateAdapter
    }

    override fun onClick(v: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        private const val HARDNESS = 10
    }
}

