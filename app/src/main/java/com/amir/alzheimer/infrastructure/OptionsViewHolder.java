package com.amir.alzheimer.infrastructure;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amir.alzheimer.R;

class OptionsViewHolder extends RecyclerView.ViewHolder{
    private final ImageView imageView;
    private final TextView textView;

    OptionsViewHolder(View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.item_alzheimer_option_image_view);
        textView = itemView.findViewById(R.id.item_alzheimer_option_text_view);
    }


    void populate(int image, String tag) {
        imageView.setImageResource(image);
        textView.setText(tag);
        itemView.setTag(image);
    }
}
