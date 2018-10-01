package com.superlamer.uber;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;

import android.app.Application;

public class StarterApplication extends Application {

    @Override
    public void onCreate() {

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(this)
        .applicationId("6a93137fb5a2f8e83eea7ba936faebe9f6d82a76")
        .clientKey("624b9f70d2f2314d51a4e588d17d7f841dd3179a")
        .server("http://18.223.166.188:80/parse/")
        .build());

        //ParseUser.enableAutomaticUser();

        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
        defaultACL.setPublicWriteAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);
    }
}
