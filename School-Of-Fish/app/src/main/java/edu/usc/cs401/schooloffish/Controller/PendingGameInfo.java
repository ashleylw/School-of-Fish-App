package edu.usc.cs401.schooloffish.Controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.usc.cs401.schooloffish.Controller.Fragments.PlayerRoster;
import edu.usc.cs401.schooloffish.Model.Game;
import edu.usc.cs401.schooloffish.Model.Player;
import edu.usc.cs401.schooloffish.Model.Role;
import edu.usc.cs401.schooloffish.R;

import static android.content.ContentValues.TAG;

/**
 * Created by Ashley Walker on 3/24/2018.
 */

public class PendingGameInfo extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    private Game game;
    private Role role;
    private Player p;
    List<String> notAvailable;

    private Button joinGameButton;
    private TextView pendingGameName;
    private TextView pendingBFName;
    private TextView pendingRoundNum;
    private TextView pendingPlayerNum;

    private EditText playerName;
    private AlertDialog dialog;

    public void setGame(Game game) {
        this.game = game;
        this.renderGameInfo();
    }

    public void PendingGameInfo(Game game)
    {
        this.game = game;
    }

    /*
     * renders a profile edit page
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pending_game_info);

        Intent intent = getIntent();
        this.game = (Game) intent.getSerializableExtra("Pending Game");

        this.renderGameInfo();

        //initialize join game button
        joinGameButton = (Button) findViewById(R.id.join_game_button);
        joinGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Launch dialog asking for name

                LayoutInflater inflater = PendingGameInfo.this.getLayoutInflater();
                View view = inflater.inflate(R.layout.create_player_dialog, null);

                // Use the Builder class for convenient dialog construction
                AlertDialog.Builder builder = new AlertDialog.Builder(PendingGameInfo.this);
                builder.setView(view)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Positive response
                                if (playerName.getText().toString().equals("")){
                                    showFormCompletionError("Incomplete Form", "Valid name is needed.");
                                    //} else if(duplicateName()){
                                    //showFormCompletionError("Invalid Name", "Name already exists");
                                } else {
                                    finishCreatePlayer();
                                }
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // negative response
                                // do nothing
                            }
                        });

                dialog = builder.create();

                playerName = (EditText) view.findViewById(R.id.create_player_name);

                // set this instance as callback for editor action
                playerName.requestFocus();

                dialog.setTitle("Please provide your name:");
                dialog.show();

            }

        });

        notAvailable = new ArrayList<>();

        DatabaseReference myRef = database.getReference()
                .child("players");

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<Map<String, Object>> genericTypeIndicator = new GenericTypeIndicator<Map<String, Object>>() {};
                Map<String, Object> players = dataSnapshot.getValue(genericTypeIndicator);

                notAvailable.clear();

                if (players != null) {
                    for (String id : players.keySet()) {
                        if (game.getPlayers() != null) {
                            for (String inGameID : game.getPlayers()) {
                                if (id.equals(inGameID)) {

                                    // Player exists in game, need to add  Role to unavailable roles list
                                    notAvailable.add(dataSnapshot.child(id).child("role").getValue(String.class));

                                }
                            }
                        }
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

    private void renderGameInfo() {
        pendingGameName = (TextView) findViewById(R.id.pending_game_name);
        pendingBFName = (TextView) findViewById(R.id.pending_bf_name);
        pendingRoundNum = (TextView) findViewById(R.id.pending_round_num);
        pendingPlayerNum = (TextView) findViewById(R.id.pending_player_num);

        pendingGameName.setText(game.getName());
        pendingBFName.setText(game.getBigFish().getName());
        pendingRoundNum.setText(game.getNumRounds()+"");
        pendingPlayerNum.setText(game.getPlayers().size()+"");
    }

    public void finishCreatePlayer() {
        // TODO: on dialog lose, assign random role, and initialize player
        dialog.dismiss();

        boolean noAvail = true;
        do {
            noAvail = false;
            role = Role.getRandomRole();
            System.err.println("***************FINDING AVAILABLE ROLE************\n******   "
                    + notAvailable.size() + role.getName());

            for (String r : notAvailable) {
                if (r.equalsIgnoreCase(role.getName())) {
                    noAvail = true;
                }
            }
        } while (noAvail);

        p = new Player(playerName.getText().toString(), role);

        // TODO: Add player to player list in game
        game.addPlayer(p);

        // Start PlayerMain activity
        Intent myIntent = new Intent(PendingGameInfo.this, PlayerMain.class);
        myIntent.putExtra("GameID", game.getID());
        PendingGameInfo.this.startActivity(myIntent);
    }

    /*
    private boolean duplicateName(){
        ArrayList<Schedule> schedules = ScheduleManager.getDefaultManager().getAllSchedules();
        for (Schedule sched: schedules){
            if (sched.getName().equals(playerName.getText().toString())){
                return true;
            }
        }
        return false;
    }*/

    private void showFormCompletionError(String title, String message) {
        new AlertDialog.Builder(getApplicationContext())
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Okay", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which) {

                    }

                })
                .show();
    }

}
