package com.superlamer.connect3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuView;
import android.view.View;
import android.widget.ImageView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    // 0 = yellow; 1 = red;

    private int activePlayer = 0;
    private int[][] gameState = new int[3][];

    private void initializeGameState() {
        for (int i = 0; i < gameState.length; i++) {
            gameState[i] = new int[] {0,0,0};
        }
    }

    private void occupyIndex(int[] colRowToOccupy) {
        int col = colRowToOccupy[0];
        int row = colRowToOccupy[1];

        if (gameState[row][col] == 0) {
            gameState[row][col] = 1;
        }
    }

    private boolean isCellOccupied(int[] colRowToOccupy) {
        int col = colRowToOccupy[0];
        int row = colRowToOccupy[1];
        boolean occupied = false;

        if (gameState[col][row] == 1) {
            occupied = true;
        }

        return  occupied;
    }

    private void placeAnItem(ImageView item) {
        int[] colRow = getSelectedIndex(item.getTag().toString());

        if (!isCellOccupied(colRow)) {
            occupyIndex(colRow);
        }
    }

    private int[] getSelectedIndex(String tag) {

        int[] colRow = new int[2];

        String regexPattern = "\\d";
        Pattern pattern = Pattern.compile(regexPattern);
        Matcher matcher = pattern.matcher(tag);

        int col = Integer.valueOf(matcher.group(1));
        int row = Integer.valueOf(matcher.group(2));

        colRow[0] = col;
        colRow[1] = row;

        return colRow;
    }

    public void dropIn(View view) {
        ImageView counter = (ImageView) view;
        initializeGameState();

        counter.setTranslationY(-1000f);
        System.out.println(counter.getTag());

        if (activePlayer == 0) {
            counter.setImageResource(R.drawable.yellow);
            placeAnItem(counter);
            activePlayer = 1;
        }
        else {
            counter.setImageResource(R.drawable.red);
            placeAnItem(counter);
            activePlayer = 0;
        }

        counter.animate().translationYBy(1000f).rotation(180).setDuration(2000);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
