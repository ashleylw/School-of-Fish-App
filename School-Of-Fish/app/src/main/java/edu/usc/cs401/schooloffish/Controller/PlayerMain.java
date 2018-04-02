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

    private String gameID;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_main);

        Intent intent = getIntent();
        gameID = intent.getExtras().getString("GameID");
    }

}
