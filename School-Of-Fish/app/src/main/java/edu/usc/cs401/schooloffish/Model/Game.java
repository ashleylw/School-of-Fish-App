package edu.usc.cs401.schooloffish.Model;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

/**
 * Created by Ashley Walker on 2/17/2018.
 */

public class Game extends NamedObject {

    private static final long serialVersionUID = 2L;

    private BigFish bigFish;
    private List<String> players;

    private int numRounds;
    private double roundLength;
    private double preRoundLength;


    public Game(String name, BigFish bigFish, int numRounds, double roundLength, double preRoundLength) {
        super(name);
        this.bigFish = bigFish;
        this.numRounds = numRounds;
        this.roundLength = roundLength;
        this.preRoundLength = preRoundLength;
        this.players = new ArrayList<>();
        updateFirebase(null);

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference myRef = database.getReference()
                .child("games").child(getID());

        // Reading from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("name").getValue(String.class);
                Game.this.setName(name);

                BigFish bigFish = dataSnapshot.child("bigFish").getValue(BigFish.class);
                Game.this.bigFish = bigFish;

                int numRounds = dataSnapshot.child("numRounds").getValue(int.class);
                Game.this.numRounds = numRounds;

                double roundLength = dataSnapshot.child("roundLength").getValue(double.class);
                Game.this.roundLength = roundLength;

                double preRoundLength = dataSnapshot.child("preRoundLength").getValue(double.class);
                Game.this.preRoundLength = preRoundLength;

                GenericTypeIndicator<Map<String, Object>> genericTypeIndicator = new GenericTypeIndicator<Map<String, Object>>() {};
                Map<String, Object> players = dataSnapshot.child("players").getValue(genericTypeIndicator);

                if (players != null) {
                    for (String id : players.keySet()) {
                        Game.this.addPlayer(id);
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }

    public Game(String id) {
        super("");

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference myRef = database.getReference()
                .child("games").child(id);

        if (myRef != null) {
            this.setID(id);
        } else {
            // already have unique id
        }

        // Reading from the database
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("name").getValue(String.class);
                Game.this.setName(name);

                BigFish bigFish = dataSnapshot.child("bigFish").getValue(BigFish.class);
                Game.this.bigFish = bigFish;

                int numRounds = dataSnapshot.child("numRounds").getValue(int.class);
                Game.this.numRounds = numRounds;

                double roundLength = dataSnapshot.child("roundLength").getValue(double.class);
                Game.this.roundLength = roundLength;

                double preRoundLength = dataSnapshot.child("preRoundLength").getValue(double.class);
                Game.this.preRoundLength = preRoundLength;

                GenericTypeIndicator<Map<String, Object>> genericTypeIndicator = new GenericTypeIndicator<Map<String, Object>>() {};
                Map<String, Object> players = dataSnapshot.child("players").getValue(genericTypeIndicator);

                Game.this.players = new ArrayList<>();

                if (players != null) {
                    for (String id : players.keySet()) {
                        Game.this.addPlayer(id);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }

    private void updateFirebase(DatabaseReference myRef) {
        if (myRef == null) {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            myRef = database.getReference()
                    .child("games").child(this.getID());
        }

        myRef.child("name").setValue(this.getName());
        myRef.child("bigFish").setValue(this.bigFish);
        myRef.child("numRounds").setValue(this.numRounds);
        myRef.child("roundLength").setValue(this.roundLength);
        myRef.child("preRoundLength").setValue(this.preRoundLength);

        for (String id : players) {
            myRef.child("players").child(id).setValue(id);
        }
    }

    public BigFish getBigFish() {
        return this.bigFish;
    }

    public List<String> getPlayers() {
        return this.players;
    }

    public void setPlayers(List<Player> players) {
        this.players.clear();

        List<String> newIDs = new ArrayList<>();

        for (Player p : players) {
            newIDs.add(p.getID());
        }

        this.players = newIDs;

        updateFirebase(null);
    }

    public int getNumRounds() {
        return numRounds;
    }

    /**
     * Add a player to the game IF there are less than 13 players
     * @param p player to potentially add
     */
    public void addPlayer(Player p) {
        if (players.size() < 13 && !players.contains(p.getID())) {
            players.add(p.getID());
            updateFirebase(null);
        }
    }

    /**
     * Add a player to the game IF there are less than 13 players
     * @param id id of player to potentially add
     */
    public void addPlayer(String id) {
        if (players.size() < 13 && !players.contains(id)) {
            players.add(id);
            updateFirebase(null);
        }
    }

    public void start() {
        setUp();

        for (int i = 0; i < numRounds; i++) {
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
