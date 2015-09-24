package org.ametiste.elo.infrastructure.dto;

/**
 * Created by atlantis on 9/24/15.
 */
public class PlayerDto {

    private int id;
    private String name;

    public PlayerDto(int id, String name) {

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
