package com.amir.alzheimer.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.amir.alzheimer.infrastructure.Database;

public abstract class BaseFragment extends Fragment {
    protected AlzheimerApplication application;
    protected Database database;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        application = (AlzheimerApplication) getActivity().getApplication();
        database = application.getDatabase();
    }
}
