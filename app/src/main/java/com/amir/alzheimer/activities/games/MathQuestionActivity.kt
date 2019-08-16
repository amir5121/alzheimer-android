package com.amir.alzheimer.activities.games

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.amir.alzheimer.R
import com.amir.alzheimer.infrastructure.adapter.MathQuestionAdapter
import kotlinx.android.synthetic.main.activity_math_question.*

class MathQuestionActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var adapter: MathQuestionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_math_question)
        adapter = MathQuestionAdapter(this, intent.getIntExtra(MATH_MODE, MathQuestionAdapter.EXPRESSION))
        activity_math_question_list.adapter = adapter
        activity_math_question_button_answer.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            activity_math_question_button_answer -> {
                adapter.initiate()
                activity_math_question_button_answer.visibility = View.GONE
            }
        }

    }

    companion object {
        const val MATH_MODE = "MATH_MODE"
    }
}
