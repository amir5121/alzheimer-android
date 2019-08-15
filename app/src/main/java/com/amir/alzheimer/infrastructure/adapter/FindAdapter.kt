package com.amir.alzheimer.infrastructure.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.core.content.ContextCompat
import com.amir.alzheimer.R
import kotlinx.android.synthetic.main.image_index_item.view.*
import java.util.*

class FindAdapter(val context: Context, val mode: Int) : BaseAdapter() {
    var items: ArrayList<Int> = arrayListOf()
    var toFind: List<Int> = listOf()
    var found: BooleanArray
    private var inflater: LayoutInflater
    var initiated = false

    init {
        val indexes = (1 until 50).shuffled().slice(1 until HARDNESS * HARDNESS + 1)
        for (i in indexes) {
            items.add(
                    if (mode == IMAGE)
                        context.resources.getIdentifier(
                                "duplicate_${String.format(Locale.US, "%03d", i)}", "drawable",
                                context.packageName)
                    else i
            )
        }
        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        toFind = if (mode == IMAGE) {
            items.slice(0..HARDNESS)
        } else {
            initiated = true
            items.filter { it % 2 == 0 }
        }
        found = BooleanArray(indexes.size)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val gridItem: View = convertView
                ?: inflater.inflate(R.layout.image_index_item, parent, false)

        if (mode == IMAGE) {
            gridItem.index_item_image.setImageResource(items[position])
        } else {
            gridItem.index_text_text.text = items[position].toString()

        }
        if (found[position]) {
            gridItem.index_activity_container.setBackgroundColor(ContextCompat.getColor(context, R.color.green))
        } else {
            gridItem.index_activity_container.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
        }
        gridItem.index_item_index_text.visibility = View.GONE
        return gridItem

    }

    override fun getItem(position: Int): Any {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return if (!initiated) HARDNESS else items.size
    }

    companion object {
        const val HARDNESS = 5
        const val IMAGE = 1
        const val NUMBER = 2
    }

}
