package com.amir.alzheimer.infrastructure.gallery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.amir.alzheimer.R;

import java.util.ArrayList;

public class PeopleAdapter extends BaseAdapter {
    private final ArrayList<String> peoples;
    private final LayoutInflater inflater;

    public PeopleAdapter(Context context, ArrayList<String> peoples) {
        this.inflater = LayoutInflater.from(context);
        this.peoples = peoples;

        peoples.add("Amir");
        peoples.add("Mehdi");
        peoples.add("Mohsen");
        peoples.add("Tannaz");
        peoples.add("Zeynab");

    }


    @Override
    public int getCount() {
        return peoples.size();
    }

    @Override
    public String getItem(int position) {
        return peoples.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PeopleViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.people_item, parent, false);
            viewHolder = new PeopleViewHolder();
            viewHolder.textView = (TextView) convertView.findViewById(R.id.people_item_text_view);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (PeopleViewHolder) convertView.getTag();
        }

        viewHolder.textView.setText(peoples.get(position));

        return convertView;
    }


    class PeopleViewHolder {
        TextView textView;
    }
}
