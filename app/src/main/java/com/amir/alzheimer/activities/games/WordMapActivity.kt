package com.amir.alzheimer.activities.games

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.amir.alzheimer.R
import com.amir.alzheimer.base.BaseActivity
import com.amir.alzheimer.infrastructure.Constants
import kotlinx.android.synthetic.main.activity_word_map.*
import kotlinx.android.synthetic.main.include_score_timer.*
import java.util.*
import kotlin.collections.ArrayList


class WordMapActivity : BaseActivity(), View.OnClickListener, AdapterView.OnItemClickListener {
    private val timer = Timer()
    private var toRemember: List<String> = listOf()


    private var toRememberSize = 0
    private var lastClickedView: TextView? = null
    private var count: Int = 0
    private var mode = WORD
    private var itemFound: Int = 0
    private var pauseCounter: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_word_map)
        activity_number_game_text_level.text = "1"
        word_map_button_ready.setOnClickListener(this)

        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    activity_text_view_timer.text = count.toString()
                    if (count > 0 && !pauseCounter) count++
                }
            }
        }, 1000, 1000)

        mode = intent.getIntExtra(MAP_MODE, WORD)
        if (mode == NUMBER_PLATE)
            word_map_list_left.visibility = View.VISIBLE

        updateBoard()
    }

    override fun onClick(v: View?) {
        when (v) {
            word_map_button_ready -> {
                if (mode == NUMBER_PLATE) {
                    finish()
                    return
                }
                word_map_button_ready.visibility = View.GONE
                if (itemFound > 0) {
                    word_map_list_left.visibility = View.GONE
                    itemFound = 0
                    pauseCounter = true
                    activity_number_game_text_level.text = (activity_number_game_text_level.text.toString().toInt() + 1).toString()
                } else {
                    word_map_list_left.visibility = View.VISIBLE
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
            word_map_list_right -> {
                if (view is TextView) {
                    lastClickedView?.background = ContextCompat.getDrawable(this, R.drawable.card_background)
                    lastClickedView = view
                    view.background = ContextCompat.getDrawable(this, R.drawable.card_background_green)
                }
            }

            word_map_list_left -> {

                if (view is TextView && toRemember.indexOf(
                                lastClickedView?.text.toString()) + toRememberSize == toRemember.indexOf(view.text.toString())) {
                    lastClickedView = null
                    view.background = ContextCompat.getDrawable(this, R.drawable.card_background_green)
                    itemFound += 1
                    if (itemFound >= toRememberSize) {
                        pauseCounter = true
                        word_map_button_ready.text = getString(R.string.next_level)
                        word_map_button_ready.visibility = View.VISIBLE
                    }
                }
            }
        }
    }


    private fun updateBoard() {
        toRemember = if (mode == WORD)
            Constants.TO_REMEMBER_SOURCE.slice(0 until 10)
        else
            listOf("بیرجند", "زنجان", "سمنان", "شیراز", "قم", "۵۲", "۸۷", "۸۶", "۶۳", "۱۶")
        toRememberSize = toRemember.size / 2

        if (word_map_list_left.visibility == View.GONE) {
            word_map_button_ready.visibility = View.VISIBLE
            word_map_button_ready.text = getString(R.string.ready)
            val matches: ArrayList<String> = ArrayList()
            for (i in 0 until toRememberSize) {
                matches.add("${toRemember[i]}-${toRemember[i + toRememberSize]}")
            }
            val mainListAdapter = ArrayAdapter(this, R.layout.simple_list_item, matches)
            word_map_list_right.adapter = mainListAdapter
        } else {

            val firstHalfAdapter = ArrayAdapter(
                    this, R.layout.simple_list_item, toRemember.slice(0 until toRememberSize).shuffled())
            val secondHalfAdapter = ArrayAdapter(
                    this, R.layout.simple_list_item, toRemember.slice(toRememberSize until toRemember.size).shuffled())
            word_map_list_right.adapter = firstHalfAdapter
            word_map_list_left.adapter = secondHalfAdapter

            word_map_list_right.onItemClickListener = this
            word_map_list_left.onItemClickListener = this
        }

    }

    companion object {
        const val TAG: String = "WordMapActivity"
        const val MAP_MODE: String = "MAP_MODE"
        const val WORD = 1
        const val NUMBER_PLATE = 2
    }

}
