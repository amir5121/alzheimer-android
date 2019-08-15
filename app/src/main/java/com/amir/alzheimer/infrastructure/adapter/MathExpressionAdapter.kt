package com.amir.alzheimer.infrastructure.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.amir.alzheimer.R
import com.amir.alzheimer.infrastructure.Constants
import kotlinx.android.synthetic.main.math_expression_item.view.*


class MathExpressionAdapter(context: Context) : BaseAdapter() {
    var items: List<String> = (1..5).map { Constants.getExpression(4) }
    var answers: List<Double> = listOf()
    private var inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    @SuppressLint("SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val listItem: View = convertView
                ?: inflater.inflate(R.layout.math_expression_item, parent, false)

        if (answers.isNotEmpty()) {
            listItem.math_expression_item_result.text = "%.2f".format(answers[position])
            listItem.math_expression_item_result.visibility = View.VISIBLE
        }

        listItem.math_expression_item_text.text = items[position]
        return listItem
    }

    override fun getItem(position: Int): Any {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return items.size
    }

}
