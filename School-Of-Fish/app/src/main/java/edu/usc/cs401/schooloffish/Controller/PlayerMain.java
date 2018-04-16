package edu.usc.cs401.schooloffish.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

import edu.usc.cs401.schooloffish.Controller.Fragments.PlayerRoster;
import edu.usc.cs401.schooloffish.Controller.ViewAdapters.PlayerRosterViewAdapter;
import edu.usc.cs401.schooloffish.Model.Game;
import edu.usc.cs401.schooloffish.Model.Player;
import edu.usc.cs401.schooloffish.R;

import static android.content.ContentValues.TAG;

/**
 * Created by Ashley Walker on 3/24/2018.
 */

public class PlayerMain extends AppCompatActivity {

    private Game game;
    private Player player;
    private ArrayList<Player> players;

    private TextView habitatTextView;
    private TextView roleFact;
    private TextView gameName;

    private ListView playerList;

    private TextView timeLeft;

    private PlayerRosterViewAdapter playerRosterViewAdapter;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_main);

        Intent intent = getIntent();
        game = (Game) intent.getExtras().getSerializable("Game");
        player = (Player) intent.getExtras().getSerializable("Player");

        habitatTextView = (TextView) findViewById(R.id.habitat_pl);
        roleFact = (TextView) findViewById(R.id.role_fact);
        gameName = (TextView) findViewById(R.id.game_name_pl);
        playerList = (ListView) findViewById(R.id.player_list_pl);
        timeLeft = (TextView) findViewById(R.id.time_left_round_pl);

        // Set up initial view
        habitatTextView.setText("Pending - Round 0");
        roleFact.setText("Your role is the " + player.getRole().getName());
        gameName.setText(game.getName());
        timeLeft.setText(game.getTimeLeft()/60 + " mins " + game.getTimeLeft()%60 + " secs");

        players = new ArrayList<>();

        playerRosterViewAdapter = new PlayerRosterViewAdapter(PlayerMain.this, R.layout.player_roster_item, players);
        playerList.setAdapter(playerRosterViewAdapter);
        playerRosterViewAdapter.notifyDataSetChanged();

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference myRef = database.getReference()
                .child("players");

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<Map<String, Object>> genericTypeIndicator = new GenericTypeIndicator<Map<String, Object>>() {};
                Map<String, Object> players = dataSnapshot.getValue(genericTypeIndicator);

                PlayerMain.this.players.clear();

                if (players != null) {
                    for (String id : players.keySet()) {
                        if (game.getPlayers() != null) {
                            for (String inGameID : game.getPlayers()) {
                                if (id.equals(inGameID)) {
                                    System.err.println("****************\nPLAYER MAIN LIST\n**************");
                                    PlayerMain.this.players.add(new Player(id));
                                }
                            }
                        }
                    }
                }

                playerRosterViewAdapter.setPlayers(PlayerMain.this.players);
                playerRosterViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

}
