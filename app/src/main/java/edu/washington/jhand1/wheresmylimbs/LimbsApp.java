package edu.washington.jhand1.wheresmylimbs;

import android.app.Application;

/**
 * Created by Jordan on 5/25/2015.
 */
public class LimbsApp extends Application {
    private static LimbsApp instance = null;

    public LimbsApp() {
        if (instance == null) {
            instance = this;
        } else {
            throw new RuntimeException("Too many Limbs Apps!!");
        }
    }

    public LimbsApp getInstance() {
        return instance;
    }


}
