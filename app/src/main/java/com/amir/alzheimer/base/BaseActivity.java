package com.amir.alzheimer.base;

import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amir.alzheimer.R;

import java.util.Locale;

public abstract class BaseActivity extends AppCompatActivity {
    protected AlzheimerApplication application;
    private Toolbar toolbar;
    protected int displayWidth, displayHeight;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        application = (AlzheimerApplication) getApplication();

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        displayHeight = displayMetrics.heightPixels;
        displayWidth = displayMetrics.widthPixels;

    }

    public void setFont(TextView textView) {
        if (textView != null)
            textView.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/IRANSans_Medium.ttf"));
    }

    public void setFont(ViewGroup group) {
        if (group != null) {
            int count = group.getChildCount();
            View v;
            for (int i = 0; i < count; i++) {
                v = group.getChildAt(i);
                if (v instanceof TextView) {
                    setFont((TextView) v);
                } else if (v instanceof ViewGroup)
                    setFont((ViewGroup) v);
            }
        } else {
            Log.e(getClass().getSimpleName(), "viewGroup was null");
        }
    }


    public void setLocale() {
        Locale locale = new Locale("fa");

        Locale.setDefault(locale);
        Configuration config = getBaseContext().getResources().getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            config.setLocale(locale);
        } else {
            config.locale = locale;
        }

        //todo: ??? need to use updateConfiguration replacement
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
    }
}
