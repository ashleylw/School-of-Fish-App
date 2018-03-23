package edu.usc.cs401.schooloffish.Model;

import java.util.ArrayList;

/**
 * Created by Ashley Walker on 3/19/2018.
 */

public class AllGames {

    private static ArrayList<Game> allGames = new ArrayList<>();

    private static class Loader {
        static volatile AllGames INSTANCE = new AllGames();
    }

    public static synchronized AllGames getInstance() {
        return Loader.INSTANCE;
    }

    private AllGames() {
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
