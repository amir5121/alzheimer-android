package com.amir.alzheimer.activites;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.amir.alzheimer.R;
import com.amir.alzheimer.base.BaseActivity;
import com.amir.alzheimer.base.BaseFragment;
import com.amir.alzheimer.fragments.GalleryFragment;
import com.amir.alzheimer.fragments.GameFragment;
import com.amir.alzheimer.fragments.PuzzleFragment;
import com.amir.alzheimer.fragments.SettingsFragment;
import com.amir.alzheimer.infrastructure.AlzheimerItemCallback;
import com.amir.alzheimer.infrastructure.OptionsAdapter;
import com.amir.alzheimer.infrastructure.Utils;
import com.tangxiaolv.telegramgallery.GalleryActivity;

import java.util.List;

public class MainActivity extends BaseActivity implements AlzheimerItemCallback, View.OnClickListener {

    private static final String TAG = "MainActivity";
    private static final String PUZZLE_FRAGMENT = "PUZZLE_FRAGMENT";

    //TODO: https://github.com/VinkasHQ/reminders-android
    //TODO: or maybe this one is better https://github.com/ataradov/alarm-me thou older

    //TODO: the puzzle comes from: https://github.com/worldsproject/android-jigsaw-puzzle-library

    private int width = 0;
    //    private ImageView mainImageView;
    private RecyclerView recyclerViewTop;
    private RecyclerView recyclerViewBottom;
    private RecyclerView.OnScrollListener topScrollListener;
    private RecyclerView.OnScrollListener bottomScrollListener;
    private View expandButton;
    private int listHeight;
    private boolean animateExpanding;
    private Fragment lastFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewTop = (RecyclerView) findViewById(R.id.activity_main_recycler_view_top);
        recyclerViewBottom = (RecyclerView) findViewById(R.id.activity_main_recycler_view_bottom);

        recyclerViewTop.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewBottom.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        expandButton = findViewById(R.id.activity_main_expand);
        expandButton.setOnClickListener(this);

//        mainImageView = (ImageView) findViewById(R.id.activity_main_main_image_view);

        topScrollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (bottomScrollListener != null) {
                        recyclerViewBottom.addOnScrollListener(bottomScrollListener);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dx != 0) {
                    recyclerViewBottom.removeOnScrollListener(bottomScrollListener);
                    recyclerViewBottom.scrollBy(dx, dy);
                }
            }
        };

        bottomScrollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (topScrollListener != null) {
                        recyclerViewTop.addOnScrollListener(topScrollListener);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dx != 0) {
                    recyclerViewTop.removeOnScrollListener(topScrollListener);
                    recyclerViewTop.scrollBy(dx, dy);
                }
            }
        };

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if (width == 0) {
            width = findViewById(R.id.activity_main_fragment_container).getWidth();

            if (width != 0) {
                OptionsAdapter adapter = new OptionsAdapter(this, width / 3, this);

                recyclerViewBottom.setAdapter(adapter);

                recyclerViewTop.setAdapter(adapter);

                recyclerViewTop.scrollToPosition(Integer.MAX_VALUE / 2);
                recyclerViewBottom.scrollToPosition(Integer.MAX_VALUE / 2 + OptionsAdapter.IMAGES.length / 2);

                recyclerViewBottom.addOnScrollListener(bottomScrollListener);
                recyclerViewTop.addOnScrollListener(topScrollListener);

            }
        }

        if (listHeight == 0) {
            listHeight = recyclerViewTop.getHeight();
        }
    }

    @Override
    public void itemClicked(final int item) {

        if (lastFragment != null)
            getSupportFragmentManager().beginTransaction().remove(lastFragment).commitAllowingStateLoss();

        switch (item) {

            case R.drawable.ic_audiotrack:
                break;
            case R.drawable.ic_directions:
                animateExpanding = false;
                lastFragment = new GameFragment();
//                getSupportFragmentManager()
//                        .beginTransaction()
//                        .add(R.id.activity_main_fragment_container, lastFragment, PUZZLE_FRAGMENT)
//                        .commit();
                break;
            case R.drawable.ic_edit:
                animateExpanding = true;
                lastFragment = new SettingsFragment();

                break;
            case R.drawable.ic_face:
                animateExpanding = true;
                lastFragment = new GalleryFragment();

                break;
            case R.drawable.ic_filter:
                break;
            case R.drawable.ic_flash:
                break;
        }

        if (lastFragment != null) {
            updateFragment(lastFragment);
        }
    }

    public void updateFragment(Fragment lastFragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.activity_main_fragment_container, lastFragment, PUZZLE_FRAGMENT)
                .commit();
    }

    @Override
    public void onClick(View v) {
        int itemId = v.getId();
        if (itemId == R.id.activity_main_expand) {
            boolean expanded = recyclerViewTop.getVisibility() == View.VISIBLE;

            if (animateExpanding)
                if (expanded) {
                    Utils.collapse(recyclerViewTop, expandButton, null);
                    Utils.collapse(recyclerViewBottom, expandButton, null);
                } else {
                    Utils.expand(recyclerViewTop, expandButton, listHeight);
                    Utils.expand(recyclerViewBottom, expandButton, listHeight);
                }
            else {
                if (expanded) {
                    recyclerViewTop.setVisibility(View.GONE);
                    recyclerViewBottom.setVisibility(View.GONE);
                } else {
                    recyclerViewTop.setVisibility(View.VISIBLE);
                    recyclerViewBottom.setVisibility(View.VISIBLE);
                }
            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //list of photos of selected
        if (data != null) {
            List<String> photos = (List<String>) data.getSerializableExtra(GalleryActivity.PHOTOS);

            for (String dir :
                    photos) {
                Log.e(TAG, "onActivityResult: " + dir);
            }

        }

        ((SettingsFragment) lastFragment).relativesWasAdded();
        //list of videos of seleced
//        List<String> vides = (List<String>) data.getSerializableExtra(GalleryActivity.VIDEO);
    }
}
