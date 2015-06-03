package edu.washington.jhand1.wheresmylimbs;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jordan on 5/23/2015.
 */
public class Room {
    private String name; //is a name necessary? RoomID better?
    private String description;
    private List<String> items; //main item to be found in the room (i.e. limb)
    private int x;
    private int y;
    private List<Direction> availableDirections;

    public Room(String name, String description, List<String> items, int x, int y) {
        this.name = name;
        this.description = description;
        this.items = items;
        this.x = x;
        this.y = y;
        this.availableDirections = new ArrayList<>();
    }

    public Room(String name, String description, int x, int y) {
        this(name, description, new ArrayList<String>(), x, y);
    }
    
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getItems() {
        return items;
    }

    public void addItem(String item) {
        items.add(item);
    }

    public void addDirection(Direction direction) {
        availableDirections.add(direction);
    }

    public List<Direction> getAvailableDirections() {
        return availableDirections;
    }

}
