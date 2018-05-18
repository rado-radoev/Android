package com.superlamer.animation;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private ImageView bart;

    public void fade(View view) {

        bart.animate().translationXBy(1000f).setDuration(2000);
    }

 @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         bart = (ImageView) findViewById(R.id.bart);

         bart.setTranslationX(-1000f);
    }


}
