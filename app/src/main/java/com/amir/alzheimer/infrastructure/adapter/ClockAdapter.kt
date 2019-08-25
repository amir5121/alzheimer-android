package com.amir.alzheimer.infrastructure.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.amir.alzheimer.R
import kotlinx.android.synthetic.main.item_clock.view.*


class ClockAdapter(val context: Context) : BaseAdapter() {
    var initiated = false
    lateinit var picked: String
    var items: ArrayList<String> = (0..3).map { "${(1..12).random()}:${(0..60 step 5).toList().random()}" } as ArrayList<String>
    private var inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val gridItem: View = convertView
                ?: inflater.inflate(R.layout.item_clock, parent, false)
        val hourDegree = items[position].split(':')[0].toInt() * 30 + 90
        gridItem.item_clock_big_hand.rotation = hourDegree.toFloat()
        val minuteDegree = items[position].split(':')[1].toInt() * 6 + 90
        gridItem.item_clock_small_hand.rotation = minuteDegree.toFloat()
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
        val elements = ((0..2).map { "${(1..12).random()}:${(0..60 step 5).toList().random()}" } as ArrayList)
        picked = items.random()
        initiated = true
        elements.add(picked)
        items = elements.shuffled() as ArrayList<String>
        notifyDataSetChanged()
    }

}
