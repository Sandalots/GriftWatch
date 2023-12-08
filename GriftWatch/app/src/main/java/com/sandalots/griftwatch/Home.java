package com.sandalots.griftwatch; // The  Home Activity that houses all the other fragments for GriftWatch.

// Android imports
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.griftwatch.R;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

// The Home Activity of GriftWatch. Where all the other 'pages' are displayed using fragments. Initially the feed fragment is displayed on launch. Sets up the navigation drawer also.
public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    // objects to define the layout and actionbar of the navigation drawer.
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;

    // onCreate method for the Home Activity class
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Turn off system dark mode.
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        // save the instance state of the Home activity.
        super.onCreate(savedInstanceState);

        // set the view to use the layout defined in the layout resource file.
        setContentView(R.layout.activity_home);

        // Modify action bar styling.
        Objects.requireNonNull(getSupportActionBar()).setTitle(Html.fromHtml("<font color='#FFDF8C'>GriftWatch</font>"));

        // Some custom options for the navigation drawer.
        // get the drawerlayout
        drawerLayout = findViewById(R.id.navDrawer);

        // create the toggle object
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);
        // color the toggle icon to be themed a darker sand color, defined in the theme resource file.
        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.darkerSand));

        // add an action listener for the implemented navigation drawer.
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        // Enable the back button on the action bar navigation drawer.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Get the drawer navigation view and add a navigation item event listener to it.
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    // method for hiding the keyboard, used for hiding the keyboard on drawer fragment switch.
    public void hideKeyboard() {
        // get the current focus
        View view = this.getCurrentFocus();

        // if the view is not null
        if (view != null) {
            // create an input manager object
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            // hide the keyboard
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    // Navigation Drawer Item Selection event handler.
    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        // check if item is nav_feed
        if (menuItem.getItemId() == R.id.nav_feed) {
            // navigate to feed
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentView, new FeedFragment()).commit();
            // close the drawer
            drawerLayout.closeDrawers();
        }

        // if nav_griftBoard
        if (menuItem.getItemId() == R.id.nav_griftBoard) {
            // navigate to the grift board
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentView, new GriftBoardFragment()).commit();
            // close the drawer
            drawerLayout.closeDrawers();
        }

        // if nav_resources
        if (menuItem.getItemId() == R.id.nav_resources) {
            // navigate to resources
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentView, new ResourcesFragment()).commit();
            // close the drawer
            drawerLayout.closeDrawers();
        }

        // if nav_help
        if (menuItem.getItemId() == R.id.nav_help) {
            // navigate to help & settings
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentView, new HelpAndSettingsFragment()).commit();
            // close the drawer
            drawerLayout.closeDrawers();
        }

        // if nav_createGrifter
        if (menuItem.getItemId() == R.id.nav_createGrifter) {
            // navigate to create grifter
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentView, new CreateGrifterFragment()).commit();
            // close the drawer
            drawerLayout.closeDrawers();
        }

        // exit the method
        return true;
    }

    // Method to enable navigation drawer functionality after clicking the drawer icon.
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // hide the keyboard
        hideKeyboard();

        // if draw icon is clicked make sure the drawer opens
        actionBarDrawerToggle.onOptionsItemSelected(item);

        // exit the method and return the item selected
        return super.onOptionsItemSelected(item);
    }
}