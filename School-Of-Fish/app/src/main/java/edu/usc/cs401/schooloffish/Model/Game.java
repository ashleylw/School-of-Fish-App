package edu.usc.cs401.schooloffish.Model;

import com.google.api.services.sheets.v4.model.Spreadsheet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ashley Walker on 2/17/2018.
 */

public class Game {
    //private static final Game ourInstance = new Game("School of Fish");

    //public static Game getInstance() { return ourInstance; }

    private String name;
    private BigFish bigFish;
    private List<Player> players;

    private Spreadsheet sheet;

    private int numOfRounds;
    private double roundLength;
    private double preRoundLength;

    public Game(String name, BigFish bigFish, int numOfRounds, Spreadsheet sheet) {
        this.name = name;
        this.bigFish = bigFish;
        this.numOfRounds = numOfRounds;
        this.sheet = sheet;
        this.players = new ArrayList<Player>();
    }

    public String getName() {
        return this.name;
    }

    public BigFish getBigFish() {
        return this.bigFish;
    }

    /**
     * Add a player to the game IF there are less than 13 players
     * @param p player to potentially add
     */
    public void addPlayer(Player p) {
        if (players.size() < 13) {
            players.add(p);
        }
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
