package com.amir.alzheimer.activites.games;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;

import com.amir.alzheimer.R;
import com.amir.alzheimer.base.BaseActivity;

import java.util.Random;

public class SquareMatchActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "SquareMatchActivity";
    private int step = 3, score = 0;
    private GridLayout first, second;
    private boolean isMatch;
    private TextView scoreText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_square_match);


        first = findViewById(R.id.activity_square_match_first);
        second = findViewById(R.id.activity_square_match_second);
        scoreText = findViewById(R.id.activity_square_match_score);
        update();

        findViewById(R.id.activity_square_match_match_button).setOnClickListener(this);
        findViewById(R.id.activity_square_match_miss_button).setOnClickListener(this);
        Log.wtf(TAG, "width: " + displayWidth + " height " + displayHeight);

    }

    private void update() {
        Integer[][] first = generateMatrix(step);
        isMatch = new Random().nextBoolean();
        apply(this.first, first);
        if (isMatch) {
            int rot = Math.abs(new Random().nextInt() % 4);
            for (int i = 0; i < rot; i++) {
                first = rotateMatrixClockwise(first);
            }
            apply(this.second, first);
        } else {
            apply(this.second, generateMatrix(step));
        }

    }

    private void apply(GridLayout grid, Integer[][] matrix) {
        int smallest = (int) ((displayWidth < displayHeight ? displayWidth : displayHeight) / 2.3);
        grid.removeAllViews();
        grid.setColumnCount(matrix[0].length);
        for (int i = 0; i < matrix[0].length * matrix[0].length; i++) {
            View view = new View(this);
            if (matrix[i / matrix[0].length][i % matrix[0].length] == 1)
                view.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            int size = smallest / matrix[0].length;
            view.setLayoutParams(new ViewGroup.LayoutParams(size, size));
            grid.addView(view);
        }
    }


    public static Integer[][] rotateMatrixClockwise(Integer[][] matrix) {
        Integer[][] rotated = new Integer[matrix[0].length][matrix.length];

        for (int i = 0; i < matrix[0].length; ++i) {
            for (int j = 0; j < matrix.length; ++j) {
                rotated[i][j] = matrix[matrix.length - j - 1][i];
            }
        }

        return rotated;
    }

    public static Integer[][] generateMatrix(int size) {

        Random ran = new Random();
        Integer[][] matrix = new Integer[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = Math.abs(ran.nextInt() % 2);
            }
        }
        return matrix;
    }

    public static Integer[][] rotateMatrixCounterClockwise(Integer[][] matrix) {
        Integer[][] rotated = new Integer[matrix[0].length][matrix.length];

        for (int i = 0; i < matrix[0].length; ++i) {
            for (int j = 0; j < matrix.length; ++j) {
                rotated[i][j] = matrix[j][matrix[0].length - i - 1];
            }
        }

        return rotated;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.activity_square_match_match_button) {
            if (isMatch) {
                score++;
                if (step < 6)
                    step++;
            } else
                score--;
            update();
        } else if (id == R.id.activity_square_match_miss_button) {
            if (isMatch) {
                score--;
            }
            update();
        }
        scoreText.setText(String.valueOf(score));

    }
}
