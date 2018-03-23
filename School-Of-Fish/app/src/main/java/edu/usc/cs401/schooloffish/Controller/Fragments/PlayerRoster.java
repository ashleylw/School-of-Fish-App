package edu.usc.cs401.schooloffish.Controller.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import edu.usc.cs401.schooloffish.Controller.BigFishMain;
import edu.usc.cs401.schooloffish.Controller.CreateGame;
import edu.usc.cs401.schooloffish.Controller.LoadingDialog;
import edu.usc.cs401.schooloffish.Controller.ViewAdapters.PlayerRosterViewAdapter;
import edu.usc.cs401.schooloffish.Model.AllGames;
import edu.usc.cs401.schooloffish.Model.Game;
import edu.usc.cs401.schooloffish.Model.Player;
import edu.usc.cs401.schooloffish.R;

/**
 * Created by Ashley Walker on 3/22/2018.
 */

public class PlayerRoster extends Fragment {

    public static PlayerRoster playerRoster = null;

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

        players = AllGames.getInstance().getList().get(0).getPlayers();

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

        FragmentManager fm = getFragmentManager();
        LoadingDialog loadingDialogFragment = LoadingDialog.newInstance();
        // SETS the target fragment for use later when sending results
        loadingDialogFragment.setTargetFragment(PlayerRoster.this, 0);
        loadingDialogFragment.show(fm, "loading");

        players.clear();
        players = AllGames.getInstance().getList().get(0).getPlayers();
        playerRosterViewAdapter.notifyDataSetChanged();

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        players.clear();
        players = AllGames.getInstance().getList().get(0).getPlayers();
        System.out.println(AllGames.getInstance().size());
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
