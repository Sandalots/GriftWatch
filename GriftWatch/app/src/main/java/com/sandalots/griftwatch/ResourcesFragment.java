package com.sandalots.griftwatch;

// Android Imports
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.griftwatch.R;
import com.sandalots.griftwatch.adapter.LinkAdapter;

import java.util.ArrayList;
import java.util.List;

// Class to handle the resource fragment
public class ResourcesFragment extends Fragment {
    // constructor for the Resources Fragment
    public ResourcesFragment() {
        // Required empty public constructor
    }

    // onCreate method for the Resources fragment
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // onCreateView method for the Resources fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_resources, container, false);
    }

    // on ViewCreated() method for the Resources fragment
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // create an empty list of strings for future URL populating
        List<String> urls = new ArrayList<>();

        // Add several helpful resources to aid in the betterment of Grifter ranking and creation
        urls.add("https://www.criminaldefenselawyer.com/resources/criminal-defense/white-collar-crime/how-to-detect-a-grifter");
        urls.add("https://portal.ct.gov/DOB/Consumer/Consumer-Education/How-to-Spot-a-Con-Artist");
        urls.add("https://www.netflix.com/tudum/articles/how-good-are-you-at-spotting-a-con");
        urls.add("https://www.spring.org.uk/2022/07/grifters-con-artists-7.php");
        urls.add("https://www.drphil.com/advice/how-to-spot-a-con-artist");
        urls.add("https://www.businessinsider.com/7-tell-tale-signs-of-a-con-artist-2016-3?r=US&IR=T");
        urls.add("http://archive.ncpc.org/resources/files/pdf/fraud/Use-Common-Sense-Brochure.pdf");
        urls.add("https://nslawla.com/life-events/con-artists-how-to-spot-and-stop-them/");
        urls.add("https://en.wikipedia.org/wiki/Confidence_trick");
        urls.add("https://www.nidirect.gov.uk/articles/how-spot-scam");
        urls.add("https://www.ageuk.org.uk/information-advice/money-legal/scams-fraud/");

        // get the resources list recycler view
        RecyclerView recyclerView = view.findViewById(R.id.resourcesList);

        // define the LinkAdapter adapter class and pass it the urls created above to populate the recycler view with all the resources link
        LinkAdapter adapter = new LinkAdapter(urls);

        // set the adapter of the recycler view to use the adapter LinkAdapter defined above
        recyclerView.setAdapter(adapter);
        // set the layout manager of the recycler view
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
    }
}