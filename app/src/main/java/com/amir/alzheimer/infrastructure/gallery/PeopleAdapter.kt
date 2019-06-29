package com.amir.alzheimer.infrastructure.gallery

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

import com.amir.alzheimer.R
import com.amir.alzheimer.infrastructure.database.AlzhimerDatabase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import io.reactivex.disposables.CompositeDisposable



class PeopleAdapter(context: Context?) : BaseAdapter() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var peoples: List<String> = listOf()
    var compositeDisposable = CompositeDisposable()

    init {
        AlzhimerDatabase.getAlzhimerDao().allRelatives()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { relatives ->
                    peoples = relatives.map { it.title }
                    Log.wtf("Aaaa", "aaaaa")
                    notifyDataSetChanged()
                }.addTo(compositeDisposable)

    }

    override fun getCount(): Int {
        return peoples.size
    }

    override fun getItem(position: Int): String {
        return peoples[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val viewHolder: PeopleViewHolder
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.people_item, parent, false)
            viewHolder = PeopleViewHolder()
            viewHolder.textView = convertView!!.findViewById(R.id.people_item_text_view)
            convertView.tag = viewHolder
        } else {
            viewHolder = convertView.tag as PeopleViewHolder
        }

        viewHolder.textView!!.text = peoples[position]

        return convertView
    }


    internal inner class PeopleViewHolder {
        var textView: TextView? = null
    }
}
