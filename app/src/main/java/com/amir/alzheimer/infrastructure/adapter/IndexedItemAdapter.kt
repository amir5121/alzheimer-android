package com.amir.alzheimer.infrastructure.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.amir.alzheimer.R
import com.amir.alzheimer.infrastructure.Constants
import com.amir.alzheimer.infrastructure.dto.IndexedItem
import kotlinx.android.synthetic.main.image_index_item.view.*
import java.util.*


class IndexedItemAdapter(context: Context, mode: Int) : BaseAdapter() {
    private var duplicateItems: ArrayList<IndexedItem> = arrayListOf()
    private var inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    var initiated = false

    init {
        for (i in 1..HARDNESS) {
            var imageIndex = if (mode == FRUITS)
                (100..999).random()
            else
                (1..10).random()
            while (!duplicateItems.none { it.index == imageIndex }) {
                imageIndex = if (mode == FRUITS)
                    (100..999).random()
                else
                    (1..10).random()
            }
            when (mode) {
                IMAGE -> duplicateItems.add(
                        IndexedItem(
                                context.resources.getIdentifier(
                                        "duplicate_${String.format(Locale.US, "%03d", i)}", "drawable",
                                        context.packageName),
                                imageIndex

                        )
                )
                TEXT -> duplicateItems.add(
                        IndexedItem(
                                Constants.TO_REMEMBER_SOURCE[i],
                                imageIndex

                        )
                )
                NUMBER -> duplicateItems.add(
                        IndexedItem(
                                Constants.TO_REMEMBER_NUMBER[i],
                                imageIndex

                        )
                )
                CHICKS_NAME -> duplicateItems.add(
                        IndexedItem(
                                Constants.CHICKS_NAMES[i],
                                imageIndex

                        )
                )
                FRUITS -> duplicateItems.add(
                        IndexedItem(
                                Constants.FRUITES[i],
                                imageIndex

                        )
                )
            }
        }

    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val gridItem: View = convertView
                ?: inflater.inflate(R.layout.image_index_item, parent, false)


        val duplicateItem = duplicateItems[position]
        gridItem.index_item_image.setImageResource(duplicateItem.image)
        gridItem.index_text_text.text = duplicateItem.text
        gridItem.index_item_index_text.text = duplicateItem.index.toString()
        gridItem.index_item_index_text.visibility = duplicateItem.visibility
        return gridItem
    }

    override fun getItem(position: Int): Any {
        return duplicateItems[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return duplicateItems.size
    }

    fun initiate() {
        initiated = true
        val duplicateItems: ArrayList<IndexedItem> = arrayListOf()
        for (i in 0 until HARDNESS * 3) {
            this.duplicateItems.random().let {
                it.visibility = View.GONE
                duplicateItems.add(it)
            }
        }
        this.duplicateItems = duplicateItems
        notifyDataSetChanged()
    }

    fun sumOfIndexes(): Int {
        return duplicateItems.sumBy { it.index }
    }

    companion object {
        private const val TAG = "DuplicateAdapter"
        private const val HARDNESS = 4
        const val IMAGE = 2
        const val TEXT = 1
        const val NUMBER = 4
        const val CHICKS_NAME = 5
        const val FRUITS = 7

    }

}
