package com.amir.alzheimer.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;

import com.amir.alzheimer.R;
import com.amir.alzheimer.activites.games.NumberGameActivity;
import com.amir.alzheimer.activites.games.OneToFifty;
import com.amir.alzheimer.activites.games.SquareMatchActivity;
import com.amir.alzheimer.androidpuzzlegame.MainActivityPuzzle;
import com.amir.alzheimer.base.BaseFragment;

import java.util.Arrays;

public class GameFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.game_fragment, container, false);
        GridView listView = view.findViewById(R.id.game_fragment_list_view);
        Context context = getContext();
        if (context != null) {
            ListAdapter adapter = new ArrayAdapter<>(
                    context,
                    R.layout.simple_list_item,
                    Arrays.asList(
                            context.getString(R.string.puzzle),
                            context.getString(R.string.missing_number),
                            context.getString(R.string.match),
                            context.getString(R.string.one_to_fifthy)
                    ));
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(this);
        }

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            switch (i) {
                case 0:
                    activity.startActivity(new Intent(getContext(), MainActivityPuzzle.class));
                    break;
                case 1:
                    activity.startActivity(new Intent(getContext(), NumberGameActivity.class));
                    break;
                case 2:
                    activity.startActivity(new Intent(getContext(), SquareMatchActivity.class));
                    break;
                case 3:
                    activity.startActivity(new Intent(getContext(), OneToFifty.class));
                    break;
            }
        }
    }
}
