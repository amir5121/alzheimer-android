package com.amir.alzheimer.infrastructure.gallery

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.viewpager.widget.PagerAdapter

import com.amir.alzheimer.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.simple_imageview.view.*

class ViewPagerAdapter(private val context: Context) : PagerAdapter() {
    var images: List<String> = listOf()

    override fun getCount(): Int {
        return images.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }


    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemView = layoutInflater.inflate(R.layout.simple_imageview, container, false)
        val imageView = itemView.simple_image_view_image_view
        Glide.with(container).load(images[position]).into(imageView)
        container.addView(itemView)

        return itemView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as RelativeLayout)
    }

}
