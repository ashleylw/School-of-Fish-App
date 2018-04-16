package edu.usc.cs401.schooloffish.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import edu.usc.cs401.schooloffish.Model.Game;
import edu.usc.cs401.schooloffish.R;

/**
 * Created by Ashley Walker on 3/22/2018.
 */

public class BigFishMain extends AppCompatActivity {

    private Game game;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.big_fish_main);

        Intent intent = getIntent();
        game = (Game) intent.getExtras().getSerializable("Game");
    }


}
