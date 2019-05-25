package com.amir.alzheimer.fragments

import android.os.Bundle
import android.os.Handler
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView

import com.amir.alzheimer.R
import com.amir.alzheimer.base.BaseFragment
import com.amir.alzheimer.infrastructure.gallery.PeopleAdapter
import com.amir.alzheimer.infrastructure.gallery.ViewPagerAdapter

import java.util.Timer
import java.util.TimerTask

import me.relex.circleindicator.CircleIndicator

class GalleryFragment : BaseFragment() {
    private var viewPager: ViewPager? = null
    private var viewPagerAdapter: ViewPagerAdapter? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.slider_fragment, container, false)
        viewPager = view.findViewById(R.id.slider_fragment_viewpager)
        val adapter = PeopleAdapter(context, database.allContacts)

        val listView = view.findViewById<ListView>(R.id.slider_fragment_list_view)
        listView.adapter = adapter

        val indicator = view.findViewById<CircleIndicator>(R.id.indicator)

        listView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            listView.visibility = View.GONE
            viewPager!!.visibility = View.VISIBLE
        }

        viewPagerAdapter = ViewPagerAdapter(activity)
        viewPager!!.adapter = viewPagerAdapter

        indicator.setViewPager(viewPager)


        viewPager!!.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                currentPage = position
            }

            override fun onPageScrollStateChanged(i: Int) {

                if (i == ViewPager.SCROLL_STATE_IDLE) {

                    val pageCount = viewPagerAdapter!!.ImgR.size
                    if (currentPage == 0) {
                        viewPager!!.setCurrentItem(pageCount, false)
                    }
                }
            }
        })

        val handler = Handler()
        val run = Runnable {
            if (currentPage == viewPagerAdapter!!.ImgR.size) {
                viewPager!!.setCurrentItem(0, false)
            }
            viewPager!!.setCurrentItem(currentPage++, true)
        }

        val swapper = Timer()
        swapper.schedule(object : TimerTask() {
            override fun run() {
                handler.post(run)
            }
        }, 3000, 3000)

        return view
    }

    companion object {

        private var currentPage = 0
    }

}
