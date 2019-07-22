package com.amir.alzheimer.activities.games

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import com.amir.alzheimer.R
import com.amir.alzheimer.base.BaseActivity
import com.amir.alzheimer.infrastructure.AlzhimerButton
import kotlinx.android.synthetic.main.activity_duplicate.*
import kotlinx.android.synthetic.main.include_score_timer.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.sqrt


class DuplicateActivity : BaseActivity(), View.OnClickListener, AdapterView.OnItemClickListener {
    private val timer = Timer()
    private var toRemember: List<String> = listOf()
    private var toRememberSource: List<String> = listOf(
            "خورشید", "آفتاب", "روز", "سواد", "فریاد", "ماشین", "تلوزیون", "ساعت", "بوق", "دیوار",
            "دیو", "ترس", "تنفر", "شاذی", "کمند", "آرزو", "آبی", "مو", "کمد", "لامپ", "صدا", "مداد",
            "بوق", "خودکار", "نوک", "طلایی", "کتاب"
    )

    private var toRememberSize = 0
    private var lastClickedView: TextView? = null
    private var count: Int = 0
    private var itemFound: Int = 0
    private var mode: Int = REMEMBER
    private var pauseCounter: Boolean = true


    override fun onClick(v: View?) {
        when (v) {
            activity_duplicate_button_ready -> {
                activity_duplicate_button_ready.visibility = View.GONE
                if (itemFound > 0) {
                    activity_duplicate_list_left.visibility = View.GONE
                    itemFound = 0
                    pauseCounter = true
                    activity_number_game_text_level.text = (activity_number_game_text_level.text.toString().toInt() + 1).toString()
                } else {
                    activity_duplicate_list_left.visibility = View.VISIBLE
                    pauseCounter = false
                }
                updateBoard()
            }
            is Button -> {
                when (v.text.toString()) {

                }
            }
        }
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (parent) {
            activity_duplicate_list_right -> {
                if (view is TextView) {
                    lastClickedView?.background = ContextCompat.getDrawable(this, R.drawable.card_background)
                    lastClickedView = view
                    view.background = ContextCompat.getDrawable(this, R.drawable.card_background_green)
                }
            }

            activity_duplicate_list_left -> {

                if (view is TextView && toRemember.indexOf(
                                lastClickedView?.text.toString()) + toRememberSize == toRemember.indexOf(view.text.toString())) {
                    lastClickedView = null
                    view.background = ContextCompat.getDrawable(this, R.drawable.card_background_green)
                    itemFound += 1
                    if (itemFound >= toRememberSize) {
                        pauseCounter = true
                        activity_duplicate_button_ready.text = getString(R.string.next_level)
                        activity_duplicate_button_ready.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_duplicate)
        activity_number_game_text_level.text = "1"
        activity_duplicate_button_ready.setOnClickListener(this)

        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    activity_text_view_timer.text = count.toString()
                    if (count > 0 && !pauseCounter) count++
                }
            }
        }, 1000, 1000)

        updateBoard()


    }


    private fun updateBoard() {
        when (mode) {
            DUPLICATE -> {
                var firstHalf: Iterable<Int> = arrayListOf(1, 2, 24, 1, 3, 23, 7, 8)
                firstHalf += firstHalf
                firstHalf.shuffled()

                val smallest = if (displayHeight < displayWidth) displayHeight else displayWidth

                activity_duplicate_grid.removeAllViews()

                val itemCountIntRow = sqrt(firstHalf.count().toDouble())

                activity_duplicate_grid.columnCount = itemCountIntRow.toInt()
                val width = ((smallest - (activity_duplicate_grid.layoutParams as RelativeLayout.LayoutParams).topMargin * 2) / itemCountIntRow.toInt())
                for (i in firstHalf) {
                    val view = this.layoutInflater.inflate(R.layout.one_to_fifthy_button, activity_duplicate_grid, false) as Button
                    view.setOnClickListener(this)
                    view.layoutParams = ViewGroup.LayoutParams(width, width)
                    view.text = i.toString()
                    activity_duplicate_grid.addView(view)
                }

            }

            REMEMBER -> {
                if (activity_duplicate_list_left.visibility == View.GONE) {
                    toRememberSource = toRememberSource.shuffled()
                    toRemember = toRememberSource.slice(0 until 10)
                    toRememberSize = toRemember.size / 2
                    activity_duplicate_button_ready.visibility = View.VISIBLE
                    activity_duplicate_button_ready.text = getString(R.string.ready)
                    val matches: ArrayList<String> = ArrayList()
                    for (i in 0 until toRememberSize) {
                        matches.add("${toRemember[i]}-${toRemember[i + toRememberSize]}")
                    }
                    val mainListAdapter = ArrayAdapter(this, R.layout.simple_list_item, matches)
                    activity_duplicate_list_right.adapter = mainListAdapter
                } else {

                    val firstHalfAdapter = ArrayAdapter(
                            this, R.layout.simple_list_item, toRemember.slice(0 until toRememberSize).shuffled())
                    val secondHalfAdapter = ArrayAdapter(
                            this, R.layout.simple_list_item, toRemember.slice(toRememberSize until toRemember.size).shuffled())
                    activity_duplicate_list_right.adapter = firstHalfAdapter
                    activity_duplicate_list_left.adapter = secondHalfAdapter

                    activity_duplicate_list_right.onItemClickListener = this
                    activity_duplicate_list_left.onItemClickListener = this
                }
            }

        }
    }

    companion object {
        const val DUPLICATE: Int = 0
        const val REMEMBER: Int = 2
        const val TAG: String = "DuplicateActivity"
    }

}
