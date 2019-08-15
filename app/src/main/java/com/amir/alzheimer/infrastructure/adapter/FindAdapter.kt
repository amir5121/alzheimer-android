package com.amir.alzheimer.infrastructure.adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.core.content.ContextCompat
import com.amir.alzheimer.R
import kotlinx.android.synthetic.main.image_index_item.view.*
import java.util.*
import kotlin.collections.ArrayList

class FindAdapter(val context: Context) : BaseAdapter() {
    var images: ArrayList<Int> = arrayListOf()
    var toFind: List<Int> = listOf()
    var found: BooleanArray
    private var inflater: LayoutInflater
    var initiated = false

    init {
        val indexes = (1 until 50).shuffled().slice(1 until HARDNESS * HARDNESS + 1)
        for (i in indexes) {
            images.add(
                    context.resources.getIdentifier(
                            "duplicate_${String.format(Locale.US, "%03d", i)}", "drawable",
                            context.packageName)
            )
        }
        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        toFind = images.slice(0..HARDNESS)
        found = BooleanArray(indexes.size)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val gridItem: View = convertView
                ?: inflater.inflate(R.layout.image_index_item, parent, false)

        gridItem.index_item_image.setImageResource(images[position])
        if (found[position]) {
            gridItem.index_activity_container.setBackgroundColor(ContextCompat.getColor(context, R.color.green))
        } else {
            gridItem.index_activity_container.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
        }
        gridItem.index_item_index_text.visibility = View.GONE
        return gridItem

    }

    override fun getItem(position: Int): Any {
        return images[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return if (!initiated) HARDNESS else images.size
    }

    companion object {
        const val HARDNESS = 5
    }

}
