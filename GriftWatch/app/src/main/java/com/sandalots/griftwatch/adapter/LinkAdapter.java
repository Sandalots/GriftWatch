package com.sandalots.griftwatch.adapter;

// Android Imports
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.griftwatch.R;
import java.util.List;

/** @noinspection ALL*/
// Class to define the adapter to be used for the url list in the Resources fragment
public class LinkAdapter extends RecyclerView.Adapter<LinkAdapter.UrlViewHolder> {
    // Define a string list of url's
    private final List<String> urlList;

    // Constructor for the LinkAdapter, takes the urlList
    public LinkAdapter(List<String> urlList) {
        this.urlList = urlList;
    }

    // onCreateViewHolder method for the LinkAdapter class
    @NonNull
    @Override
    public UrlViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.url_item, parent, false);

        // return view holder with the itemView
        return new UrlViewHolder(itemView);
    }

    // onBindViewHolder for the LinkAdapter class
    @Override
    public void onBindViewHolder(UrlViewHolder holder, int position) {
        // get the current url
        final String url = urlList.get(position);

        // create a bullet point before the link and show the link
        holder.url.setText("\u2022 " + url);
        // set the text color to use my custom color input mellow
        holder.url.setTextColor(holder.url.getResources().getColor(R.color.inputMellow));

        // Setup an onclick handler for clicking the links (if the user clicks a link, open the web-browser and surf the given link destination)
        holder.url.setOnClickListener(v -> {
            // Create a new intent
            Intent intent = new Intent(Intent.ACTION_VIEW);
            // parse the url data
            intent.setData(Uri.parse(url));

            // Open the link in the default web browser
            v.getContext().startActivity(intent);
        });
    }

    // Method to get item count in url list
    @Override
    public int getItemCount() {
        return urlList.size();
    }

    /** @noinspection InnerClassMayBeStatic*/
    // Inner class to define the UrlViewHolder of the urlList
    public static class UrlViewHolder extends RecyclerView.ViewHolder {
        // Get the url TextView
        public final TextView url;

        // Constructor for the UrlViewHolder
        public UrlViewHolder(View view) {
            super(view);

            // get the url by resource id
            url = view.findViewById(R.id.url);
        }
    }
}