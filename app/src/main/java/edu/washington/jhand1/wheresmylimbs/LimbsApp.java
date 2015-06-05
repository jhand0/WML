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
    private List<Item> items;
    private int movesLeft;
    private boolean allItemsCollected;
    private int currX;
    private int currY;
    private Room currentRoom;
    Room[][] board;

    public LimbsApp() {
        if (instance == null) {
            instance = this;
            items = new ArrayList<>();
        } else {
            throw new RuntimeException("Ya got too many Limbs Apps!!");
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("LimbsApp", "LimbsApp object has been initialized");

        mapRepo = new MapRepository(this);

        items = mapRepo.getObjectiveItems();

        board = mapRepo.getBoard();
        currX = mapRepo.getStartX();
        currY = mapRepo.getStartY();
        currentRoom = board[currX][currY];
        allItemsCollected = false;
    }

    public static LimbsApp getInstance() {
        return instance;
    }

    public MapRepository getMapRepository() {
        return mapRepo;
    }

    // Initial setup called from game activity

    public void setDifficulty(int difficulty) {
        movesLeft = mapRepo.getMoves(difficulty);
    }

    // Getters for the game/end activity

    public boolean canMove(Direction direction) {
        return currentRoom.getAvailableDirections().contains(direction);
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
                    currX++;
                    break;
                case WEST:
                    currX--;
                    break;
            }
        }
        currentRoom = board[currX][currY];
        movesLeft--;

        List<Item> roomItems = currentRoom.getItems();
        for (int i = 0; i < roomItems.size(); i++) {
            for (int j = 0; j < items.size(); j++) {
                if (items.get(j).equals(roomItems.get(i))) {
                    items.get(j).collect();
                    break;
                }
            }
        }

        // TODO: Add code to check if all items found; if so, flip allItemsCollected boolean
        boolean checkColl = true;
        for (int i = 0; i < items.size(); i++) {
            if (!items.get(i).isCollected()) {
                checkColl = false;
            }
        }
        allItemsCollected = checkColl;
    }

    public int movesLeft() {
        return movesLeft;
    }

    public String getRoomTitle() {
        return currentRoom.getName();
    }

    public String getRoomDescription() {
        return currentRoom.getDescription();
    }

    public String getRoomUpdate() {
        // TODO: implement this method:
//        if (roomcontainsitem) {
//            store item in variable
//            remove item from room;
//            return "You have found " + item.getName();
//        } else {
//            return "";
//        }

        // Stub:
        return "";
    }

    public boolean allItemsCollected() {
        return allItemsCollected;
    }

    public String getIntro() {
        return mapRepo.getIntro();
    }

    public String getDeathMessage() {
        return mapRepo.getDeathMessage();
    }

    public String getVictoryMessage() {
        return mapRepo.getVictoryMessage();
    }

    public String getAdventureTitle() {
        return mapRepo.getMapTitle();
    }

    public List<Item> getItems() {
        return items;
    }

    public void createRepo() {
        // TODO: implement method for creating a new repo
        mapRepo = new MapRepository(this);

        items = mapRepo.getObjectiveItems();

        board = mapRepo.getBoard();
        currX = mapRepo.getStartX();
        currY = mapRepo.getStartY();
        currentRoom = board[currX][currY];
        allItemsCollected = false;
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

    public String loadJSON(InputStream is) {
        String json;
        try {
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return json;
    }

}
