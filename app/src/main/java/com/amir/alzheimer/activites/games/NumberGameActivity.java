package com.amir.alzheimer.activites.games;

import android.os.Bundle;
import android.support.v7.widget.GridLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.amir.alzheimer.R;
import com.amir.alzheimer.base.BaseActivity;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class NumberGameActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "NumberGameActivity";
    int[] numbers, toFind;
    private GridLayout gridLayout;
    private boolean solving = false;
    private boolean updated = false;
    private int score = 0, hitCount = 0;
    private TextView scoreText;
    private Button actionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_game);
        gridLayout = findViewById(R.id.activity_number_game_grid);
        updateNumber(3);

        actionButton = findViewById(R.id.activity_number_game_continue_button);
        actionButton.setOnClickListener(this);
        scoreText = findViewById(R.id.activity_number_game_score);
    }

    void updateNumber(int size) {
        int[] numbers = new int[size * size + size];
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = i + 1;
        }
//        Collections.shuffle(Arrays.asList(numbers));
        shuffleArray(numbers);
        this.numbers = Arrays.copyOfRange(numbers, 0, size * size);
        toFind = Arrays.copyOfRange(numbers, size * size, numbers.length);
//        return this.numbers;

    }

    static void shuffleArray(int[] ar) {
        Random rnd = new Random();
        for (int i = ar.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            int a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Log.wtf(TAG, "update_grid: " + gridLayout.getWidth());
        if (gridLayout.getWidth() != 0 && !updated) {
            update_grid();
            updated = true;
        }

    }


    private void update_grid() {
        gridLayout.removeAllViews();
        gridLayout.setColumnCount((int) Math.sqrt(numbers.length));

        for (int i = 0; i < numbers.length; i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.number_game_item, gridLayout, false);
            view.getLayoutParams().width = gridLayout.getWidth() / ((int) Math.sqrt(numbers.length));
            view.setOnClickListener(this);
            view.getLayoutParams().height = gridLayout.getHeight() / ((int) Math.sqrt(numbers.length) + (solving ? 1 : 0));
            gridLayout.addView(view);
        }

        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            TextView textView = (TextView) gridLayout.getChildAt(i);
            textView.setText(String.valueOf(numbers[i]));
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.activity_number_game_continue_button) {
            if (solving) {
                if (hitCount >= toFind.length) {
                    solving = false;
                    updateNumber(4);
                    ((TextView) findViewById(R.id.activity_number_game_hint)).setText("Find the missing number, memorise them and hit solve! Numbers are from 1 to 20");
                    update_grid();
                    hitCount = 0;
                    actionButton.setText("Solve!");
                } else {
                    Toast.makeText(application, "There are " + Math.abs(hitCount - toFind.length) + " remaining!", Toast.LENGTH_SHORT).show();
                }
            } else {
                int[] array1and2 = new int[numbers.length + toFind.length];
                System.arraycopy(numbers, 0, array1and2, 0, numbers.length);
                System.arraycopy(toFind, 0, array1and2, numbers.length, toFind.length);
                numbers = array1and2;
                shuffleArray(numbers);
                solving = true;
                gridLayout.setBackgroundColor(getResources().getColor(R.color.solving_color));
                update_grid();
            }
        } else {
            if (view instanceof TextView) {
                if (solving) {
                    TextView view1 = (TextView) view;
                    if (hasValue(toFind, Integer.valueOf(view1.getText().toString()))) {
                        if (hitCount < toFind.length) {
                            view.setBackgroundColor(getResources().getColor(R.color.green));
                            view1.setTextColor(getResources().getColor(R.color.white));
                            view1.setClickable(false);
                            score++;
                            view1.setTextSize(25);
                            hitCount++;
                        }
                        if (hitCount >= toFind.length) {
                            actionButton.setText("Next!");
                        }
                    } else {
                        view.setBackgroundColor(getResources().getColor(R.color.red));
                        view1.setClickable(false);
                        view1.setTextColor(getResources().getColor(R.color.white));
                        score--;
                    }

                    scoreText.setText(String.valueOf(score));
                }
            }
        }
    }

    private static boolean hasValue(int[] numbers, int val) {
        for (int i : numbers) if (val == i) return true;
        return false;
    }
}
