package com.superlamer.connect3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    // 0 = yellow; 1 = red;

    private int activePlayer = 0;
    private static int[][] gameState = new int[3][];
    private int[][]winningPositions = {
            {0,1,2},
            {3,4,5},
            {6,7,8},
            {1,4,7},
            {2,5,8},
            {0,4,8},
            {2,4,6}
    };

    public MainActivity() {
        initializeGameState();
    }

    private static void initializeGameState() {
        for (int i = 0; i < gameState.length; i++) {
            gameState[i] = new int[] {2,2,2};
        }
    }

    private void occupyCell(int col, int row) {
        gameState[col][row] = activePlayer;
    }

    private boolean isCellOccupied(int col, int row) {
        boolean occupied = false;

        if (gameState[col][row] == 1) {
            occupied = true;
        }

        return  occupied;
    }

    private boolean placeItem(ImageView item) {
        boolean successful = false;
        int[] colRow = getItemLocation(item.getTag().toString());
        int col = colRow[0];
        int row = colRow[1];

        if (!isCellOccupied(col,row)) {
            occupyCell(col, row);
            successful = true;
        }

        return successful;
    }

    private int[] getItemLocation(String tag) {

        int[] colRow = new int[2];

        String regexPattern = "\\d";
        Pattern pattern = Pattern.compile(regexPattern);
        Matcher matcher = pattern.matcher(tag);

        int count = 0;
        while (matcher.find()) {
            count++;
            colRow[count - 1] = Integer.valueOf(tag.substring(matcher.start(),matcher.end()));
            tag.substring(matcher.start(),matcher.end());
        }

        return colRow;
    }

    public void dropIn(View view) {
        ImageView counter = (ImageView) view;

        int[] selectedIndex = getItemLocation(counter.getTag().toString());

        if (!(isCellOccupied(selectedIndex[0], selectedIndex[1]))) {
            counter.setTranslationY(-1000f);

            if (activePlayer == 0) {
                counter.setImageResource(R.drawable.yellow);
                if (placeItem(counter)) {
                    activePlayer = 1;
                    counter.animate().translationYBy(1000f).rotation(180).setDuration(2000);
                }
            }
            else {
                counter.setImageResource(R.drawable.red);
                if (placeItem(counter)) {
                    activePlayer = 0;
                    counter.animate().translationYBy(1000f).rotation(180).setDuration(2000);
                }
            }
        }

        for (int[] winningPosition : winningPositions) {

        }



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
