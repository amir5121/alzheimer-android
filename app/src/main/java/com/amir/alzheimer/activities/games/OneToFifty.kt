package com.amir.alzheimer.activities.games

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RelativeLayout
import com.amir.alzheimer.R
import com.amir.alzheimer.base.BaseActivity
import kotlinx.android.synthetic.main.activity_one_to_fifthy.*
import kotlinx.android.synthetic.main.include_score_timer.*
import java.util.*
import kotlin.math.sqrt


class OneToFifty : BaseActivity(), View.OnClickListener {
    private var mode: Int = COUNT_UP
    private var pauseCounter: Boolean = true
    private var lastClick: Int = 0
    private val hardnessLevels: IntArray = intArrayOf(18, 32, 50, 72, 98)
    private var hardnessLevel: Int = 0
    private var count: Int = hardnessLevels[hardnessLevel] * 3
    private lateinit var firstHalf: MutableList<Int>
    private lateinit var secondHalf: MutableList<Int>
    private val timer = Timer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_one_to_fifthy)

        activity_number_game_button_next_level.setOnClickListener(this)
        mode = intent.getIntExtra(MODE, COUNT_UP)
        when (mode) {
            COUNT_DOWN -> activity_one_to_fifty_help_text.text = getString(R.string.count_down)
            COUNT_2_UP -> activity_one_to_fifty_help_text.text = getString(R.string.count_up_2)
            COUNT_2_DOWN -> activity_one_to_fifty_help_text.text = getString(R.string.count_down_2)
        }
        if (intent.getIntExtra(HARDNESS_INTENT, hardnessLevel) != 0) {
            activity_number_game_level.visibility = View.GONE
        }

        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    activity_text_view_timer.text = count.toString()
                    if (count > 0 && !pauseCounter) count--
                }
            }
        }, 1000, 1000)

        updateBoard()
    }

    private fun updateBoard() {
        hardnessLevel = intent.getIntExtra(HARDNESS_INTENT, hardnessLevel)
        activity_number_game_text_level.text = (hardnessLevel + 1).toString()

        activity_number_game_button_next_level.visibility = View.GONE
        val smallest = if (displayHeight < displayWidth) displayHeight else displayWidth

        activity_one_to_fifty_grid.removeAllViews()
        val mid = hardnessLevels[hardnessLevel] / 2
        val midToHardness = (mid + 1..hardnessLevels[hardnessLevel]).shuffled().toMutableList()
        val oneToMid = (1..mid).shuffled() as MutableList<Int>
        firstHalf =
                when (mode) {
                    COUNT_UP -> oneToMid
                    COUNT_DOWN -> midToHardness
                    COUNT_2_UP -> (1..mid * 2 step 2).shuffled() as MutableList<Int>
                    COUNT_2_DOWN -> (mid * 2 + 1..hardnessLevels[hardnessLevel] * 2 step 2).shuffled().toMutableList()
                    else -> oneToMid
                }
        secondHalf =
                when (mode) {
                    COUNT_UP -> midToHardness
                    COUNT_DOWN -> oneToMid
                    COUNT_2_UP -> (mid * 2 + 1..hardnessLevels[hardnessLevel] * 2 step 2).shuffled().toMutableList()
                    COUNT_2_DOWN -> (1..mid * 2 step 2).shuffled() as MutableList<Int>
                    else -> midToHardness
                }

        val itemCountIntRow = sqrt(mid.toDouble())
        activity_one_to_fifty_grid.columnCount = itemCountIntRow.toInt()
        val width = ((smallest - (activity_one_to_fifty_grid.layoutParams as RelativeLayout.LayoutParams).topMargin * 2) / itemCountIntRow).toInt()

        for (i in firstHalf) {
            val view = this.layoutInflater.inflate(R.layout.one_to_fifthy_button, activity_one_to_fifty_grid, false) as Button
            view.setOnClickListener(this)
            view.layoutParams = ViewGroup.LayoutParams(width, width)
            view.text = i.toString()
            activity_one_to_fifty_grid.addView(view)
        }

        when (mode) {
            COUNT_DOWN -> lastClick = hardnessLevels[hardnessLevel] + 1
            COUNT_2_UP -> lastClick = -1
            COUNT_2_DOWN -> lastClick = hardnessLevels[hardnessLevel] * 2 + 1

        }

    }

    override fun onClick(view: View?) {
        when (view) {
            activity_number_game_button_next_level -> {
                if (hardnessLevel < hardnessLevels.size) hardnessLevel++
                count += hardnessLevels[hardnessLevel] * hardnessLevel
                lastClick = 0
                updateBoard()
            }
            is Button -> {
                val clickedNext = view.text.toString().toInt() == lastClick + 1
                val clickedNext2 = view.text.toString().toInt() == lastClick + 2
                val clickedBefore2 = view.text.toString().toInt() == lastClick - 2
                val clickedBefore = view.text.toString().toInt() == lastClick - 1
                if (clickedNext || clickedBefore || clickedNext2 || clickedBefore2) {
                    pauseCounter = false
                    when {
                        clickedNext -> lastClick++
                        clickedBefore -> lastClick--
                        clickedNext2 -> lastClick += 2
                        clickedBefore2 -> lastClick -= 2
                    }
                    if (secondHalf.isNotEmpty())
                        view.text = secondHalf.removeAt(0).toString()
                    else {
                        view.text = ""
                        view.isClickable = false
                    }

                    if (lastClick == hardnessLevels[hardnessLevel] || lastClick == 1) {
                        pauseCounter = true
                        activity_number_game_button_next_level.visibility = View.VISIBLE
                    }
                }
            }
        }
    }


    companion object {
        const val COUNT_UP = 0
        const val COUNT_DOWN = 2
        const val COUNT_2_UP = 1
        const val COUNT_2_DOWN = 12
        const val HARDNESS_INTENT = "HARDNESS_INTENT"
        const val MODE = "MODE"
    }
}