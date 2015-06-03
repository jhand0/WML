package edu.washington.jhand1.wheresmylimbs;

import android.app.Application;
import android.util.Log;

/**
 * Created by Jordan on 5/25/2015.
 */
public class LimbsApp extends Application {
    private static LimbsApp instance = null;
    private MapRepository mapRepo;

    public LimbsApp() {
        if (instance == null) {
            instance = this;
            mapRepo = new MapRepository();
        } else {
            throw new RuntimeException("Ya got too many Limbs Apps!!");
        }


    }

    public static LimbsApp getInstance() {
        return instance;
    }

    public MapRepository getMapRepository() {
        return mapRepo;
    }


}
