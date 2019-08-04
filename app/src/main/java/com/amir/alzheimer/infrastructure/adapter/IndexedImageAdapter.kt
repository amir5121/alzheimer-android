package com.amir.alzheimer.infrastructure.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.amir.alzheimer.R
import com.amir.alzheimer.infrastructure.dto.IndexedImageItem
import kotlinx.android.synthetic.main.image_index_item.view.*
import java.util.*
import kotlin.collections.ArrayList


class IndexedImageAdapter(context: Context) : BaseAdapter() {
    private var duplicateItems: ArrayList<IndexedImageItem> = arrayListOf()
    private var inflater: LayoutInflater
    var initiated = false

    init {
        for (i in 1..HARDNESS) {
            var imageIndex = (1..10).random()
            while (!duplicateItems.none { it.index == imageIndex }) {
                imageIndex = (1..10).random()
            }
            duplicateItems.add(
                    IndexedImageItem(
                            context.resources.getIdentifier(
                                    "duplicate_${String.format(Locale.US, "%03d", i)}", "drawable",
                                    context.packageName),
                            View.VISIBLE,
                            imageIndex

                    )
            )
        }
        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val gridItem: View = convertView
                ?: inflater.inflate(R.layout.image_index_item, parent, false)


        val duplicateItem = duplicateItems[position]
        gridItem.image_index_item_image.setImageResource(duplicateItem.image)
        gridItem.image_index_item_text.text = duplicateItem.index.toString()
        gridItem.image_index_item_text.visibility = duplicateItem.visibility
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
        val duplicateItems: ArrayList<IndexedImageItem> = arrayListOf()
        for (i in 0 until HARDNESS ) {
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
        private const val ALL_IMAGES_COUNT = 50

    }

}
