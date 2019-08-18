package com.amir.alzheimer.infrastructure.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.viewpager.widget.PagerAdapter
import com.amir.alzheimer.R
import kotlinx.android.synthetic.main.multi_question_item.view.*


class MultiQuestionAdapter(
        context: Context,
        private val questions: List<String>,
        private val images: List<Int>
) : PagerAdapter() {

    private var inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }


    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        val gridItem = inflater.inflate(R.layout.multi_question_item, container, false)

        gridItem.activity_multi_item_question.text = questions[position]
        gridItem.activity_multi_item_question_image.setImageResource(images[position])
        container.addView(gridItem)
        return gridItem
    }

    override fun getCount(): Int {
        return questions.size
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as RelativeLayout)
    }

}
