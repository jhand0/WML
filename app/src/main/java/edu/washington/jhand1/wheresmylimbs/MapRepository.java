package edu.washington.jhand1.wheresmylimbs;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jordan on 5/28/2015.
 */
public class MapRepository {
    private List<String> objectiveItems;
    private List<Room> rooms;
    private int[] turnCounts; //turn counts from easy to hard
    private int xMax;
    private int yMax;
    private Room[][] board;

    public MapRepository() {
        objectiveItems = new ArrayList<>();
        rooms = new ArrayList<>();
        parseJSONFromFile();
    }

    public List<String> getObjectiveItems() {
        return objectiveItems;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public String readJSONFile(InputStream inputStream) throws IOException {

        int size = inputStream.available();
        byte[] buffer = new byte[size];
        inputStream.read(buffer);
        inputStream.close();

        return new String(buffer, "UTF-8");
    }

    public int getXMax() {
        return xMax;
    }

    public int getYMax() {
        return yMax;
    }

    public int[] getTurnCounts() {
        return turnCounts;
    }


    //reads JSON from a file and parses it into usable objects
    private void parseJSONFromFile() {
        try {
            InputStream inputStream = LimbsApp.getInstance().getAssets().open("adventure.json");
            String json = readJSONFile(inputStream);

            JSONObject adventure = new JSONObject(json);

            JSONArray turns = adventure.getJSONArray("turns");
            this.turnCounts = new int[turns.length()];
            for (int i = 0; i < turns.length(); i++) {
                this.turnCounts[i] = turns.getInt(i);
            }

            this.xMax = adventure.getInt("xsize");
            this.yMax = adventure.getInt("ysize");
            board = new Room[xMax][yMax];

            //parse rooms from JSON
            JSONArray jsonRooms = adventure.getJSONArray("rooms");
            for (int i = 0; i < jsonRooms.length(); i++) {
                JSONObject room = jsonRooms.getJSONObject(i);
                parseRoom(room);
            }
            parseObjectives(adventure); //adds room objectives to list
        }catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void parseRoom(JSONObject room) throws JSONException{
        int x = room.getInt("xcoordinate");
        int y = room.getInt("ycoordinate");
        String title = room.getString("room_title");
        //String description = room.getString("room_description");
        Room adventureRoom = new Room(title, x, y);
        JSONArray jsonItems = room.getJSONArray("room_items");
        for (int i = 0; i < jsonItems.length(); i++) {
            adventureRoom.addItem(jsonItems.getString(i));
        }
        JSONArray jsonDirections = room.getJSONArray("available_directions");
        for (int i = 0; i < jsonDirections.length(); i++) {
            adventureRoom.addDirection(jsonDirections.getString(i));
        }
        rooms.add(adventureRoom);
        board[x][y] = adventureRoom; //puts room on the board
    }

    private void parseObjectives(JSONObject adventure) throws JSONException {
        JSONArray jsonObjectives = adventure.getJSONArray("objectives");
        for (int i = 0; i < jsonObjectives.length(); i++) {
            objectiveItems.add(jsonObjectives.getString(i));
        }
    }
}
