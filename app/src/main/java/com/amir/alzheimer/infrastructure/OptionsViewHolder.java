package com.amir.alzheimer.infrastructure;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.amir.alzheimer.R;

class OptionsViewHolder extends RecyclerView.ViewHolder{
    private final ImageView imageView;

    OptionsViewHolder(View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.item_alzheimer_option_image_view);
    }


    void populate(int image) {
        imageView.setImageResource(image);
        itemView.setTag(image);
    }
}
