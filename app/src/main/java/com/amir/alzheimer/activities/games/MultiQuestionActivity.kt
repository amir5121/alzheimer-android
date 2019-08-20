package com.amir.alzheimer.activities.games

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.amir.alzheimer.R
import com.amir.alzheimer.infrastructure.adapter.MultiQuestionAdapter
import kotlinx.android.synthetic.main.activity_multi_question.*

class MultiQuestionActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_multi_question)

        activity_multi_item_question.text = intent.getStringExtra(QUESTION)

        var questions = intent.getStringArrayListExtra(MULTI_QUESTION)
        if (questions == null) questions = arrayListOf()

        var images = intent.getIntegerArrayListExtra(MULTI_IMAGES)
        if (images == null) images = arrayListOf()

        activity_multi_question_viewpager.adapter = MultiQuestionAdapter(
                this,
                questions,
                images,
                intent.getBooleanExtra(VISIBLE_TEXT, true),
                intent.getBooleanExtra(VISIBLE_IMAGE, true),
                intent.getIntExtra(ITEM_COUNT, 1)
        )
        activity_multi_item_button.setOnClickListener(this)
        activity_multi_question_indicator.setViewPager(activity_multi_question_viewpager)
    }

    companion object {
        const val MULTI_QUESTION = "MULTI"
        const val MULTI_IMAGES = "MULTI_IMAGES"
        const val QUESTION = "QUESTION"
        const val VISIBLE_TEXT = "VISIBLE_TEXT"
        const val VISIBLE_IMAGE = "VISIBLE_IMAGE"
        const val ITEM_COUNT = "ITEM_COUNT"
    }
}
