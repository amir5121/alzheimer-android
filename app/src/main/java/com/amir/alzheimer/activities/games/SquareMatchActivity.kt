package com.amir.alzheimer.activities.games

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import androidx.core.content.ContextCompat

import com.amir.alzheimer.R
import com.amir.alzheimer.base.BaseActivity
import kotlinx.android.synthetic.main.activity_square_match.*

import java.util.Random

class SquareMatchActivity : BaseActivity(), View.OnClickListener {
    private var step = 3
    private var score = 0
    private var isMatch: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_square_match)

        update()

        activity_square_match_match_button.setOnClickListener(this)
        activity_square_match_miss_button.setOnClickListener(this)
        Log.wtf(TAG, "width: $displayWidth height $displayHeight")

    }

    private fun update() {
        var first = generateMatrix(step)
        isMatch = Random().nextBoolean()
        apply(this.activity_square_match_first, first)
        if (isMatch) {
            val rot = Math.abs(Random().nextInt() % 4)
            for (i in 0 until rot) {
                first = rotateMatrixClockwise(first)
            }
            apply(this.activity_square_match_second, first)
        } else {
            apply(this.activity_square_match_second, generateMatrix(step))
        }

    }

    private fun apply(grid: GridLayout, matrix: Array<IntArray>) {
        val smallest = ((if (displayWidth < displayHeight) displayWidth else displayHeight) / 2.3).toInt()
        grid.removeAllViews()
        grid.columnCount = matrix[0].size
        for (i in 0 until matrix[0].size * matrix[0].size) {
            val view = View(this)
            if (matrix[i / matrix[0].size][i % matrix[0].size] == 1)
                view.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary))
            val size = smallest / matrix[0].size
            view.layoutParams = ViewGroup.LayoutParams(size, size)
            grid.addView(view)
        }
    }

    override fun onClick(view: View) {
        val id = view.id
        if (id == R.id.activity_square_match_match_button) {
            if (isMatch) {
                score++
                if (step < 6)
                    step++
            } else
                score--
            update()
        } else if (id == R.id.activity_square_match_miss_button) {
            if (isMatch) {
                score--
            }
            update()
        }
        activity_square_match_score.text = score.toString()

    }

    companion object {

        private const val TAG = "SquareMatchActivity"


        fun rotateMatrixClockwise(matrix: Array<IntArray>): Array<IntArray> {
            val rotated = Array(matrix[0].size) { IntArray(matrix.size) }

            for (i in 0 until matrix[0].size) {
                for (j in matrix.indices) {
                    rotated[i][j] = matrix[matrix.size - j - 1][i]
                }
            }

            return rotated
        }

        fun generateMatrix(size: Int): Array<IntArray> {

            val ran = Random()
            val matrix = Array(size) { IntArray(size) }
            for (i in 0 until size) {
                for (j in 0 until size) {
                    matrix[i][j] = Math.abs(ran.nextInt() % 2)
                }
            }
            return matrix
        }

        fun rotateMatrixCounterClockwise(matrix: Array<IntArray>): Array<IntArray> {
            val rotated = Array(matrix[0].size) { IntArray(matrix.size) }

            for (i in 0 until matrix[0].size) {
                for (j in matrix.indices) {
                    rotated[i][j] = matrix[j][matrix[0].size - i - 1]
                }
            }

            return rotated
        }
    }
}
