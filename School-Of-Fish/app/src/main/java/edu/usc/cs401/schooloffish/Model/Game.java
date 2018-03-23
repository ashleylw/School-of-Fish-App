package edu.usc.cs401.schooloffish.Model;

import android.app.ActivityManager;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.GooglePlayServicesAvailabilityIOException;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.usc.cs401.schooloffish.Controller.MainActivity;

/**
 * Created by Ashley Walker on 2/17/2018.
 */

public class Game {
    //private static final Game ourInstance = new Game("School of Fish");

    //public static Game getInstance() { return ourInstance; }

    private String name;
    private BigFish bigFish;
    private List<Player> players;

    private String sheetID;

    private int numOfRounds;
    private double roundLength;
    private double preRoundLength;


    public Game(String name, BigFish bigFish, int numOfRounds, String sheetID) {
        this.name = name;
        this.bigFish = bigFish;
        this.numOfRounds = numOfRounds;
        this.sheetID = sheetID;
        this.players = new ArrayList<Player>();
    }

    public String getName() {
        return this.name;
    }

    public BigFish getBigFish() {
        return this.bigFish;
    }

    public List<Player> getPlayers() {
        return this.players;
    }

    public void setPlayers(List<Player> players) {
        this.players.clear();
        this.players = players;
    }

    public String getSheetID() { return sheetID; }

    /**
     * Add a player to the game IF there are less than 13 players
     * @param p player to potentially add
     */
    public void addPlayer(Player p) {
        if (players.size() < 13) {
            players.add(p);
        }
    }

    /**
     * Read the current amount of players in the googlesheet
     */
    public void readPlayers() {

    }

    public void start() {
        setUp();

        for (int i = 0; i < numOfRounds; i++) {
            preRound();
            round();
        }

        end();
    }

    /**
     * PENDING GAME
     * In setUp, the players are initialized, given roles, asked their peeks (and additional info).
     * The bigFish is prompted to set number of rounds, round length, and preRound length
     * When everything is set, method returns
     */
    private void setUp() {
        // TODO
    }

    /**
     * A Timer is set to the preRoundLength,
     * and Players must choose where to move to for the next round.
     * If a player fails to choose, they die.
     */
    private void preRound() {
        // TODO
    }

    /**
     * A Timer is set to the roundLength
     * Players that need to eat must do so before the Timer is up, otherwise they die
     * Players that are successfully eaten die immediately
     * When a player dies, an in-game notification is sent to all players and the bigFish
     *      AND data on screens is immediately changed
     */
    private void round() {
        // TODO
    }

    /**
     * GAME OVER
     * Leaderboard of winning players and their respective roles is shown on every screen
     * Prompt if bigFish would like to initiate another game
     */
    private void end() {
        // TODO
    }
}
