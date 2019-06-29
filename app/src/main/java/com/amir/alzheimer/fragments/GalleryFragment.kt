package com.amir.alzheimer.fragments

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.viewpager.widget.ViewPager
import com.amir.alzheimer.R
import com.amir.alzheimer.base.BaseFragment
import com.amir.alzheimer.infrastructure.gallery.PeopleAdapter
import com.amir.alzheimer.infrastructure.gallery.ViewPagerAdapter
import kotlinx.android.synthetic.main.slider_fragment.view.*
import java.util.*

class GalleryFragment : BaseFragment() {
    private lateinit var viewPagerAdapter: ViewPagerAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.slider_fragment, container, false)
        val adapter = PeopleAdapter(context)
        view.slider_fragment_list_view.adapter = adapter
        viewPagerAdapter = ViewPagerAdapter(activity)


//        val indicator = view.findViewById<CircleIndicator>(R.id.indicator)

        view.slider_fragment_list_view.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            view.slider_fragment_list_view.visibility = View.GONE
            view.slider_fragment_viewpager.visibility = View.VISIBLE
        }

        view.slider_fragment_viewpager.adapter = viewPagerAdapter

        view.indicator.setViewPager(view.slider_fragment_viewpager)


        view.slider_fragment_viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                currentPage = position
            }

            override fun onPageScrollStateChanged(i: Int) {

                if (i == ViewPager.SCROLL_STATE_IDLE) {

                    val pageCount = viewPagerAdapter.ImgR.size
                    if (currentPage == 0) {
                        view.slider_fragment_viewpager.setCurrentItem(pageCount, false)
                    }
                }
            }
        })

        val handler = Handler()
        val run = Runnable {
            if (currentPage == viewPagerAdapter.ImgR.size) {
                view.slider_fragment_viewpager.setCurrentItem(0, false)
            }
            view.slider_fragment_viewpager.setCurrentItem(currentPage++, true)
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
