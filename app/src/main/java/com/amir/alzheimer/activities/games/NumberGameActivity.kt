package com.amir.alzheimer.activities.games

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat

import com.amir.alzheimer.R
import com.amir.alzheimer.base.BaseActivity
import kotlinx.android.synthetic.main.activity_number_game.*

import java.util.Arrays
import java.util.Random
import kotlin.math.abs
import kotlin.math.sqrt

class NumberGameActivity : BaseActivity(), View.OnClickListener {
    private lateinit var numbers: IntArray
    private lateinit var toFind: IntArray
    private var solving = false
    private var updated = false
    private var score = 0
    private var hitCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_number_game)
        updateNumber(3)

        activity_number_game_continue_button.setOnClickListener(this)
    }

    private fun updateNumber(size: Int) {
        val numbers = IntArray(size * size + size)
        for (i in numbers.indices) {
            numbers[i] = i + 1
        }
        //        Collections.shuffle(Arrays.asList(numbers));
        shuffleArray(numbers)
        this.numbers = Arrays.copyOfRange(numbers, 0, size * size)
        toFind = Arrays.copyOfRange(numbers, size * size, numbers.size)
        //        return this.numbers;

    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        Log.wtf(TAG, "updateGrid: " + activity_number_game_grid.width)
        if (activity_number_game_grid.width != 0 && !updated) {
            updateGrid()
            updated = true
        }

    }


    private fun updateGrid() {
        activity_number_game_grid.removeAllViews()
        activity_number_game_grid.columnCount = sqrt(numbers.size.toDouble()).toInt()

        for (i in numbers.indices) {
            val view = LayoutInflater.from(this).inflate(R.layout.number_game_item, activity_number_game_grid, false)
            view.layoutParams.width = activity_number_game_grid.width / sqrt(numbers.size.toDouble()).toInt()
            view.setOnClickListener(this)
            view.layoutParams.height = activity_number_game_grid.height / (sqrt(numbers.size.toDouble()).toInt() + if (solving) 1 else 0)
            activity_number_game_grid.addView(view)
        }

        for (i in 0 until activity_number_game_grid.childCount) {
            val textView = activity_number_game_grid.getChildAt(i) as TextView
            textView.text = numbers[i].toString()
        }
    }

    override fun onClick(view: View) {
        val id = view.id
        if (id == R.id.activity_number_game_continue_button) {
            if (solving) {
                if (hitCount >= toFind.size) {
                    solving = false
                    updateNumber(4)
                    (findViewById<View>(R.id.activity_number_game_hint) as TextView).text = resources.getText(R.string.to_20)
                    updateGrid()
                    hitCount = 0
                    activity_number_game_continue_button.text = resources.getText(R.string.solve)
                } else {
                    Toast.makeText(application, "There are " + abs(hitCount - toFind.size) + " remaining!", Toast.LENGTH_SHORT).show()
                }
            } else {
                val array1and2 = IntArray(numbers.size + toFind.size)
                System.arraycopy(numbers, 0, array1and2, 0, numbers.size)
                System.arraycopy(toFind, 0, array1and2, numbers.size, toFind.size)
                numbers = array1and2
                shuffleArray(numbers)
                solving = true
                activity_number_game_grid.setBackgroundColor(ContextCompat.getColor(this, R.color.solving_color))
                updateGrid()
            }
        } else {
            if (view is TextView) {
                if (solving) {
                    if (hasValue(toFind, Integer.valueOf(view.text.toString()))) {
                        if (hitCount < toFind.size) {
                            view.setBackgroundColor(ContextCompat.getColor(this, R.color.green))
                            view.setTextColor(ContextCompat.getColor(this, R.color.white))
                            view.isClickable = false
                            score++
                            view.textSize = 25f
                            hitCount++
                        }
                        if (hitCount >= toFind.size) {
                            activity_number_game_continue_button.text = resources.getText(R.string.next)
                        }
                    } else {
                        view.setBackgroundColor(ContextCompat.getColor(this, R.color.red))
                        view.isClickable = false
                        view.setTextColor(ContextCompat.getColor(this, R.color.white))
                        score--
                    }

                    activity_number_game_score.text = score.toString()
                }
            }
        }
    }

    companion object {

        private const val TAG = "NumberGameActivity"

        internal fun shuffleArray(ar: IntArray) {
            val rnd = Random()
            for (i in ar.size - 1 downTo 1) {
                val index = rnd.nextInt(i + 1)
                // Simple swap
                val a = ar[index]
                ar[index] = ar[i]
                ar[i] = a
            }
        }

        private fun hasValue(numbers: IntArray, value: Int): Boolean {
            for (i in numbers) if (value == i) return true
            return false
        }
    }
}
