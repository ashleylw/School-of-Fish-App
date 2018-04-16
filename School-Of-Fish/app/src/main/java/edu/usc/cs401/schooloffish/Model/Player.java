package edu.usc.cs401.schooloffish.Model;

import android.util.Log;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;
import java.util.UUID;

import static android.content.ContentValues.TAG;

/**
 * Created by Ashley Walker on 2/17/2018.
 */

public class Player extends NamedObject{

    private static final long serialVersionUID = 2L;

    private Role role;
    private boolean aliveStatus;
    private Habitat currentHabitat;
    private Habitat previousHabitat;

    // Every player can peek at the role of another player.
    // If a player's role is the CRAB, REMORA, or TURTLE, they can peek at two players
    private String peekOne;
    private String peekTwo;

    // If the player is the turtle, we need to keep track of who they guessed would win
    private Role guess;

    // If a player is the Octopus, we need to keep track of who they're disguised as
    private Role disguise;

    // TODO: need to have a way of keeping track of whether a player has eaten

    /**
     * A player can see the following:
     * - NAMES of people in their current location
     * - A choice to bite anyone in their current location
     * - NAMES of people who are dead
     * - in between rounds, only habitats they are ABLE to visit next
     */

    /**
     * Player Constructor
     * @param name of the player
     */
    public Player(String name, Role role) {
        super(name);
        this.role = role;

        aliveStatus = true;
        currentHabitat = role.getHome();
        previousHabitat = role.getHome();

        peekOne = null;
        peekTwo = null;

        guess = null;
        disguise = null;
        updateFirebase(null);

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference myRef = database.getReference()
                .child("players").child(this.getID());

        // Reading from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("name").getValue(String.class);
                Player.this.setName(name);

                String role = dataSnapshot.child("role").getValue(String.class);
                Player.this.role = Role.getRoleForName(role);

                boolean aliveStatus = dataSnapshot.child("aliveStatus").getValue(boolean.class);
                Player.this.aliveStatus = aliveStatus;

                String currentHabitat = dataSnapshot.child("currentHabitat").getValue(String.class);
                Player.this.currentHabitat = Habitat.getHabitatForName(currentHabitat);

                String previousHabitat = dataSnapshot.child("previousHabitat").getValue(String.class);
                Player.this.previousHabitat = Habitat.getHabitatForName(previousHabitat);

                String peekOne = dataSnapshot.child("peekOne").getValue(String.class);
                Player.this.peekOne = peekOne;

                String peekTwo = dataSnapshot.child("peekTwo").getValue(String.class);
                Player.this.peekTwo = peekTwo;

                String guess = dataSnapshot.child("guess").getValue(String.class);
                Player.this.guess = Role.getRoleForName(guess);

                String disguise = dataSnapshot.child("disguise").getValue(String.class);
                Player.this.disguise = Role.getRoleForName(disguise);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    public Player(String id) {
        super("", id);

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference myRef = database.getReference()
                .child("players").child(id);

        if (myRef != null) {
            this.setID(id);
        } else {
            // already unique id
        }

        // Reading from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("name").getValue(String.class);
                Player.this.setName(name);

                String role = dataSnapshot.child("role").getValue(String.class);
                Player.this.role = Role.getRoleForName(role);

                boolean aliveStatus = dataSnapshot.child("aliveStatus").getValue(boolean.class);
                Player.this.aliveStatus = aliveStatus;

                String currentHabitat = dataSnapshot.child("currentHabitat").getValue(String.class);
                Player.this.currentHabitat = Habitat.getHabitatForName(currentHabitat);

                String previousHabitat = dataSnapshot.child("previousHabitat").getValue(String.class);
                Player.this.previousHabitat = Habitat.getHabitatForName(previousHabitat);

                String peekOne = dataSnapshot.child("peekOne").getValue(String.class);
                Player.this.peekOne = peekOne;

                String peekTwo = dataSnapshot.child("peekTwo").getValue(String.class);
                Player.this.peekTwo = peekTwo;

                String guess = dataSnapshot.child("guess").getValue(String.class);
                Player.this.guess = Role.getRoleForName(guess);

                String disguise = dataSnapshot.child("disguise").getValue(String.class);
                Player.this.disguise = Role.getRoleForName(disguise);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }


    public void updateFirebase(DatabaseReference myRef) {
        if (myRef == null) {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            myRef = database.getReference()
                    .child("players").child(this.getID());
        }

        myRef.child("name").setValue(this.getName());
        myRef.child("role").setValue(this.role.getName());
        myRef.child("aliveStatus").setValue(this.aliveStatus);
        myRef.child("currentHabitat").setValue(this.currentHabitat);
        myRef.child("previousHabitat").setValue(this.previousHabitat);

        myRef.child("peekOne").setValue(this.peekOne);
        myRef.child("peekTwo").setValue(this.peekTwo);
        myRef.child("guess").setValue(this.guess);
        myRef.child("disguise").setValue(this.disguise);
    }

    /**
     * @return this player's Role
     */
    public Role getRole() {
        return role;
    }

    /**
     * @param role to set for player
     */
    public void setRole(Role role) {
        this.role = role;
        updateFirebase(null);
    }

    public Habitat getCurrentHabitat() {
        return currentHabitat;
    }

    public void setCurrentHabitat(Habitat currentHabitat) {
        this.currentHabitat = currentHabitat;
        updateFirebase(null);
    }

    public Habitat getPreviousHabitat() {
        return previousHabitat;
    }

    public void setPreviousHabitat(Habitat previousHabitat) {
        this.previousHabitat = previousHabitat;
        updateFirebase(null);
    }

    /**
     * @return true if player's role is POLARBEAR, OCTOPUS, SEALION, CRAB, or TURTLE
     */
    public boolean canVisitIsland() {
        return (this.role == Role.POLARBEAR) ||
                (this.role == Role.OCTOPUS) ||
                (this.role == Role.SEALION) ||
                (this.role == Role.CRAB) ||
                (this.role == Role.TURTLE);
    }

    /**
     * @return player's (first) peek
     */
    public String getPeekOne() {
        return peekOne;
    }

    /**
     * @param peekOne Player to peek and set as peekOne
     */
    public void setPeekOne(String peekOne) {
        this.peekOne = peekOne;
        updateFirebase(null);
    }

    /**
     * @return peekTwo IF the player's Role is CRAB, REMORA, or TURTLE
     */
    public String getPeekTwo() {
        if (this.role == Role.CRAB || this.role == Role.REMORA || this.role == Role.TURTLE) {
            return peekTwo;
        } else return null;
    }

    /**
     * @param peekTwo Player to peek and set as peekTwo (for CRAB, REMORA, and TURTLE only)
     */
    public void setPeekTwo(String peekTwo) {
        this.peekTwo = peekTwo;
        updateFirebase(null);
    }

    /**
     * Bite action performed on another player in same habitat
     * A bite will FAIL if:
     * - p's Role is the FUGU (and this player will die)
     * - p's rank is higher than this.rank
     * - p's Role is a sea animal AND all (alive) sea animals are in the same habitat
     * If a bite SUCCEEDS, then:
     * - p dies
     * - this player HAS EATEN for this round
     * @param p Player to bite
     * @return boolean is bite is successful
     */
    public boolean Bite(Player p) {
        // TODO - check if Player p can be bitten by this
        return false;
    }

    /**
     * IsAlive check
     * @return boolean if aliveStatus is true or not
     */
    public boolean isAlive() {
        return aliveStatus;
    }

    /**
     * setDead action sets player's aliveStatus to false and remove them from all
     * playable Habitats into the Grave
     * A player will DIE if:
     * - their Role is the ORCA and they haven't eaten for 1 round
     * - their Role is the SHARK and they haven't eaten in 2 rounds
     * - their Role is the POLARBEAR and they haven't eaten in 2 rounds
     * - their Role is the EEL and they haven't eaten in 3 rounds
     * - they get eaten
     * These things will likely be handled elsewhere...
     */
    public void setDead() {
        // TODO
    }

    /**
     * Move action performed in between rounds
     * @param h Habitat that this player wants to move to.
     */
    public void Move(Habitat h) {
        // TODO
    }

    /**
     * @param r Role to set guess for TURTLE player only
     */
    public void setGuess(Role r) {
        if (this.role == Role.TURTLE) {
            this.guess = role;
        } else this.guess = this.role;
    }

    /**
     * @return Role of guess (only applicable to turtle; if not the turtle, return own role)
     */
    public Role getGuess() {
        return this.guess;
    }

    /**
     * @param r Role to set disguise for OCTOPUS player only
     */
    public void setDisguise(Role r) {
        if (this.role == Role.OCTOPUS) {
            this.disguise = r;
        } else this.disguise = this.role;
    }

    /**
     * @return Role of disguise (only applicable to octopus; if not the octopus, returns own role)
     */
    public Role getDisguise() {
        return this.disguise;
    }
}
