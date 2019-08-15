package com.amir.alzheimer.infrastructure.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.amir.alzheimer.R
import com.amir.alzheimer.infrastructure.Constants
import com.amir.alzheimer.infrastructure.dto.DuplicateItem
import kotlinx.android.synthetic.main.duplicate_activity_image_vire.view.*
import java.util.*
import kotlin.collections.ArrayList


class DuplicateAdapter(context: Context, private val hardness: Int, val mode: Int) : BaseAdapter() {
    private var duplicateItems: ArrayList<DuplicateItem> = arrayListOf()
    private var inflater: LayoutInflater

    init {
        var initialArray = (1..ALL_IMAGES_COUNT).toList() as List<*>
        if (mode == TEXT)
            initialArray = listOf(
                    "پ", "ت", "ث", "ج", "چ", "ح", "خ", "د", "ذ", "ر", "ز", "ژ", "س", "ش", "ص", "ض", "ط", "ظ", "ع", "غ", "ف", "ق", "ک", "گ", "ل", "م", "ن", "و", "ه", "ی")
        if (mode == NUMBER)
            initialArray = Constants.TO_REMEMBER_NUMBER

        var listOfIndexes = initialArray.shuffled().slice(1..hardness)

        listOfIndexes = (listOfIndexes + listOfIndexes).shuffled()
        var firstIndex: Int
        var secondIndex: Int
        for (i in initialArray) {
            firstIndex = listOfIndexes.indexOf(i)
            secondIndex = listOfIndexes.lastIndexOf(i)
            if (firstIndex != -1 && secondIndex != -1) {
                duplicateItems.add(
                        if (mode == IMAGE)
                            DuplicateItem(
                                    context.resources.getIdentifier(
                                            "duplicate_${String.format(Locale.US, "%03d", i)}", "drawable",
                                            context.packageName)
                                    ,
                                    arrayOf(firstIndex, secondIndex)

                            )
                        else
                            DuplicateItem(i as String, arrayOf(firstIndex, secondIndex)

                            )
                )
            }
        }
        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val gridItem: View = convertView
                ?: inflater.inflate(R.layout.duplicate_activity_image_vire, parent, false)


        val duplicateItem = getItemByIndex(position)
        if (mode == IMAGE)
            gridItem.duplicate_activity_grid_image_item.setImageResource(duplicateItem.first.image)
        else
            gridItem.duplicate_activity_grid_text_item.text = duplicateItem.first.text
        val coverVisibility = duplicateItem.first.visibility[duplicateItem.second]
        gridItem.duplicate_activity_grid_image_item.visibility = if (coverVisibility == View.GONE) View.VISIBLE else View.GONE
        gridItem.duplicate_activity_grid_text_item.visibility = if (coverVisibility == View.GONE) View.VISIBLE else View.GONE
        gridItem.duplicate_activity_grid_image_cover.visibility = coverVisibility
        return gridItem
    }

    override fun getItem(position: Int): Any {
        return duplicateItems[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        val itemsCount = hardness * 2
        return if (itemsCount > ALL_IMAGES_COUNT) ALL_IMAGES_COUNT else itemsCount
    }

    fun setCoverVisibility(visibility: Int) {
        for (duplicateItem in duplicateItems) {
            duplicateItem.visibility = arrayOf(visibility, visibility)
        }
        notifyDataSetChanged()
    }

    fun getItemByIndex(index: Int): Pair<DuplicateItem, Int> {
        for (item in duplicateItems) {
            val indexOf = item.index.indexOf(index)
            if (indexOf != -1)
                return Pair(item, indexOf)
        }
        return Pair(duplicateItems[0], 0)
    }

    companion object {
        private const val TAG = "DuplicateAdapter"
        private const val ALL_IMAGES_COUNT = 50
        const val TEXT = 0
        const val IMAGE = 1
        const val NUMBER = 3
    }

}
