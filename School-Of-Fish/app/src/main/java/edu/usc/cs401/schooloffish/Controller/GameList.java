package edu.usc.cs401.schooloffish.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.usc.cs401.schooloffish.Controller.ViewAdapters.GameListViewAdapter;
import edu.usc.cs401.schooloffish.Model.Game;
import edu.usc.cs401.schooloffish.R;

import static android.content.ContentValues.TAG;

/**
 * Created by Ashley Walker on 2/17/2018.
 */

public class GameList extends Fragment {

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    public static GameList gameList = null;

    List<Game> allGames;
    TextView noGamesText;
    public ListView listView;
    private GameListViewAdapter gameListViewAdapter;

    // newInstance constructor for creating fragment with arguments
    public static GameList newInstance() {
        GameList gameListFragment = new GameList();
        Bundle args = new Bundle();

        gameListFragment.setArguments(args);
        return gameListFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameList = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.game_list, container, false);

        DatabaseReference myRef = database.getReference()
                .child("games");

        allGames = new ArrayList<>();
        noGamesText = v.findViewById(R.id.noGamesText);


        // call the views with this layout
        listView = (ListView) v.findViewById(R.id.gameListView);

        gameListViewAdapter = new GameListViewAdapter(getActivity(), R.layout.game_list_item, allGames);
        listView.setAdapter(gameListViewAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position,
                                    long id) {
                // Have user prompted if they would like to join game
                Intent intent = new Intent(getActivity(), PendingGameInfo.class);
                intent.putExtra("Pending Game", (Serializable) allGames.get(position));
                startActivityForResult(intent, 0);
            }
        });

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<Map<String, Object>> genericTypeIndicator = new GenericTypeIndicator<Map<String, Object>>() {};
                Map<String, Object> games = dataSnapshot.getValue(genericTypeIndicator);

                allGames.clear();
                for (String id : games.keySet()) {
                    allGames.add(new Game(id));
                }
                if (allGames.size() > 0) {
                    noGamesText.setText("");
                } else noGamesText.setText("No Pending Games");

                gameListViewAdapter.notifyDataSetChanged();
                //listView.setAdapter(gameListViewAdapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        return v;
    }

    /**
     * Override of onResume method, notify data set changed in adapter
     */
    @Override
    public void onResume() {
        super.onResume();

        this.gameListViewAdapter.notifyDataSetChanged();
        listView.setAdapter(gameListViewAdapter);
    }

    /**
     * Returning from the CreateGame activity, reset game list
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            System.out.println("Activity result from creating a game was null");
            return;
        }
    }

}
