package com.amir.alzheimer.activites

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View

import com.amir.alzheimer.R
import com.amir.alzheimer.base.BaseActivity
import com.amir.alzheimer.fragments.GalleryFragment
import com.amir.alzheimer.fragments.GameFragment
import com.amir.alzheimer.fragments.SettingsFragment
import com.amir.alzheimer.infrastructure.AlzheimerItemCallback
import com.amir.alzheimer.infrastructure.OptionsAdapter
import com.amir.alzheimer.infrastructure.Utils
import com.tangxiaolv.telegramgallery.GalleryActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), AlzheimerItemCallback, View.OnClickListener {

    //TODO: https://github.com/VinkasHQ/reminders-android
    //TODO: or maybe this one is better https://github.com/ataradov/alarm-me thou older

    //TODO: the puzzle comes from: https://github.com/worldsproject/android-jigsaw-puzzle-library

    private var width = 0
    //    private ImageView mainImageView;
    private lateinit var topScrollListener: RecyclerView.OnScrollListener
    private lateinit var bottomScrollListener: RecyclerView.OnScrollListener
    private var listHeight: Int = 0
    private var animateExpanding: Boolean = false
    private var lastFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        activity_main_recycler_view_top.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        activity_main_recycler_view_bottom.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)


        activity_main_expand.setOnClickListener(this)


        topScrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    activity_main_recycler_view_bottom.addOnScrollListener(bottomScrollListener)
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dx != 0) {
                    activity_main_recycler_view_bottom.removeOnScrollListener(bottomScrollListener)
                    activity_main_recycler_view_bottom.scrollBy(dx, dy)
                }
            }
        }

        bottomScrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    activity_main_recycler_view_top.addOnScrollListener(topScrollListener)
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dx != 0) {
                    activity_main_recycler_view_top.removeOnScrollListener(topScrollListener)
                    activity_main_recycler_view_top.scrollBy(dx, dy)
                }
            }
        }

        this.itemClicked(R.mipmap.ic_mind)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)

        if (width == 0) {
            width = activity_main_fragment_container.width

            if (width != 0) {

                val adapter = OptionsAdapter(this, width / 3, this)

                activity_main_recycler_view_bottom.adapter = adapter

                activity_main_recycler_view_top.adapter = adapter

                activity_main_recycler_view_top.scrollToPosition(Integer.MAX_VALUE / 2)
                activity_main_recycler_view_bottom.scrollToPosition(Integer.MAX_VALUE / 2 + OptionsAdapter.IMAGES.size / 2)

                activity_main_recycler_view_bottom.addOnScrollListener(bottomScrollListener)
                activity_main_recycler_view_top.addOnScrollListener(topScrollListener)

                //                recyclerViewBottom.bringToFront();
                //                recyclerViewTop.bringToFront();

            }
        }

        if (listHeight == 0) {
            listHeight = activity_main_recycler_view_top.height
        }
    }

    override fun itemClicked(item: Int) {

        lastFragment?.let { supportFragmentManager.beginTransaction().remove(it).commitAllowingStateLoss() }

        when (item) {

            R.mipmap.ic_doc -> {
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "09178947604"))
                startActivity(intent)
            }
            R.mipmap.ic_mind -> {
                animateExpanding = false
                lastFragment = GameFragment()
            }
            R.mipmap.ic_set -> {
                animateExpanding = true
                lastFragment = SettingsFragment()
            }
            R.mipmap.ic_note -> {
                animateExpanding = true
                lastFragment = GalleryFragment()
            }
            R.mipmap.ic_med -> {
            }
            R.mipmap.ic_rem -> {
            }
        }

        lastFragment?.let { updateFragment(it) }
    }

    private fun updateFragment(lastFragment: Fragment) {
        supportFragmentManager
                .beginTransaction()
                .add(R.id.activity_main_fragment_container, lastFragment, PUZZLE_FRAGMENT)
                .commit()
    }

    override fun onClick(v: View) {
        val itemId = v.id
        if (itemId == R.id.activity_main_expand) {
            val expanded = activity_main_recycler_view_top.visibility == View.VISIBLE

            updateMainViewSize(expanded)

        }
    }

    override fun onBackPressed() {
        val expanded = activity_main_recycler_view_top.visibility != View.VISIBLE

        if (expanded) {
            updateMainViewSize(false)
        } else {
            super.onBackPressed()
        }

    }

    private fun updateMainViewSize(expanded: Boolean) {
        if (animateExpanding)
            if (expanded) {
                Utils.collapse(activity_main_recycler_view_top, activity_main_expand, null)
                Utils.collapse(activity_main_recycler_view_bottom, activity_main_expand, null)
            } else {
                Utils.expand(activity_main_recycler_view_top, activity_main_expand, listHeight)
                Utils.expand(activity_main_recycler_view_bottom, activity_main_expand, listHeight)
            }
        else {
            if (expanded) {
                activity_main_recycler_view_top.visibility = View.GONE
                activity_main_recycler_view_bottom.visibility = View.GONE
            } else {
                activity_main_recycler_view_top.visibility = View.VISIBLE
                activity_main_recycler_view_bottom.visibility = View.VISIBLE
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        //list of photos of selected
        if (data != null) {
            val photos = data.getSerializableExtra(GalleryActivity.PHOTOS) as List<*>

            for (dir in photos) {
                Log.e(TAG, "onActivityResult: $dir")
            }

        }

        (lastFragment as SettingsFragment).relativesWasAdded()
        //list of videos of selected
        //        List<String> videos = (List<String>) data.getSerializableExtra(GalleryActivity.VIDEO);
    }

    companion object {

        private const val TAG = "MainActivity"
        private const val PUZZLE_FRAGMENT = "PUZZLE_FRAGMENT"
    }
}
