package com.amir.alzheimer.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.amir.alzheimer.R;
import com.amir.alzheimer.base.BaseFragment;
import com.amir.alzheimer.infrastructure.gallery.PeopleAdapter;
import com.amir.alzheimer.infrastructure.gallery.ViewPagerAdapter;

import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class GalleryFragment extends BaseFragment {

    private static int currentPage = 0;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.slider_fragment, container, false);
        viewPager = (ViewPager) view.findViewById(R.id.slider_fragment_viewpager);
        PeopleAdapter adapter = new PeopleAdapter(getContext(), database.getAllContacts());

        final ListView listView = (ListView) view.findViewById(R.id.slider_fragment_list_view);
        listView.setAdapter(adapter);

        CircleIndicator indicator = (CircleIndicator) view.findViewById(R.id.indicator);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listView.setVisibility(View.GONE);
                viewPager.setVisibility(View.VISIBLE);
            }
        });

        viewPagerAdapter = new ViewPagerAdapter(getActivity());
        viewPager.setAdapter(viewPagerAdapter);

        indicator.setViewPager(viewPager);


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPage = position;
            }

            @Override
            public void onPageScrollStateChanged(int i) {

                if (i == ViewPager.SCROLL_STATE_IDLE) {

                    int pageCount = viewPagerAdapter.ImgR.length;
                    if (currentPage == 0) {
                        viewPager.setCurrentItem(pageCount, false);
                    }
                }
            }
        });

        final Handler handler = new Handler();
        final Runnable run = new Runnable() {
            @Override
            public void run() {

                if (currentPage == viewPagerAdapter.ImgR.length) {
                    viewPager.setCurrentItem(0, false);
                }
                viewPager.setCurrentItem(currentPage++, true);

            }
        };

        Timer swapper = new Timer();
        swapper.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(run);
            }
        }, 3000, 3000);

        return view;
    }

}
