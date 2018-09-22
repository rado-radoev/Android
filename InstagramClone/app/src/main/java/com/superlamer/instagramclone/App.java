package com.superlamer.instagramclone;

import android.app.Application;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;

public class App extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("d3a349773a97bf5e3eca2cb394fef58132d70bbc")
                // if defined
                .clientKey("bdfb0d2820f9f213443bd417fe62e2b156b4df4f")
                .server("http://18.223.156.221:80/parse/")
                .build()
        );

        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
        defaultACL.setPublicWriteAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);
    }
}
