package edu.usc.cs401.schooloffish.Controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

import edu.usc.cs401.schooloffish.Model.BigFish;
import edu.usc.cs401.schooloffish.Model.Game;
import edu.usc.cs401.schooloffish.Model.Player;
import edu.usc.cs401.schooloffish.R;

/**
 * Created by Ashley Walker on 2/17/2018.
 */

public class GameList extends Fragment {
    public static GameList gameList = null;

    private ArrayList<Game> games;
    public ListView listView;
    public FloatingActionButton newGameButton;
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

        games = new ArrayList<Game>();

        games.add(new Game("Game 1", new BigFish("Chantelle")));
        games.add(new Game("Game 2", new BigFish("June")));
        games.add(new Game("Game 3", new BigFish("Selene")));
        games.add(new Game("Game 4", new BigFish("Ren")));
    }

    //@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.game_list, container, false);

        TextView noGamesText = v.findViewById(R.id.noGamesText);
        if (games.size() > 0) {
            noGamesText.setText("");
        } else noGamesText.setText("No Pending Games");

        newGameButton = (FloatingActionButton) v.findViewById(R.id.newGameButton);
        newGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();

                Intent intent = new Intent(getActivity(), CreateGame.class);
                startActivityForResult(intent, 0);
            }
        });

        // call the views with this layout
        listView = (ListView) v.findViewById(R.id.gameListView);

        gameListViewAdapter = new GameListViewAdapter(getActivity(), 0, games);
        listView.setAdapter(gameListViewAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position,
                                    long id) {
                /*
                Intent intent = new Intent(getActivity(), JoinGameInterfaceController.class);
                intent.putExtra("PROFILE", (Serializable) profiles.get(position));
                startActivityForResult(intent, 0);
                */
            }
        });

        return v;
    }

}
