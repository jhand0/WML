package edu.washington.jhand1.wheresmylimbs;

import android.app.Application;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jordan on 5/25/2015.
 */
public class LimbsApp extends Application {
    private static LimbsApp instance = null;
    private MapRepository mapRepo;
    private List<String> inventory;
    private int currMove;
    private int maxMoves;
    private Room currentRoom;

    public LimbsApp() {
        if (instance == null) {
            instance = this;
            mapRepo = new MapRepository();
            inventory = new ArrayList<>();
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

    public void move(Direction direction) {

    }

    public int movesLeft() {
        return maxMoves - currMove;
    }

    public String getRoomTitle() {
        return currentRoom.getName();
    }

    /* Rooms dont currently have a description
    public String getRoomDescription() {

    }
    */


    /*what does this mean?
    public String getRoomUpdate() {

    }
    */

    public boolean allItemsCollected() {
        return inventory.containsAll(mapRepo.getObjectiveItems());
    }

    public List<String> getItemsCollected() {
        return inventory;
    }




}
