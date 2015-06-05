package edu.washington.jhand1.wheresmylimbs;

/**
 * Created by Jordan on 5/23/2015.
 */
public class Item {
    private String name;
    private String art;
    private boolean collected;

    public Item(String name, String art) {
        this.name = name;
        this.art = art;
        collected = false;
    }

    public boolean isCollected() {
        return collected;
    }

    public void collect() {
        collected = true;
    }

    public String getArt() {
        return art;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Item))
            return false;
        return this.name.equals(((Item) obj).name);
    }

    public String toString() {
        return getName();
    }
}
