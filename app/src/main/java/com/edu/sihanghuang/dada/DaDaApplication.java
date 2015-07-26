package com.edu.sihanghuang.dada;

import android.app.Application;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseCrashReporting;
import com.parse.ParseUser;
import com.parse.ParseInstallation;

/**
 * Created by jasonhuang on 7/24/15.
 */
public class DaDaApplication extends Application{

    private String app_id = "kpC3WlseWq0eDAiyeDdykzNTKcbJTWuXexLOFp3o";
    private String app_secret = "uVrOJyPIMN6NIT1qi9VcSJW4hltqnMvGFBFZeVmv";
    @Override
    public void onCreate() {
        super.onCreate();

        // Parse SDK
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, app_id, app_secret);
        ParseInstallation.getCurrentInstallation().saveInBackground();
    }
}
