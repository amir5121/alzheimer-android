package com.amir.alzheimer.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.amir.alzheimer.R;
import com.amir.alzheimer.activites.MainActivity;
import com.amir.alzheimer.activites.NumberGameActivity;
import com.amir.alzheimer.activites.SquareMatchActivity;
import com.amir.alzheimer.base.BaseFragment;

public class GameFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    private String[] games = {"Puzzle", "Missing Number", "Match"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.game_fragment, container, false);
        ListView listView = view.findViewById(R.id.game_fragment_list_view);
        ListAdapter adapter = new ArrayAdapter<>(getContext(), R.layout.simple_list_item, games);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (i) {
            case 0:
                ((MainActivity) getActivity()).updateFragment(new PuzzleFragment());
                break;
            case 1:
                getActivity().startActivity(new Intent(getContext(), NumberGameActivity.class));
                break;
            case 2:
                getActivity().startActivity(new Intent(getContext(), SquareMatchActivity.class));
                break;
        }
    }
}
