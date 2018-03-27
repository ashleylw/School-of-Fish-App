package edu.usc.cs401.schooloffish.Controller;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import edu.usc.cs401.schooloffish.Controller.Fragments.GameMain;
import edu.usc.cs401.schooloffish.Controller.Fragments.GameRules;
import edu.usc.cs401.schooloffish.Controller.Fragments.GameUpdates;
import edu.usc.cs401.schooloffish.Controller.Fragments.PlayerRoster;

/**
 * Created by Ashley Walker on 3/22/2018.
 */

public class GamePagerAdapter extends FragmentPagerAdapter {

    private static final String MAIN_TITLE = "MAIN";
    private static final String UPDATES_TITLE = "UPDATES";
    private static final String RULES_TITLE = "RULES";
    private static final String ROSTER_TITLE = "ROSTER";

    private int NUM_TABS = 3;

    private boolean bigFish;
    private String gameID;

    public GamePagerAdapter(final FragmentManager fragmentManager, boolean bigFish, String gameID) {
        super(fragmentManager);
        this.bigFish = bigFish;
        this.gameID = gameID;

        if (this.bigFish) NUM_TABS = 4;
    }

    @Override
    public Fragment getItem(final int position) {
        switch (position) {
            case 0:
            default:
                return new GameMain();
            case 1:
                return new GameUpdates();
            case 2:
                return new GameRules();
            case 3:
                PlayerRoster pr = new PlayerRoster();
                Bundle myBundle = new Bundle();
                myBundle .putString("GameID", gameID);
                pr.setArguments(myBundle);
                return pr;
        }
    }

    @Override
    public int getCount() {
        return NUM_TABS;
    }

    @Override
    public CharSequence getPageTitle(final int position) {
        switch (position) {
            case 0:
                return MAIN_TITLE;
            case 1:
                return UPDATES_TITLE;
            case 2:
                return RULES_TITLE;
            case 3:
                return ROSTER_TITLE;
            default:
                return null;
        }
    }
}