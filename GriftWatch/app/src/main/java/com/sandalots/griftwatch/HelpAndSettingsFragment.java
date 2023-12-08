package com.sandalots.griftwatch;

// Android Imports
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import com.example.griftwatch.R;
import com.sandalots.griftwatch.data.GrifterRepository;

// Class to handle the Help and Settings fragment, that houses some settings and the main tutorial help text.
public class HelpAndSettingsFragment extends Fragment {
    // constructor for the Help & Settings fragment
    public HelpAndSettingsFragment() {
        // Required empty public constructor
    }

    // method to reset the application from a switch toggle.
    public void resetApplication() {
        // get the grifter repo
        GrifterRepository grifterRepository = new GrifterRepository(requireActivity().getApplication());
        // reset the grifter repo (delete entries)
        grifterRepository.reset();

        // Create a new intent to go home
        Intent intent = new Intent(getActivity(), Home.class);
        // Clear the flag
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // start the intent
        startActivity(intent);
        // exit
        Runtime.getRuntime().exit(0);
    }

    // method to request notification permissions from the user.
    public void requestNotificationPermissions() {
        // get the alt switch (for notification access helper request)
        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch altSwitch = requireView().findViewById(R.id.altSwitch);
        // if the alt switch is checked (turned on)
        if (altSwitch.isChecked()) {

            // If notifications are not enabled
            if (!NotificationManagerCompat.from(requireActivity()).areNotificationsEnabled()) {

                // Notifications are not enabled. Show a dialog to the user.
                new AlertDialog.Builder(requireActivity())
                        // set title for notification alert
                        .setTitle("Enable Notifications for GriftWatch")
                        // display the message that notifications are disabled
                        .setMessage("Notifications are currently disabled for GriftWatch. Please enable them in the system settings.")
                        // set the open settings button to open permissions system settings
                        .setPositiveButton("Open Settings", (dialog, which) -> {
                            // Intent to open the system settings
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", requireActivity().getPackageName(), null);
                            intent.setData(uri);
                            startActivity(intent);
                        })

                        // Cancel button for the dialog
                        .setNegativeButton("Cancel", null)
                        .show();
            }
        }
    }

    // onCreate method for the Help & Settings fragment
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // onCreateView method for the Help & Settings fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_helpandsettings, container, false);
    }

    // onView created method for the Help & Settings fragment
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Get resetSwitch Switch
        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch resetSwitch = view.findViewById(R.id.resetSwitch);

        // Set onClickListener for resetSwitch
        resetSwitch.setOnClickListener(v -> resetApplication());

        // set an onclick listener for the alt switch
        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch altSwitch = view.findViewById(R.id.altSwitch);
        altSwitch.setOnClickListener(v -> requestNotificationPermissions());

        // get appBuildButton
        View appBuildButton = view.findViewById(R.id.appBuildButton);

        // set onClickListener for appBuildButton
        appBuildButton.setOnClickListener(v -> {
            // create new AlertDialog
            new AlertDialog.Builder(requireActivity())
                    // Display Build Number title
                    .setTitle("Application Build Number")
                    // output the build number
                    .setMessage("GriftWatch v1.0.0 Codename: \"The Griftiest\"")
                    // setup the cancel button
                    .setPositiveButton("Close", null)
                    // show the alertDialog
                    .show();
        });
    }
}