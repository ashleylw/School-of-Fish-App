package edu.usc.cs401.schooloffish.Controller.ViewAdapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import edu.usc.cs401.schooloffish.Model.Player;
import edu.usc.cs401.schooloffish.R;

/**
 * Created by Ashley Walker on 3/22/2018.
 */

public class PlayerRosterViewAdapter extends ArrayAdapter {

    private Context mContext;
    private View view;
    private Player player;
    private List<Player> players;
    private int id;

    public PlayerRosterViewAdapter(Context context, int id, List<Player> players){
        super(context, id, players);
        this.mContext = context;
        this.id = id;
        this.players = players;
    }

    /**
     * Update the data set and reload the view.
     * @param players The new list of profiles to display.
     */
    public void setPlayers(@NonNull List<Player> players) {
        this.players.clear();
        this.players.addAll(players);
        this.notifyDataSetChanged();
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        view = convertView;

        player = players.get(position);

        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder = null; // view lookup cache stored in tag

        //create new row view if null
        if (view == null) {
            LayoutInflater vi = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = vi.inflate(id, null);
            viewHolder = new PlayerRosterViewAdapter.ViewHolder(view);

            // Cache the viewHolder object inside the fresh view
            view.setTag(viewHolder);
        } else {
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (ViewHolder) view.getTag();
        }

        // Populate the data from the data object via the viewHolder object
        // into the template view.
        viewHolder.playerName.setText(player.getName());
        viewHolder.playerRole.setText(player.getRole().getName());

        // Return the completed view to render on screen
        return view;
    }

    // View lookup cache that populates the listview
    public class ViewHolder {
        TextView playerName;
        TextView playerRole;

        public ViewHolder (View v){
            this.playerName = (TextView)v.findViewById(R.id.player_name);
            this.playerRole = (TextView)v.findViewById(R.id.player_role);
        }
    }

}
