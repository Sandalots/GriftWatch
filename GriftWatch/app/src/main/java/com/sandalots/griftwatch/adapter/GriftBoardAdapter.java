package com.sandalots.griftwatch.adapter;

// Android Imports
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.griftwatch.R;
import com.sandalots.griftwatch.data.Grifter;

import java.util.List;

/** @noinspection ALL*/

// Class to setup the adapter of the GriftBoard frgments recycler view
public class GriftBoardAdapter extends RecyclerView.Adapter<GriftBoardAdapter.GriftBoardViewHolder> {
    // Define a list of grifters
    private List<Grifter> grifterList;

    // Constructor takes the list of grifters in
    public GriftBoardAdapter(List<Grifter> grifterList) {
        this.grifterList = grifterList;
    }

    // onCreateViewHolder method for the GriftBoard
    @NonNull
    @Override
    public GriftBoardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.grift_board_items, parent, false);

        // return view holder with the itemView
        return new GriftBoardViewHolder(itemView);
    }

    // onBindViewHolder for the GriftBoardAdapter class
    @Override
    public void onBindViewHolder(GriftBoardViewHolder holder, int position) {
        // Define a grifter object
        Grifter grifter = grifterList.get(position);

        // get String representation of grifter name
        String grifterName = grifter.getName();

        // check if grifter is in first place
        if (position == 0) {
            // add a golden crown emoji to the grifter name
            grifterName = "👑 " + grifterName;
        }

        // check if grifter is in second place
        if (position == 1) {
            // add a silver crown emoji to the grifter name
            grifterName = "🥈 " + grifterName;
        }

        // check if grifter is in third place
        if (position == 2) {
            // add a bronze crown emoji to the grifter name
            grifterName = "🥉 " + grifterName;
        }

        // Setup the holder view components of said obtained grifter
        holder.name.setText(grifterName); // use stylised position name
        holder.griftrank.setText("Grift Rank: " + String.valueOf(grifter.getGriftRank()));
    }

    // get the total item count fo the grifterList
    @Override
    public int getItemCount() {
        return grifterList.size();
    }

    // update grifterList data method
    public void updateData(List<Grifter> grifters) {
        // get the passed grifters list and set it as the new data of the grifters list
        this.grifterList = grifters;

        // tell the grift board adapter that the data has changed
        notifyDataSetChanged();
    }

    /** @noinspection InnerClassMayBeStatic*/
    // Inner class to define the View Holder for the GriftBoard Adapter
    public static class GriftBoardViewHolder extends RecyclerView.ViewHolder {
        // Get the components to be used in the GriftBoard
        public final TextView name;
        public final TextView griftrank;

        // Constructor for the view, gets the components by resource id
        public GriftBoardViewHolder(View view) {
            super(view);

            // Get UI components
            name = view.findViewById(R.id.name); // get the name TextView
            griftrank = view.findViewById(R.id.griftRank); // get the griftrank TextView
        }
    }
}