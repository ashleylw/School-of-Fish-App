package edu.usc.cs401.schooloffish.Controller;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import edu.usc.cs401.schooloffish.R;

/**
 * Created by Ashley Walker on 3/22/2018.
 */

public class BigFishMain extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.big_fish_main);

        tabLayout = (TabLayout) findViewById(R.id.tabLayoutBF);
        viewPager = (ViewPager) findViewById(R.id.viewPagerBF);

        final GamePagerAdapter gamePagerAdapter =
                new GamePagerAdapter(getSupportFragmentManager(), true);
        viewPager.setAdapter(gamePagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
    }


}
