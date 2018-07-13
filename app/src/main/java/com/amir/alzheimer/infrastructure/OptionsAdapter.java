package com.amir.alzheimer.infrastructure;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amir.alzheimer.R;
import com.amir.alzheimer.base.BaseActivity;

public class OptionsAdapter extends RecyclerView.Adapter<OptionsViewHolder> implements View.OnClickListener {

    private static final String TAG = "OptionsAdapter";
    public static final int[] IMAGES = {
            R.mipmap.ic_doc,
            R.mipmap.ic_mind,
            R.mipmap.ic_set,
            R.mipmap.ic_note,
            R.mipmap.ic_med,
            R.mipmap.ic_rem
    };

    private final int itemSize;
    private final AlzheimerItemCallback listener;
    private LayoutInflater inflater;

    public OptionsAdapter(BaseActivity activity, int itemSize, AlzheimerItemCallback listener) {
        inflater = LayoutInflater.from(activity);
        Log.e(TAG, "OptionsAdapter: adapter was created");
        this.itemSize = itemSize;
        this.listener = listener;
    }

    @NonNull
    @Override
    public OptionsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_alzheimer_option, parent, false);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(itemSize, itemSize);
        view.setLayoutParams(params);
        view.setOnClickListener(this);
        return new OptionsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OptionsViewHolder holder, int position) {
        holder.populate(IMAGES[position % IMAGES.length]);
      }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public void onClick(View v) {
        int intID = (v.getTag() == null) ? -1 : (Integer) v.getTag();
        listener.itemClicked(intID);
    }
}
