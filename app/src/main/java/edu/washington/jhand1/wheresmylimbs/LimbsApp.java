package edu.washington.jhand1.wheresmylimbs;

import android.app.Application;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jordan on 5/25/2015.
 */
public class LimbsApp extends Application {

    public static final String FILENAME = "adventure.json";

    private static LimbsApp instance = null;
    private MapRepository mapRepo;
    private List<String> inventory;
    private int currMove;
    private int maxMoves;
    private int currX;
    private int currY;
    private Room currentRoom;
    Room[][] board;

    public LimbsApp() {
        if (instance == null) {
            instance = this;
            mapRepo = new MapRepository();
            inventory = new ArrayList<>();
            board = mapRepo.getBoard();
            currX = 0;
            currY = 0;
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
        if (currentRoom.getAvailableDirections().contains(direction)) {
            switch (direction) {
                case NORTH:
                    currY++;
                    break;
                case SOUTH:
                    currY--;
                    break;
                case EAST:
                    currX--;
                    break;
                case WEST:
                    currY++;
                    break;
            }
        }
        currentRoom = board[currX][currY];
    }

    public int movesLeft() {
        return maxMoves - currMove;
    }

    public String getRoomTitle() {
        return currentRoom.getName();
    }

    public String getRoomDescription() {
        return currentRoom.getDescription();
    }


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



    // Readers and writers

    public void writeToFile(String data) {
        try {
            Log.i("MyApp", "writing downloaded to file");

            File file = new File(getFilesDir().getAbsolutePath(), FILENAME);
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(data.getBytes());
            fos.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    public String loadJSON(FileInputStream fis) {
        String json;
        try {
            int size = fis.available();
            byte[] buffer = new byte[size];
            fis.read(buffer);
            fis.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return json;
    }

}
