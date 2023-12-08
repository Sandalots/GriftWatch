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
import com.sandalots.griftwatch.adapter.GriftBoardAdapter;
import com.sandalots.griftwatch.data.Grifter;
import com.sandalots.griftwatch.data.GrifterRepository;

import java.util.List;

// Class to handle the GriftBoard fragment that houses the ranked list of grifters
public class GriftBoardFragment extends Fragment {
    // Constructor method for the Grift Board Fragment
    public GriftBoardFragment() {
        // Required empty public constructor
    }

    // onCreate method for the Grift Board fragment
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // onCreateView method for the Grift Board Fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_grift_board, container, false);
    }

    // onViewCreated() method goes here
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // get griftBoardList recyclerView
        RecyclerView griftBoardList = view.findViewById(R.id.griftBoardList);

        // get GrifterRepository
        GrifterRepository grifterRepository = new GrifterRepository(requireContext());

        // define the list of grifters
        List<Grifter> grifterList = grifterRepository.getAllGrifters();

        // sort the grifters by grift rank
        // method to compare two grifters by grifter rank
        grifterList.sort((g1, g2) -> {
            // return the comparison
            return Integer.compare(g2.getGriftRank(), g1.getGriftRank());
        });

        // set adapter and layout manager
        griftBoardList.setAdapter(new GriftBoardAdapter(grifterList));
        griftBoardList.setLayoutManager(new LinearLayoutManager(requireContext()));
    }
}