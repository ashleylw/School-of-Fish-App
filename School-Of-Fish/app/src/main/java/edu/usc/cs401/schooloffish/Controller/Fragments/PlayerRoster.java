package edu.usc.cs401.schooloffish.Controller.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.usc.cs401.schooloffish.Controller.BigFishMain;
import edu.usc.cs401.schooloffish.Controller.ViewAdapters.PlayerRosterViewAdapter;
import edu.usc.cs401.schooloffish.Model.Game;
import edu.usc.cs401.schooloffish.Model.Player;
import edu.usc.cs401.schooloffish.R;

import static android.content.ContentValues.TAG;

/**
 * Created by Ashley Walker on 3/22/2018.
 */

public class PlayerRoster extends Fragment {

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    public static PlayerRoster playerRoster = null;

    private String gameID;
    private Game game;
    private List<Player> players;
    private ListView listView;

    PlayerRosterViewAdapter playerRosterViewAdapter;

    // newInstance constructor for creating fragment with arguments
    public static PlayerRoster newInstance(int page, String title) {
        PlayerRoster playerRosterFragment = new PlayerRoster();
        Bundle args = new Bundle();

        playerRosterFragment.setArguments(args);
        return playerRosterFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        playerRoster = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.player_roster, container, false);

        gameID = getArguments().getString("GameID");

        game = new Game(gameID);
        players = new ArrayList<>();

        DatabaseReference myRef = database.getReference()
                .child("players");

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<Map<String, Object>> genericTypeIndicator = new GenericTypeIndicator<Map<String, Object>>() {};
                Map<String, Object> players = dataSnapshot.getValue(genericTypeIndicator);

                PlayerRoster.this.players.clear();

                if (players != null) {
                    for (String id : players.keySet()) {
                        if (game.getPlayers() != null) {
                            for (String inGameID : game.getPlayers()) {
                                if (id.equals(inGameID)) {
                                    PlayerRoster.this.players.add(new Player(id));
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

        // call the views with this layout
        listView = (ListView) v.findViewById(R.id.rosterListView);

        playerRosterViewAdapter = new PlayerRosterViewAdapter(getActivity(), 0, players);
        listView.setAdapter(playerRosterViewAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position,
                                    long id) {
                //nothing
            }
        });

        playerRosterViewAdapter.notifyDataSetChanged();

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        for (Player p : players) {
            System.out.println(p.getName());
        }
        playerRosterViewAdapter.setPlayers(players);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            System.out.println("Activity result from showing player roster was null");
            return;
        }
    }

    public void render() {
        this.playerRosterViewAdapter.notifyDataSetChanged();
    }

}
