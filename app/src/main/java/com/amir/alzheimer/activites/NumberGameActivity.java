package com.amir.alzheimer.activites;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amir.alzheimer.R;
import com.amir.alzheimer.base.BaseActivity;
import com.amir.alzheimer.infrastructure.Algorithm;

import java.util.Arrays;

public class NumberGameActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "NumberGameActivity";
    private int[] numbers = {1, 3, 4, 5, 6, 7, 9, 10, 11};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_game);
        GridLayout gridLayout = findViewById(R.id.activity_number_game_grid);
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            TextView textView = (TextView) gridLayout.getChildAt(i);
            textView.setText(String.valueOf(numbers[i]));
        }

        findViewById(R.id.activity_number_game_continue_button).setOnClickListener(this);
        findViewById(R.id.activity_number_game_continue_done).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.activity_number_game_continue_done) {

            LinearLayout resultContainer = findViewById(R.id.activity_number_game_result_container);
            int hit = 0;
            for (int i = 0; i < resultContainer.getChildCount(); i++) {
                TextView textView = (TextView) resultContainer.getChildAt(i);
                textView.setTextColor(getResources().getColor(R.color.white));
                int val = Integer.parseInt(textView.getText().toString());
                if (!hasValue(numbers, val)) {
                    textView.setBackgroundColor(getResources().getColor(R.color.green));
                    hit++;
                } else {
                    textView.setBackgroundColor(getResources().getColor(R.color.red));

                }
            }

            Algorithm.NumberGameHitCount(hit, resultContainer.getChildCount() - hit);
        } else if (id == R.id.activity_number_game_continue_button) {
            findViewById(R.id.activity_number_game_first_layout).setVisibility(View.GONE);
            findViewById(R.id.activity_number_game_second_layout).setVisibility(View.VISIBLE);
        }
    }

    private boolean hasValue(int[] numbers, int val) {
        for (int i : numbers) {
            if (val == i) return true;

        }
        return false;
    }
}
