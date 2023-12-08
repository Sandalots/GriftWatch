package com.sandalots.griftwatch;

// Android Imports
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.griftwatch.R;
import com.google.android.material.internal.ViewUtils;
import com.sandalots.griftwatch.data.Grifter;
import com.sandalots.griftwatch.data.GrifterRepository;

// Class to handle the Create Grifter Fragment, implements View.OnClickListener for the two buttons (cancel and add)
public class CreateGrifterFragment extends Fragment implements View.OnClickListener {
    // constructor for the fragment
    public CreateGrifterFragment() {
        // Required empty public constructor
    }

    // onCreate method
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // onCreateView method
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_grifter, container, false);
    }

    // onViewCreated() method goes here
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // get cancelButton
        Button cancelButton = view.findViewById(R.id.cancelButton);
        // create an onClick event listener for cancelButton
        cancelButton.setOnClickListener(this);

        // get addtoButton
        Button addToButton = view.findViewById(R.id.addtoButton);
        // create an onClick event listener for addtoButton
        addToButton.setOnClickListener(this);
    }

    // method to get inputs from the EditTexts (name, grifts and country)
    public void getInputs() {
        // get name user input
        EditText name = requireView().findViewById(R.id.nameInput);
        
        // get grifts user input
        EditText grifts = requireView().findViewById(R.id.griftsInput);

        // get country user input
        EditText country = requireView().findViewById(R.id.countryInput);
        
        // check if any of the inputs are empty
        if (name.getText().toString().isEmpty() || grifts.getText().toString().isEmpty() || country.getText().toString().isEmpty()) {
            // create a please fill in all forms toast message
            Toast toast = Toast.makeText(requireContext(), "Please fill in all fields.", Toast.LENGTH_SHORT);
            // show the toast message
            toast.show();

            // reload the fragment
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentView, new CreateGrifterFragment()).commit();

            // exit the method
            return;
        }

        // Define a grifter with the user inputs (set grift rank initially to 0 on new grifters)
        Grifter grifter = new Grifter(Grifter.generateId(), name.getText().toString(), grifts.getText().toString(), country.getText().toString(), 0);

        // define the repo
        GrifterRepository grifterRepository = new GrifterRepository(requireActivity().getApplication());

        // add the new grifter to the repo
        grifterRepository.addGrifter(grifter);

        // define grifter added toast
        Toast toast = Toast.makeText(requireContext(), "Grifter " + grifter.getName() + " added.", Toast.LENGTH_SHORT);
        // show the toast message
        toast.show();

        // go to feed fragment
        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentView, new FeedFragment()).commit();
    }

    @SuppressLint("RestrictedApi")
    @Override
    // On Click listener for the two buttons in the create a new grifter fragment
    public void onClick(View view) {
        // get the id of the button clicked
        int id = view.getId();

        // check if cancel button was clicked
        if (id == R.id.cancelButton) {
            // close the keyboard
            ViewUtils.hideKeyboard(view);

            // Cancel Toast message
            Toast toast = Toast.makeText(requireContext(), "Creation of a New Grifter cancelled", Toast.LENGTH_SHORT);
            // output cancel toast message back to the user
            toast.show();

            // go to the feed fragment
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentView, new FeedFragment()).commit();
        }

        // check if add to button has been clicked
        if (id == R.id.addtoButton) {
            // close the keyboard
            ViewUtils.hideKeyboard(view);

            // Get the user inputs and save the grifter
            getInputs();
        }
    }
}