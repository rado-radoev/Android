package com.superlamer.showimages;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    public void switchImage(View view) {
        ImageView image = (ImageView) findViewById(R.id.imageView);

        image.setImageResource(R.drawable.dog2);

        if (getResources().getDrawable(R.drawable.dog1) == R.drawable.dog1) {
            image.setImageResource(R.drawable.dog2);
        } else {
            image.setImageResource(R.drawable.dog1);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
