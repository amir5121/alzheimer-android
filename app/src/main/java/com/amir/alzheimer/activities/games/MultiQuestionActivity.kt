package com.amir.alzheimer.activities.games

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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

        activity_multi_question_viewpager.adapter = MultiQuestionAdapter(
                this,
                listOf(
                        getString(R.string.multi_memory_question),
                        getString(R.string.multi_memory_question),
                        getString(R.string.multi_memory_question),
                        getString(R.string.multi_memory_question)
                ),
                listOf(
                        R.drawable.duplicate_043,
                        R.drawable.duplicate_019,
                        R.drawable.duplicate_042,
                        R.drawable.duplicate_023
                )
        )
        activity_multi_item_button.setOnClickListener(this)
    }
}
