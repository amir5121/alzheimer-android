package com.amir.alzheimer.infrastructure.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.core.content.ContextCompat
import com.amir.alzheimer.R
import kotlinx.android.synthetic.main.image_index_item.view.*


class OccurrenceAdapter(val context: Context, val mode: Int) : BaseAdapter() {
    val green = ContextCompat.getColor(context, R.color.green)
    val white = ContextCompat.getColor(context, R.color.white)
    val lightGreen = ContextCompat.getColor(context, R.color.green_lighter)
    var items: ArrayList<Int> =
            when (mode) {
                SINGLE_DIGIT -> (1..20).map { (1..12).random() }
                DOUBLE_DIGIT -> (1..20).map { (10..22).random() }
                else -> (1..20).map { listOf(green, white).random() }
            } as ArrayList<Int>
    var oldItems: List<Int> = items
    private var inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    var initiated = false

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val gridItem: View = convertView
                ?: inflater.inflate(R.layout.image_index_item, parent, false)
        gridItem.index_item_index_text.visibility = View.GONE
        if (mode == PATTERN)
            gridItem.setBackgroundColor(items[position])
        else
            gridItem.index_text_text.text = items[position].toString()
        return gridItem
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
        items = if (mode == PATTERN) {
            (1..20).map { lightGreen } as ArrayList<Int>
        } else {
            (if (mode == SINGLE_DIGIT) (1..12).toList() else (10..22).toList()) as ArrayList<Int>
        }
        notifyDataSetChanged()
    }

    companion object {
        const val DOUBLE_DIGIT = 1
        const val SINGLE_DIGIT = 2
        const val PATTERN = 3
    }

}
