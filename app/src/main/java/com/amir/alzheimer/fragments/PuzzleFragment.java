package com.amir.alzheimer.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amir.alzheimer.base.BaseActivity;
import com.amir.alzheimer.base.BaseFragment;
import com.amir.alzheimer.puzzle.ExampleJigsawConfigurations;
import com.amir.alzheimer.puzzle.JigsawPuzzle;
import com.amir.alzheimer.puzzle.PuzzleCompactSurface;

public class PuzzleFragment extends BaseFragment {
    private PuzzleCompactSurface puzzleSurface;

    private static final String TAG = "PuzzleActivity";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Bundle config = ExampleJigsawConfigurations.getRcatKittenExample();

        puzzleSurface = new PuzzleCompactSurface(getContext());
        JigsawPuzzle jigsawPuzzle = new JigsawPuzzle(getContext(), config);
        puzzleSurface.setPuzzle(jigsawPuzzle);

        return puzzleSurface;
    }

    @Override
    public void onPause() {
        super.onPause();
        puzzleSurface.getThread().pause();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        puzzleSurface.getThread().saveState(outState);
    }
}
