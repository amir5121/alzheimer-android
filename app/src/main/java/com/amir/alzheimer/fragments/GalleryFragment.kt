package com.amir.alzheimer.fragments

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.viewpager.widget.ViewPager
import com.amir.alzheimer.R
import com.amir.alzheimer.base.BaseFragment
import com.amir.alzheimer.infrastructure.database.AlzhimerDatabase
import com.amir.alzheimer.infrastructure.database.relative.Relative
import com.amir.alzheimer.infrastructure.gallery.PeopleAdapter
import com.amir.alzheimer.infrastructure.gallery.ViewPagerAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.slider_fragment.view.*
import java.util.*

class GalleryFragment : BaseFragment() {
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var relatives: List<Relative>


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.slider_fragment, container, false)
        AlzhimerDatabase.getDatabase(null).relativeDao().allRelatives()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { relatives ->
                    this.relatives = relatives
                    val adapter = PeopleAdapter(context, relatives.map { it.title })
                    view.slider_fragment_list_view.adapter = adapter
                }.addTo(application.compositeDisposable)


        viewPagerAdapter = ViewPagerAdapter(this.context!!)
        view.slider_fragment_viewpager.adapter = viewPagerAdapter

        view.slider_fragment_list_view.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            Log.wtf(TAG, relatives[position].imagesDir.split(",").toString())
            viewPagerAdapter.images = relatives[position].imagesDir.split(",")
            viewPagerAdapter.notifyDataSetChanged()
            view.slider_fragment_list_view.visibility = View.GONE
            view.slider_fragment_viewpager.visibility = View.VISIBLE
        }

        view.indicator.setViewPager(view.slider_fragment_viewpager)


        view.slider_fragment_viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                currentPage = position
            }

            override fun onPageScrollStateChanged(i: Int) {

                if (i == ViewPager.SCROLL_STATE_IDLE) {

                    val pageCount = viewPagerAdapter.images.size
                    if (currentPage == 0) {
                        view.slider_fragment_viewpager.setCurrentItem(pageCount, false)
                    }
                }
            }
        })

        val handler = Handler()
        val run = Runnable {
            if (currentPage == viewPagerAdapter.images.size) {
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
        private const val TAG: String = "GalleryFragment"
        private var currentPage = 0
    }

}
