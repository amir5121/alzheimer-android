package com.amir.alzheimer.activities.games

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.amir.alzheimer.R
import com.amir.alzheimer.infrastructure.adapter.OccurrenceAdapter
import kotlinx.android.synthetic.main.activity_occurrence.*

class OccurrenceActivity : AppCompatActivity(), View.OnClickListener, AdapterView.OnItemClickListener {
    private var mode = OccurrenceAdapter.SINGLE_DIGIT
    private lateinit var adapter: OccurrenceAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_occurrence)
        mode = intent.getIntExtra(OCCURRENCE_MODE, OccurrenceAdapter.SINGLE_DIGIT)
        if (mode == OccurrenceAdapter.PATTERN) {
            activity_occurrence_help_text.text = getText(R.string.memorize_the_pattern)
        }
        adapter = OccurrenceAdapter(this, mode)
        activity_occurrence_grid.adapter = adapter
        activity_occurrence_grid.numColumns = 4
        activity_occurrence_button_ready.setOnClickListener(this)
        activity_occurrence_grid.onItemClickListener = this
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (view != null && adapter.initiated) {
            if (mode != OccurrenceAdapter.PATTERN && adapter.oldItems.filter { it == adapter.items[position] }.size > 1) {
                view.setBackgroundColor(ContextCompat.getColor(this, R.color.green_lighter))
            } else if (adapter.oldItems[position] == adapter.green) {
                if (adapter.oldItems.filter { it == adapter.green }.size - 1 == adapter.items.filter { it == adapter.green }.size)
                    adapter.items = adapter.items.map { if (it == adapter.lightGreen) adapter.white else it } as ArrayList<Int>
                adapter.items[position] = adapter.green
                adapter.notifyDataSetChanged()
            }
        }
    }


    override fun onClick(v: View?) {
        when (v) {
            activity_occurrence_button_ready -> {
                if (!adapter.initiated) {
                    adapter.initiate()
                    activity_occurrence_button_ready.text = getText(R.string.next)
                    if (mode != OccurrenceAdapter.PATTERN)
                        activity_occurrence_help_text.text = getText(R.string.more_than_one_occurrence)
                } else {
                    finish()
                }
            }
        }
    }

    companion object {
        const val OCCURRENCE_MODE = "OCCURRENCE_MODE"
    }

}
