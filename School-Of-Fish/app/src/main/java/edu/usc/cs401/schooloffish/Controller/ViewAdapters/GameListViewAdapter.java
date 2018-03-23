package edu.usc.cs401.schooloffish.Controller.ViewAdapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import edu.usc.cs401.schooloffish.Model.AllGames;
import edu.usc.cs401.schooloffish.Model.Game;
import edu.usc.cs401.schooloffish.R;

/**
 * Created by Ashley Walker on 2/17/2018.
 */

public class GameListViewAdapter extends ArrayAdapter<Game> {

    private AllGames allGames = AllGames.getInstance();
    private View view;
    private Game game;

    public GameListViewAdapter(Activity activity, int id){
        super(activity, 0);
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        view = convertView;
        // Get the data item for this position
        game = allGames.getList().get(position);

        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder = null; // view lookup cache stored in tag
        //create new row view if null
        if (view == null) {
            // If there's no view to re-use, inflate a brand new view for row
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.game_list_item, parent, false);
            viewHolder = new ViewHolder(view);
            // Cache the viewHolder object inside the fresh view
            view.setTag(viewHolder);
        } else {
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (ViewHolder) view.getTag();
        }
        // Populate the data from the data object via the viewHolder object
        // into the template view.
        viewHolder.gameName.setText(game.getName());
        viewHolder.bigFishName.setText("Big Fish: " + game.getBigFish().getName());

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
