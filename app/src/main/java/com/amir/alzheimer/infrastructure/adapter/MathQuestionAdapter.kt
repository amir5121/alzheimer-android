package com.amir.alzheimer.infrastructure.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.amir.alzheimer.R
import com.amir.alzheimer.infrastructure.Constants
import com.amir.alzheimer.infrastructure.exprk.Expressions
import kotlinx.android.synthetic.main.math_expression_item.view.*


class MathQuestionAdapter(context: Context, val mode: Int) : BaseAdapter() {
    private var items: List<String>
    private var answers: ArrayList<Double> = arrayListOf()
    private var initiated: Boolean = false
    private var inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    init {
        val questionCount = 4
        when (mode) {
            EXPRESSION -> {
                items = (1..questionCount).map { Constants.getExpression(3, 1..10) }
                answers = items.map { Expressions().eval(it).toDouble() } as ArrayList<Double>
            }
            EXPRESSION_2 -> {
                items = (1..questionCount + 1).map { Constants.getExpression(4, 1..100) }
                answers = items.map { Expressions().eval(it).toDouble() } as ArrayList<Double>
            }
            else -> items = (1..questionCount).map {
                val start = (1..9).random()
                val operator = Constants.OPERATORS_SEQUENCE.random()

                val resultArray = arrayListOf(start)

                for (i in 0..2) {
                    resultArray.add(Expressions().eval("${resultArray.last()}$operator$start").toInt())
                }
                answers.add(resultArray.last().toDouble())
                resultArray.removeAt(resultArray.size - 1)
                resultArray.reversed().joinToString()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val listItem: View = convertView
                ?: inflater.inflate(R.layout.math_expression_item, parent, false)

        if (initiated) {
            listItem.math_expression_item_result.text =
                    if (mode == EXPRESSION) "%.2f".format(answers[position]) else answers[position].toInt().toString()
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

    fun initiate() {
        initiated = true
        notifyDataSetChanged()
    }

    companion object {
        const val SEQUENCE = 0
        const val EXPRESSION = 1
        const val EXPRESSION_2 = 2
    }

}
