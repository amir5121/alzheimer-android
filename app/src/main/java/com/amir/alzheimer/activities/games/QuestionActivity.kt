package com.amir.alzheimer.activities.games

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.amir.alzheimer.R
import kotlinx.android.synthetic.main.activity_question.*

class QuestionActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)
        val hasAnswer = intent.getBooleanExtra(HAS_ANSWER, true)

        activity_question_button.setOnClickListener(this)
        activity_question_question.text = intent.getStringExtra(QUESTION)
        if (!hasAnswer) {
            activity_question_question.textSize = 23F
        }

        activity_question_answer.visibility = if (hasAnswer) View.VISIBLE else View.GONE

        if (intent.getBooleanExtra(HAS_IMAGE, false)) {
            activity_question_image.visibility = View.VISIBLE
        }

    }

    companion object {
        const val HAS_IMAGE = "HAS_IMAGE"
        const val QUESTION = "QUESTION"
        const val HAS_ANSWER = "HAS_ANSWER"
    }
}
