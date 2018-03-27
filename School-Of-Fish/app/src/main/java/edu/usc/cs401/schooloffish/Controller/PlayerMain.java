package edu.usc.cs401.schooloffish.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import edu.usc.cs401.schooloffish.R;

/**
 * Created by Ashley Walker on 3/24/2018.
 */

public class PlayerMain extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    private String gameID;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_main);

        tabLayout = (TabLayout) findViewById(R.id.tabLayoutPL);
        viewPager = (ViewPager) findViewById(R.id.viewPagerPL);

        Intent intent = getIntent();
        gameID = intent.getExtras().getString("GameID");

        final GamePagerAdapter gamePagerAdapter =
                new GamePagerAdapter(getSupportFragmentManager(), true, gameID);
        viewPager.setAdapter(gamePagerAdapter);

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
    }

}
