package com.amir.alzheimer.activites.games

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.gridlayout.widget.GridLayout

import com.amir.alzheimer.R
import com.amir.alzheimer.base.BaseActivity

import java.util.Arrays
import java.util.Collections
import java.util.Random

class NumberGameActivity : BaseActivity(), View.OnClickListener {
    private lateinit var numbers: IntArray
    private lateinit var toFind: IntArray
    private var gridLayout: GridLayout? = null
    private var solving = false
    private var updated = false
    private var score = 0
    private var hitCount = 0
    private var scoreText: TextView? = null
    private var actionButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_number_game)
        gridLayout = findViewById(R.id.activity_number_game_grid)
        updateNumber(3)

        actionButton = findViewById(R.id.activity_number_game_continue_button)
        actionButton!!.setOnClickListener(this)
        scoreText = findViewById(R.id.activity_number_game_score)
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
        Log.wtf(TAG, "update_grid: " + gridLayout!!.width)
        if (gridLayout!!.width != 0 && !updated) {
            update_grid()
            updated = true
        }

    }


    private fun update_grid() {
        gridLayout!!.removeAllViews()
        gridLayout!!.columnCount = Math.sqrt(numbers.size.toDouble()).toInt()

        for (i in numbers.indices) {
            val view = LayoutInflater.from(this).inflate(R.layout.number_game_item, gridLayout, false)
            view.layoutParams.width = gridLayout!!.width / Math.sqrt(numbers.size.toDouble()).toInt()
            view.setOnClickListener(this)
            view.layoutParams.height = gridLayout!!.height / (Math.sqrt(numbers.size.toDouble()).toInt() + if (solving) 1 else 0)
            gridLayout!!.addView(view)
        }

        for (i in 0 until gridLayout!!.childCount) {
            val textView = gridLayout!!.getChildAt(i) as TextView
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
                    update_grid()
                    hitCount = 0
                    actionButton!!.text = resources.getText(R.string.solve)
                } else {
                    Toast.makeText(application, "There are " + Math.abs(hitCount - toFind.size) + " remaining!", Toast.LENGTH_SHORT).show()
                }
            } else {
                val array1and2 = IntArray(numbers.size + toFind.size)
                System.arraycopy(numbers, 0, array1and2, 0, numbers.size)
                System.arraycopy(toFind, 0, array1and2, numbers.size, toFind.size)
                numbers = array1and2
                shuffleArray(numbers)
                solving = true
                gridLayout!!.setBackgroundColor(resources.getColor(R.color.solving_color))
                update_grid()
            }
        } else {
            if (view is TextView) {
                if (solving) {
                    if (hasValue(toFind, Integer.valueOf(view.text.toString()))) {
                        if (hitCount < toFind.size) {
                            view.setBackgroundColor(resources.getColor(R.color.green))
                            view.setTextColor(resources.getColor(R.color.white))
                            view.isClickable = false
                            score++
                            view.textSize = 25f
                            hitCount++
                        }
                        if (hitCount >= toFind.size) {
                            actionButton!!.text = resources.getText(R.string.next)
                        }
                    } else {
                        view.setBackgroundColor(resources.getColor(R.color.red))
                        view.isClickable = false
                        view.setTextColor(resources.getColor(R.color.white))
                        score--
                    }

                    scoreText!!.text = score.toString()
                }
            }
        }
    }

    companion object {

        private val TAG = "NumberGameActivity"

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

        private fun hasValue(numbers: IntArray, `val`: Int): Boolean {
            for (i in numbers) if (`val` == i) return true
            return false
        }
    }
}
