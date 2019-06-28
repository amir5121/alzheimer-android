package com.amir.alzheimer.infrastructure.gallery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.amir.alzheimer.R;

public class ViewPagerAdapter extends PagerAdapter {

    public int[] ImgR = {R.drawable.c, R.drawable.d};

    private Context cnt;

    public ViewPagerAdapter(Context cnt) {
        this.cnt = cnt;
    }

    @Override
    public int getCount() {
        return ImgR.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view == object);
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater LI = (LayoutInflater) cnt.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view = LI.inflate(R.layout.simple_imageview, container, false);
        ImageView imgv = item_view.findViewById(R.id.simple_image_view_image_view);
        imgv.setImageResource(ImgR[position]);
        container.addView(item_view);

        return item_view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout) object);
    }

}
