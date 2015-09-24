package org.ametiste.elo.domain.model;

/**
 * Created by atlantis on 9/24/15.
 */
public class PlayerInfo {

    private int id;
    private String name;

    public PlayerInfo(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
