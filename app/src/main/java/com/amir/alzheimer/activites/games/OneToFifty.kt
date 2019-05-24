package com.amir.alzheimer.activites.games

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RelativeLayout
import com.amir.alzheimer.R
import com.amir.alzheimer.base.BaseActivity
import kotlinx.android.synthetic.main.activity_one_to_fifthy.*
import java.util.*
import kotlin.collections.ArrayList


class OneToFifty : BaseActivity(), View.OnClickListener {
    private var pauseCounter: Boolean = true
    private var lastClick: Int = 0
    private val hardnesses: IntArray = intArrayOf(18, 32, 50, 72, 98)
    private var hardnessLevel: Int = 0
    private var count: Int = hardnesses[hardnessLevel] * 5
    private var secondHalf: MutableList<Int> = ArrayList()
    private val timer = Timer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_one_to_fifthy)

        activity_number_game_button_next_level.setOnClickListener(this)
        updateBoard()

        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    activity_one_to_fifty_text_view_timer.text = count.toString()
                    if (count > 0 && !pauseCounter) count--
                }
            }
        }, 1000, 1000)
    }

    private fun updateBoard() {
        activity_number_game_text_level.text = (hardnessLevel + 1).toString()

        activity_number_game_button_next_level.visibility = View.GONE
        val smallest = if (displayHeight < displayWidth) displayHeight else displayWidth

        activity_one_to_fifty_grid.removeAllViews()
        val firstHalf: IntArray = (1..hardnesses[hardnessLevel] / 2).toList().shuffled().toIntArray()
        secondHalf = (hardnesses[hardnessLevel] / 2 + 1..hardnesses[hardnessLevel]).toList().shuffled().toMutableList()

        val itemCountIntRow = Math.sqrt((firstHalf.size).toDouble())
        activity_one_to_fifty_grid.columnCount = itemCountIntRow.toInt()
        val width = ((smallest - (activity_one_to_fifty_grid.layoutParams as RelativeLayout.LayoutParams).topMargin * 2) / itemCountIntRow).toInt()

        for (i in firstHalf) {
            val view = this.layoutInflater.inflate(R.layout.one_to_fifthy_button, activity_one_to_fifty_grid, false) as Button
            view.setOnClickListener(this)
            view.layoutParams = ViewGroup.LayoutParams(width, width)
            view.text = i.toString()
            activity_one_to_fifty_grid.addView(view)
        }
    }

    override fun onClick(p0: View?) {
        when (p0) {
            activity_number_game_button_next_level -> {
                if (hardnessLevel < hardnesses.size) hardnessLevel++
                count += hardnesses[hardnessLevel] * hardnessLevel
                lastClick = 0
                updateBoard()
            }
            is Button -> {
                if (p0.text.toString().toInt() == lastClick + 1) {
                    pauseCounter = false
                    lastClick++
                    if (secondHalf.isNotEmpty())
                        p0.text = secondHalf.removeAt(0).toString()
                    else {
                        p0.text = ""
                        p0.isClickable = false
                    }

                    if (lastClick == hardnesses[hardnessLevel]) {
                        pauseCounter = true
                        activity_number_game_button_next_level.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

}