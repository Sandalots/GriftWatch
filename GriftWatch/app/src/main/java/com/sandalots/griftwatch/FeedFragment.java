package com.sandalots.griftwatch;

// Android Imports
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.griftwatch.R;
import com.sandalots.griftwatch.adapter.FeedAdapter;
import com.sandalots.griftwatch.data.Grifter;
import com.sandalots.griftwatch.data.GrifterRepository;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

// Fragment to house the feed contents (Grift entries, create new grifter button and rank and derank button functionality), implements onClickListener for the create new grifter button.
public class FeedFragment extends Fragment implements View.OnClickListener {
    // set up a tag for this fragment to help in debugging
    private static final String TAG = "FeedFragment";

    // Get the Repository for handling Grifter objects
    private GrifterRepository grifterRepository;

    // Get the context for this feed fragment
    private final Context context = getContext();
    // constructor for the feed fragment

    // Constructor for the Feed Fragment
    public FeedFragment() {
        // Required empty public constructor
    }

    // onCreate method for the Feed Fragment
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // onCreateView method for the Feed Fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed, container, false);
    }

    // onViewCreated() method goes here
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Get the createNewButton
        Button createNewButton = view.findViewById(R.id.createNewBtn);
        // set an onClickListener to the createNewButton
        createNewButton.setOnClickListener(this);

        // create a new list of grifters
        List<Grifter> grifterList = new ArrayList<>();

        // get the feedList recyclerview
        RecyclerView feedList = view.findViewById(R.id.feedList);
        // get the feedAdapter adapter
        FeedAdapter feedAdapter = new FeedAdapter(grifterList, requireActivity());

        // set the adapter of the feedList to use the feedAdapter
        feedList.setAdapter(feedAdapter);
        // setup the layout manager of the feed list
        feedList.setLayoutManager(new LinearLayoutManager(getContext()));

        // Create a helpful divider between grifter feed entries
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(feedList.getContext(), new LinearLayoutManager(context).getOrientation());
        // set color of divider background
        Objects.requireNonNull(dividerItemDecoration.getDrawable()).setTint(getResources().getColor(R.color.inputMellow));
        // add the divider to the feed list
        feedList.addItemDecoration(dividerItemDecoration);

        // get the grifter repo
        grifterRepository = new GrifterRepository(getContext());
        feedAdapter.updateData(grifterRepository.getAllGrifters());

        // if the grifter repo is empty
        if (grifterRepository.getAllGrifters().isEmpty()) {
            // download the feed from Firebase
            downloadFeed(feedAdapter);
        }

        // Upload the database to Firebase
        uploadDatabaseToFirebase();

        // dodgy fix for race condition between uploading the feed and downloading it
        try {
            // sleep for 0.5 second
            Thread.sleep(500);

        // catch any interrupted exceptions
        } catch (InterruptedException e) {
            // print the error stack trace to Logcat
            e.printStackTrace();
        }

        // Download the feed from Firebase
        downloadFeed(feedAdapter);
    }

    // Method to download the feed from the Firebase realtime database
    private void downloadFeed(FeedAdapter feedAdapter) {
        // define the url, the direct link to the grifter.json data hosted on Firebase
        String url = "https://griftwatchwebservices-default-rtdb.europe-west1.firebasedatabase.app/grifters.json";

        // Define a new stringRequest to the API
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                // if the string request gets a response
                response -> {
                    // try and upload a grifter to the Firebase database
                    try {
                        // Convert the response string into a JSONObject
                        JSONObject rootObj = new JSONObject(response);

                        // Iterate over the keys of the root JSONObject
                        Iterator<String> keys = rootObj.keys();
                        // for every grifter
                        while (keys.hasNext()) {
                            // define the next key
                            String key = keys.next();

                            // Get the grifter JSONObject associated with the key
                            JSONObject grifterObj = rootObj.getJSONObject(key);

                            // Extract the grifter data
                            int id = grifterObj.getInt("id"); // get the grifter id
                            String name = grifterObj.getString("name"); // get the grifter name
                            String grifts = grifterObj.getString("grifts"); // get the grifter grifts
                            String country = grifterObj.getString("country"); // get the grifter country
                            int griftRank = grifterObj.getInt("griftRank"); // get the grifter grift rank

                            // Create a Grifter object from the extracted data and add the grifter to the grifter repository
                            Grifter grifter = new Grifter(id, name, grifts, country, griftRank);
                            grifterRepository.addGrifter(grifter);
                        }

                        // Get the list of grifters from the repository and update the RecyclerView
                        List<Grifter> grifters = grifterRepository.getAllGrifters();

                        // update the data in the feed adapter
                        feedAdapter.updateData(grifters);

                        // Upload the database to Firebase
                        uploadDatabaseToFirebase();

                    // catch JSON Exceptions
                    } catch (JSONException e) {
                        // Print the error to Logcat
                        e.printStackTrace();
                    }

                // If an error arises during the stringRequest to firebase
                }, error -> {
                    // Send an error downloading grifters toast message
                    Toast.makeText(getContext(), "Error downloading feed", Toast.LENGTH_SHORT).show();
                    // Also log the error
                    Log.e(TAG, error.toString());
                });

        // Define a queue
        RequestQueue queue = Volley.newRequestQueue(requireContext());
        // Add the string request to the queue
        queue.add(stringRequest);
    }

    /** @noinspection MismatchedQueryAndUpdateOfCollection*/ // create a set of strings to keep track of uploaded grifters
    private final Set<String> uploadedGrifterIds = new HashSet<>();

    // Method to upload the grifter repo to Firebase
    private void uploadDatabaseToFirebase() {
        // Get the list of grifters from the repository
        List<Grifter> grifters = grifterRepository.getAllGrifters();

        // Create a RequestQueue
        RequestQueue queue = Volley.newRequestQueue(requireContext());

        // Process each grifter in the list
        for (Grifter grifter : grifters) {
            // Convert the grifter to a JSONObject
            JSONObject jsonObject = new JSONObject();

            // try and put the grifter data into the json object
            try {
                jsonObject.put("id", grifter.getId()); // try and put grifter id
                jsonObject.put("name", grifter.getName()); // try and put grifter name
                jsonObject.put("grifts", grifter.getGrifts()); // try and put grifter grifts
                jsonObject.put("country", grifter.getCountry()); // try and put grifter country
                jsonObject.put("griftRank", grifter.getGriftRank()); // try and put grifter grift rank

            // catch any JSON Exceptions
            } catch (JSONException e) {
                e.printStackTrace(); // output the stack trace to Logcat
            }

            // Define the URL for the Firebase Realtime Database
            String url = "https://griftwatchwebservices-default-rtdb.europe-west1.firebasedatabase.app/grifters/" + grifter.getId() + ".json";

            // Create a StringRequest for the PUT request
            StringRequest stringRequest = new StringRequest(Request.Method.PUT, url,
                    // if the stringRequest gets a response
                    response -> uploadedGrifterIds.add(String.valueOf(grifter.getId())),

                    // if the stringRequest gets an error
                    error -> Toast.makeText(getContext(), "Error uploading grifter " + grifter.getId(), Toast.LENGTH_SHORT).show()) {

                // method to get the body of the json request
                @Override
                public byte[] getBody() {
                    // return the desired json object
                    return jsonObject.toString().getBytes(StandardCharsets.UTF_8);
                }

                // method to get the body content type of the json request
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }
            };

            // Add the request to the RequestQueue
            queue.add(stringRequest);
        }
    }

    // onClick() method goes here
    @Override
    public void onClick(View view) {
        // get the id of the button clicked
        int id = view.getId();

        // check which button was clicked
        if (id == R.id.createNewBtn) {
            // change fragment to the CreateGriferFragment
            Fragment fragment = new CreateGrifterFragment();
            // define a new frag manager
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            // attempt a switch to the create new grifter form
            fragmentManager.beginTransaction()
                    .replace(R.id.fragmentView, fragment)
                    .addToBackStack(null)
                    .commit();
        }
    }
}