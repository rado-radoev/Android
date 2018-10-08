package com.superlamer.twitterclone;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

/*                                    #########################################################################
        [   21.287994] bitnami[1145]: #                                                                       #
        [   21.300090] bitnami[1145]: #        Setting Bitnami application password to '7bki9dXFe9MS'         #
        [   21.311992] bitnami[1145]: #        (the default application username is 'user')                   #
        [   21.325458] bitnami[1145]: #                                                                       #
        [   21.341579] bitnami[1145]: #########################################################################
*/

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

        ParseObject.create("tweets");
    }
}