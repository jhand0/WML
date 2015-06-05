package edu.washington.jhand1.wheresmylimbs;

/**
 * Created by Jordan on 5/23/2015.
 */
public class Item {
    private String name;
    private boolean collected;


    public Item(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Item))
            return false;
        return obj == this || (this.name.equals(((Item) obj).name));
    }
}
