package com.amir.alzheimer.infrastructure

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView

import com.amir.alzheimer.R
import kotlinx.android.synthetic.main.item_alzheimer_option.view.*
import kotlinx.android.synthetic.main.main_activity_list_tag_item.view.*

class OptionsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val imageView: ImageView = itemView.item_alzheimer_option_image_view
    private val textView: TextView = itemView.item_alzheimer_option_text_view


    fun populate(image: Int, tag: String) {
        imageView.setImageResource(image)
        textView.text = tag
        itemView.tag = image
    }
}
