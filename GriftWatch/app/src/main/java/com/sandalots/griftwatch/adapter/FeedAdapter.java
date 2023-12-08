package com.sandalots.griftwatch.adapter;

// Android Imports
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.griftwatch.R;
import com.sandalots.griftwatch.data.Grifter;
import com.sandalots.griftwatch.data.GrifterRepository;

import java.util.List;

/** @noinspection ALL*/

// Class to define the Adapter to be used in the Feed fragment's feedList recyclerView
public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.GrifterViewHolder> {
    // Define a list of grifters entitled grifterList
    private List<Grifter> grifterList;

    // Define a grifter repository
    private GrifterRepository grifterRepository;

    // Constructor for the FeedAdapter, the grifterList is passed into it, also gets the context
    public FeedAdapter(List<Grifter> grifterList, Context context) {
        this.grifterList = grifterList;

        // Get the repo
        grifterRepository = new GrifterRepository(context.getApplicationContext());
    }

    // onCreateViewHolder for the FeedAdapter
    @NonNull
    @Override
    public GrifterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.grifter_item, parent, false);

        // return view holder with the itemView
        return new GrifterViewHolder(itemView);
    }

    //onBindViewHolder for the FeedAdapter class
    @Override
    public void onBindViewHolder(GrifterViewHolder holder, int position) {
        // Define a grifter
        Grifter grifter = grifterList.get(position);

        // Setup the holder components
        holder.name.setText(grifter.getName()); // set data of the name to be the grifters objects name
        holder.grifts.setText("Grifts: " + grifter.getGrifts()); // set data of the grifts to be the grifters object grifts
        holder.country.setText("Country of Origin: " + grifter.getCountry()); // set data of the country to be the grifters object country
        holder.griftRank.setText("Grift Rank: " + String.valueOf(grifter.getGriftRank())); // set the data of the griftrank to include a little heading context and use the grifters objects griftrank propertie

        // On click method for the rank button
        holder.rank.setOnClickListener(v -> {
            // increase the Grift Rank
            grifter.incrementGriftRank(); // griftRank += 1
            grifterRepository.updateGriftRank(grifter.getId(), grifter.getGriftRank()); // tell the repo to update the rank

            // notify the adapter that the data has changed
            notifyDataSetChanged();
        });

        // On click method for the derank button
        holder.derank.setOnClickListener(v -> {
            // decrease the Grift Rank
            grifter.decrementGriftRank(); // griftRank -= 1
            grifterRepository.updateGriftRank(grifter.getId(), grifter.getGriftRank()); // tell the repo to update the rank

            // notify the adapter that the data has changed
            notifyDataSetChanged();
        });

        // long press event listener for a grifter entry
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            // define the onLongClick event
            @Override
            public boolean onLongClick(View v) {
                // define the url with the grifters name as the google search request
                String url = "https://www.google.com/search?q=" + Uri.encode(grifter.getName());

                // Pass the URL and use an intent to open it in the default web browser
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                v.getContext().startActivity(intent);

                // Exit the method
                return true;
            }
        });
    }

    // Method to get item count of the list
    @Override
    public int getItemCount() {
        return grifterList.size();
    }

    // Method to update the data of the recycler view
    public void updateData(List<Grifter> grifters) {
        // set the passed list to be the new list for the feed
        this.grifterList = grifters;

        // tell the adapter that the data has changed
        notifyDataSetChanged();
    }

    /** @noinspection InnerClassMayBeStatic*/
    // Inner class to get the holder contents of the feedList recycler View.
    public static class GrifterViewHolder extends RecyclerView.ViewHolder {
        // Get the components to be used in the grifter entries set to be shown in the feedList recycler View.
        public final TextView name;
        public final TextView grifts;
        public final TextView country;
        public final TextView griftRank;

        // Define the buttons (rank and derank)
        public final Button rank;
        public final Button derank;

        // Constructor for the inner class GrifterView Holder
        public GrifterViewHolder(View view) {
            super(view);

            // get the components from their resource names
            name = view.findViewById(R.id.name); // get the name TextView
            grifts = view.findViewById(R.id.grifts); // get the grifts TextView
            country = view.findViewById(R.id.country); // get the Country TextView
            griftRank = view.findViewById(R.id.griftRank); // get the griftRank TextView
            rank = view.findViewById(R.id.rank); // get the rank Button
            derank = view.findViewById(R.id.derank); // get the de rank button
        }
    }
}