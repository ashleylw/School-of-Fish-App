package edu.usc.cs401.schooloffish.Controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.Spreadsheet;

import edu.usc.cs401.schooloffish.Model.AllGames;
import edu.usc.cs401.schooloffish.Model.BigFish;
import edu.usc.cs401.schooloffish.Model.Game;
import edu.usc.cs401.schooloffish.R;

/**
 * Created by Ashley Walker on 2/17/2018.
 */

public class CreateGame extends AppCompatActivity {

    private AllGames allGames = AllGames.getInstance();

    private Spreadsheet newGameSheet;
    private Sheets sheetsService;

    private EditText gameNameEntry;
    private EditText bigFishNameEntry;
    private NumberPicker numRounds;
    private NumberPicker roundLengthMins;
    private NumberPicker roundLengthSecs;
    private NumberPicker preroundLengthMins;
    private NumberPicker preroundLengthSecs;

    private Button createGameButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_game);

        gameNameEntry = (EditText) findViewById(R.id.game_name_entry);
        bigFishNameEntry = (EditText) findViewById(R.id.big_fish_name_entry);
        createGameButton = (Button) findViewById(R.id.create_game_button);

        numRounds = (NumberPicker) findViewById(R.id.num_rounds_picker);
        roundLengthMins = (NumberPicker) findViewById(R.id.round_length_mins);
        roundLengthSecs = (NumberPicker) findViewById(R.id.round_length_secs);
        preroundLengthMins = (NumberPicker) findViewById(R.id.preround_length_mins);
        preroundLengthSecs = (NumberPicker) findViewById(R.id.preround_length_secs);

        createGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!gameNameEntry.getText().equals("") &&
                        !bigFishNameEntry.getText().equals("")) {

                    // disable button briefly
                    createGameButton.setEnabled(false);

                    // create and initialize new Game object
                    Game newGame = new Game(gameNameEntry.getText().toString(), new BigFish(bigFishNameEntry.getText().toString()),
                            numRounds.getValue(), newGameSheet);

                    // add Game to list and return to list of games screen for now
                    allGames.addGame(newGame);
                    createGameButton.setEnabled(true);
                    finish();

                }
            }
        });

        /**
         * TODO:
         * - Set limits for the numberPickers
         * - Set OnClickEventListener for the CREATE button
         */
        numRounds.setMinValue(2);
        numRounds.setMaxValue(6);

        roundLengthMins.setMinValue(1);
        roundLengthMins.setMaxValue(5);

        roundLengthSecs.setMinValue(0);
        roundLengthSecs.setMaxValue(59);

        preroundLengthMins.setMinValue(1);
        preroundLengthMins.setMaxValue(5);

        preroundLengthSecs.setMinValue(0);
        preroundLengthSecs.setMaxValue(59);

    }
}
