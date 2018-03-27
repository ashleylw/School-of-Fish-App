package edu.usc.cs401.schooloffish.Controller.ViewAdapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.usc.cs401.schooloffish.Model.Game;
import edu.usc.cs401.schooloffish.R;

import static android.content.ContentValues.TAG;

/**
 * Created by Ashley Walker on 2/17/2018.
 */

public class GameListViewAdapter extends ArrayAdapter {

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    private Context mContext;
    private int id;
    List<Game> allGames;
    private View view;
    private ViewGroup parent;
    private Game game;

    public GameListViewAdapter(Context context, int id, List<Game> allGames){
        super(context, id, allGames);
        this.mContext = context;
        this.id = id;
        this.allGames = allGames;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        view = convertView;
        this.parent = parent;

        ViewHolder viewHolder = null;
        if(view == null){
            LayoutInflater vi = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = vi.inflate(id, null);
            viewHolder = new ViewHolder(view);

            System.err.println("***\n\n\nHERE\n\n\n*******");

            // Cache the viewHolder object inside the fresh view
            view.setTag(viewHolder);
        } else {
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (ViewHolder) view.getTag();
        }

        // Get the data item for this position
        game = allGames.get(position);

        // Populate the data from the data object via the viewHolder object
        // into the template view.
        if (game != null) {
            viewHolder.gameName.setText(game.getName());
            if (game.getBigFish() != null) {
                viewHolder.bigFishName.setText("Big Fish: " + game.getBigFish().getName());
            }
        }

        // Return the completed view to render on screen
        return view;
    }

    // View lookup cache that populates the listview
    public class ViewHolder {
        TextView gameName;
        TextView bigFishName;

        public ViewHolder (View v){
            this.gameName = (TextView)v.findViewById(R.id.game_name);
            this.bigFishName = (TextView)v.findViewById(R.id.big_fish_name);
        }
    }

}
