package edu.usc.cs401.schooloffish.Model;

import android.app.Application;
import android.content.Context;

/**
 * Created by Ashley Walker on 3/22/2018.
 */

public class App extends Application {

    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

}
