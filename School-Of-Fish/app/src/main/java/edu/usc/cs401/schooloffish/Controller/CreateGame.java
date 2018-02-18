package edu.usc.cs401.schooloffish.Controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import java.util.ArrayList;

import edu.usc.cs401.schooloffish.R;

/**
 * Created by Ashley Walker on 2/17/2018.
 */

public class CreateGame extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_game);

        EditText gameNameEntry = (EditText)findViewById(R.id.game_name_entry);
        EditText bigFishNameEntry = (EditText)findViewById(R.id.big_fish_name_entry);

        /**
         * TODO:
         * - Set limits for the numberPickers
         * - Set OnClickEventListener for the CREATE button
          */

    }

}
