package edu.washington.jhand1.wheresmylimbs;

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
    private int[] moves; // Turn counts from easy to hard
    private int xMax;
    private int yMax;
    private int startX;
    private int startY;
    private Room[][] board;
    private String mapTitle;
    private String mapIntro;
    private String deathMessage;
    private String victoryMessage;

    public MapRepository(LimbsApp limbsApp) {
        objectiveItems = new ArrayList<>();
        rooms = new ArrayList<>();

        InputStream json = null;
        // Gets json from assets and sends it to MapRepo
        try {
            json = limbsApp.getAssets().open(limbsApp.FILENAME);
        } catch (IOException e) {
            e.printStackTrace();
        }

        parseJSONFromFile(limbsApp.loadJSON(json));
    }

    public List<String> getObjectiveItems() {
        return objectiveItems;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public Room[][] getBoard() {
        return board;
    }

    public int getXMax() {
        return xMax;
    }

    public int getYMax() {
        return yMax;
    }

    public int getMoves(int difficulty) {
        return moves[difficulty];
    }

    public String getIntro() {
        return mapIntro;
    }

    public String getDeathMessage() {
        return deathMessage;
    }

    public String getVictoryMessage() {
        return victoryMessage;
    }

    public String getMapTitle() {
        return mapTitle;
    }

    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }


    // Reads JSON from a file and parses it into usable objects
    private void parseJSONFromFile(String json) {
        try {
            JSONObject adventure = new JSONObject(json);

            JSONArray turns = adventure.getJSONArray("turns");
            this.moves = new int[turns.length()];
            for (int i = 0; i < turns.length(); i++) {
                this.moves[i] = turns.getInt(i);
            }

            this.startX = Integer.parseInt(adventure.getString("startx"));
            this.startY = Integer.parseInt(adventure.getString("starty"));
            this.mapTitle = adventure.getString("title");
            this.mapIntro = adventure.getString("description");
            this.victoryMessage = adventure.getString("victory_message");
            this.deathMessage = adventure.getString("death_message");
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
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void parseRoom(JSONObject room) throws JSONException{
        int x = room.getInt("xcoordinate");
        int y = room.getInt("ycoordinate");
        String title = room.getString("room_title");
        String description = room.getString("room_description");
        Room adventureRoom = new Room(title, description, x, y);
        /*
        JSONArray jsonItems = room.getJSONArray("room_items");
        for (int i = 0; i < jsonItems.length(); i++) {
            adventureRoom.addItem(jsonItems.getString(i));
        }
        */
        JSONArray jsonDirections = room.getJSONArray("available_directions");
        for (int i = 0; i < jsonDirections.length(); i++) {
            Direction direction = checkDirection(jsonDirections.getString(i));
            adventureRoom.addDirection(direction);
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

    private Direction checkDirection(String dirString) {
        Direction direction = null;
        switch (dirString) {
            case "north":
                direction = Direction.NORTH;
                break;
            case "south":
                direction = Direction.SOUTH;
                break;
            case "east":
                direction = Direction.EAST;
                break;
            case "west":
                direction = Direction.WEST;
                break;
            default: break;
        }

        return direction;
    }
}