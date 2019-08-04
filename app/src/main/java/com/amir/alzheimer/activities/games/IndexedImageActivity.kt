package com.amir.alzheimer.activities.games

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import com.amir.alzheimer.R
import com.amir.alzheimer.base.BaseActivity
import com.amir.alzheimer.infrastructure.adapter.IndexedImageAdapter
import kotlinx.android.synthetic.main.activity_indexed_image.*
import kotlinx.android.synthetic.main.include_score_timer.*
import java.util.*

class IndexedImageActivity : BaseActivity(), View.OnClickListener {

    private var count: Int = 0

    private val timer = Timer()
    private lateinit var indexedImageAdapter: IndexedImageAdapter
    private var pauseCounter: Boolean = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_indexed_image)
        indexedImageAdapter = IndexedImageAdapter(this)
        activity_indexed_image_grid.adapter = indexedImageAdapter
        activity_indexed_image_grid.numColumns = 4
        activity_indexed_image_button_ready.setOnClickListener(this)
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    activity_text_view_timer.text = count.toString()
                    if (count > 0 && !pauseCounter) count++
                }
            }
        }, 1000, 1000)
    }

    override fun onClick(v: View?) {
        when (v) {
            activity_indexed_image_button_ready -> {
                if (indexedImageAdapter.initiated) {
                    if (indexedImageAdapter.sumOfIndexes() == activity_indexed_image_sum.text.toString().toInt()) {
                        finish()
                    } else {
                        Toast.makeText(this, getString(R.string.try_again), Toast.LENGTH_SHORT).show()
                    }
                } else {
                    indexedImageAdapter.initiate()
                    activity_indexed_image_button_ready.text = getString(R.string.verify)
                    activity_indexed_image_sum_container.visibility = View.VISIBLE
                }
            }
        }
    }

}