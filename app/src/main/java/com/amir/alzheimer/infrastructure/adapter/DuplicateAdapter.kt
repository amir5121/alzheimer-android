package com.amir.alzheimer.infrastructure.adapter

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.amir.alzheimer.R
import kotlinx.android.synthetic.main.duplicate_activity_image_vire.view.*
import java.util.*
import kotlin.collections.ArrayList


class DuplicateAdapter(private val context: Context, private val hardness: Int) : BaseAdapter() {
    private var mThumbIds: ArrayList<Int> = arrayListOf()
    private var inflater: LayoutInflater

    init {
        var listOfIndexes: ArrayList<Int> = (1..100).toList().shuffled().slice(1..hardness) as ArrayList<Int>
        listOfIndexes.addAll(listOfIndexes)
        listOfIndexes = listOfIndexes.shuffled() as ArrayList<Int>
        for (i in listOfIndexes) {
            mThumbIds.add(context.resources.getIdentifier("duplicate_${String.format(Locale.US, "%03d", i)}", "drawable", context.packageName))
        }
        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val imageView: ImageView

        if (convertView == null) {
            val girdItem = inflater.inflate(R.layout.duplicate_activity_image_vire, parent, false)
            imageView = girdItem.duplicate_activity_grid_image_item as ImageView
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                imageView.setImageDrawable(context.getDrawable(mThumbIds[position]))
            } else {
                @Suppress("DEPRECATION")
                imageView.setImageDrawable(context.resources.getDrawable(mThumbIds[position]))
            }
        } else {
            imageView = convertView as ImageView
        }
        imageView.setImageResource(mThumbIds[position])
        return imageView
    }

    override fun getItem(position: Int): Any {
        return mThumbIds[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        val itemsCount = hardness * 2
        return if (itemsCount > ALL_IMAGES_COUNT) ALL_IMAGES_COUNT else itemsCount
    }

    companion object {
        private const val TAG = "DuplicateAdapter"
        private const val ALL_IMAGES_COUNT = 100
    }

}
