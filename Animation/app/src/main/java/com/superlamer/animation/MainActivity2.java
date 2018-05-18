package com.superlamer.animation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class MainActivity2 extends AppCompatActivity implements View.OnClickListener {

    private ImageView bart;
    private ImageView homer;

    public void fade(ImageView imageToFade, ImageView imageToShow) {
        imageToFade.animate().alpha(0f).setDuration(2000);
        imageToShow.animate().alpha(1f).setDuration(2000);
        imageToShow.bringToFront();
    }

 @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bart = (ImageView) findViewById(R.id.bart);
        bart.setOnClickListener(this);

        homer = (ImageView) findViewById(R.id.homer);
        homer.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.bart) {
            fade(bart, homer);
        }
        else if (view.getId() == R.id.homer) {
            fade(homer, bart);
        }
    }

}
