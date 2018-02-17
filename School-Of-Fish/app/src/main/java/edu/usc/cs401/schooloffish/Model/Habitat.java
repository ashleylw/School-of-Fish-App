package edu.usc.cs401.schooloffish.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ashley Walker on 2/17/2018.
 */

public enum Habitat {

    OCEAN("Ocean"),
    REEF("Reef"),
    ISLAND("Island"),
    KELPFOREST("Kelp Forest"),
    GRAVE("Grave");

    /**
     * The Ocean, Reef, Island, and Kelp Forest are all playable habitats.
     * The Grave is the location all dead players reside. They cannot move
     * from the Grave and alive players cannot move to the Grave.
     */

    private String name;
    private List<Player> players;

    private Habitat(String name) {
        this.name = name;
        this.players = new ArrayList<Player>();
    }

    public String getName() {
        return this.name;
    }

    public List<Player> getPlayers() {
        return this.players;
    }

    public void setPlayers(List<Player> newPlayers) {
        this.players = newPlayers;
    }

}
