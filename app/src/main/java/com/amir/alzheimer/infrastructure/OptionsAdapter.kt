package com.amir.alzheimer.infrastructure

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.amir.alzheimer.R
import com.amir.alzheimer.base.BaseActivity

class OptionsAdapter(activity: BaseActivity, private val itemSize: Int, private val listener: AlzheimerItemCallback) : RecyclerView.Adapter<OptionsViewHolder>(), View.OnClickListener {
    private val tags: Array<String> = arrayOf(activity.getString(R.string.doctor),
            activity.getString(R.string.brain),
            activity.getString(R.string.settings),
            activity.getString(R.string.relatives),
            activity.getString(R.string.medicines),
            activity.getString(R.string.reminders)
    )

    private val inflater: LayoutInflater = LayoutInflater.from(activity)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OptionsViewHolder {
        val view = inflater.inflate(R.layout.item_alzheimer_option, parent, false)
        val params = ViewGroup.LayoutParams(itemSize, itemSize)
        view.layoutParams = params
        view.setOnClickListener(this)
        return OptionsViewHolder(view)
    }

    override fun onBindViewHolder(holder: OptionsViewHolder, position: Int) {
        holder.populate(IMAGES[position % IMAGES.size], tags[position % IMAGES.size])
    }

    override fun getItemCount(): Int {
        return Integer.MAX_VALUE
    }

    override fun onClick(v: View) {
        val intID = if (v.tag == null) -1 else v.tag as Int
        listener.itemClicked(intID)
    }

    companion object {

        private val TAG = "OptionsAdapter"
        val IMAGES = intArrayOf(R.mipmap.ic_doc, R.mipmap.ic_mind, R.mipmap.ic_set, R.mipmap.ic_note, R.mipmap.ic_med, R.mipmap.ic_rem)
    }
}
