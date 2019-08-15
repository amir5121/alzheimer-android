package com.amir.alzheimer.activities.games

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.amir.alzheimer.R
import com.amir.alzheimer.infrastructure.adapter.MathExpressionAdapter
import com.amir.alzheimer.infrastructure.exprk.Expressions
import kotlinx.android.synthetic.main.activity_math_question.*

class MathQuestionActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var adapter: MathExpressionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_math_question)
        adapter = MathExpressionAdapter(this)
        activity_math_question_list.adapter = adapter
        activity_math_question_button_answer.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            activity_math_question_button_answer -> {
                adapter.answers = adapter.items.map { Expressions().eval(it).toDouble() }
                adapter.notifyDataSetChanged()
                activity_math_question_button_answer.visibility = View.GONE
            }
        }

    }
}
