package edu.usc.cs401.schooloffish.Model;

import java.util.ArrayList;

/**
 * Created by Ashley Walker on 3/19/2018.
 */

public class AllGames {

    String name;
    ArrayList<Game> allGames;

    private static final AllGames ourInstance = new AllGames("School of Fish");

    public static AllGames getInstance() { return ourInstance; }

    public AllGames(String name) {
        this.name = name;
        allGames = new ArrayList<Game>();
    }

    public void addGame(Game g) {
        allGames.add(g);
    }

    public void removeGame(Game g) {
        allGames.remove(g);
    }

    public int size() {
        return allGames.size();
    }

    public ArrayList<Game> getList() {
        return allGames;
    }
}
