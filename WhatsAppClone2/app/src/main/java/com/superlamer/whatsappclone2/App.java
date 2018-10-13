package com.superlamer.whatsappclone2;

import com.parse.Parse;
import com.parse.ParseSession;

import android.app.Application;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("5a4b6dc50fa434bc55801638a9f9a64f1a15c07b")
                // if desired
                .clientKey("678d585a95e2e3054fdc344b32d7094a7ab68b56")
                .server("http://18.224.200.246:80/parse/")
                .build()
        );
    }
}