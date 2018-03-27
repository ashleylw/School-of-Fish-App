package edu.usc.cs401.schooloffish.Controller;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import edu.usc.cs401.schooloffish.R;

public class MainActivity extends AppCompatActivity {

    private TextView mOutputText;
    private FloatingActionButton mNewGameButton;

    private FirebaseAuth mAuth;
    private static final int GOOGLE_SIGN_IN = 1;

    private static final String DEFAULT_WED_CLIENT_ID =
            "512466518466-rlro07d9d7oi9ujtieqadiolrq4mp2mv.apps.googleusercontent.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final TextView noGamesText = (TextView) findViewById(R.id.noGamesText);
        mOutputText = (TextView) findViewById(R.id.mOutputText);
        mNewGameButton = (FloatingActionButton) findViewById(R.id.newGameButton);

        mNewGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNewGameButton.setEnabled(false);
                mOutputText.setText("");
                //getResultsFromApi();
                mNewGameButton.setEnabled(true);

                mAuth = FirebaseAuth.getInstance();
                FirebaseUser currentUser = mAuth.getCurrentUser();

                if (currentUser != null) {
                    Intent intent = new Intent(MainActivity.this, CreateGame.class);
                    startActivityForResult(intent, 0);
                } else {
                    Intent intent = new Intent(MainActivity.this, GoogleSignInActivity.class);
                    startActivityForResult(intent, GOOGLE_SIGN_IN);
                }

                noGamesText.setText("");
            }
        });

        //setContentView(activityLayout);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == GOOGLE_SIGN_IN) {
            mAuth = FirebaseAuth.getInstance();
            FirebaseUser currentUser = mAuth.getCurrentUser();
            if (currentUser != null) {
                Intent intent = new Intent(MainActivity.this, CreateGame.class);
                startActivityForResult(intent, 0);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /*// Returns the fragment to display for that page
    @Override
    public Fragment getItem(int position) {
        return GameList.newInstance();
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}