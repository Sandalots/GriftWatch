package com.sandalots.griftwatch.data;

// Android Imports
import android.content.Context;

import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

// Class to define the Grifter repository for GriftWatch
public class GrifterRepository {
    // Define the grifter data access object
    private final GrifterDAO grifterDao;

    // Constructor for the grifter repo
    public GrifterRepository(Context context) {
        // create the AppDatabase database db
        AppDatabase db = Room.databaseBuilder(context, AppDatabase.class, "grifter-database").build();

        // get the grifter data access object for accessing grifter objects
        grifterDao = db.grifterDao();
    }

    // get size of database
    public int getSize() {
        // concurrent code
        FutureTask<Integer> futureTask = new FutureTask<>(() -> grifterDao.getAll().size());

        // create a new Thread
        new Thread(futureTask).start();

        // try and get size
        try {
            // try and get the size of the grifters repo
            return futureTask.get();

        // else catch the error
        } catch (InterruptedException | ExecutionException e) {
            // print the stack trace to Logcat
            e.printStackTrace();
        }

        // exit the method
        return 0;
    }

    // Method to get all grifters in the db
    public List<Grifter> getAllGrifters() {
        // concurrent code
        FutureTask<List<Grifter>> futureTask = new FutureTask<>(grifterDao::getAll);

        // start a new thread with the future task
        new Thread(futureTask).start();

        // try and get all grifters
        try {
            // try and get all grifters in the grifter list
            return futureTask.get();

        // catch any exception
        } catch (InterruptedException | ExecutionException e) {
            // print the stack trace to Logcat
            e.printStackTrace();
        }

        // return the arraylist of grifters
        return new ArrayList<>();
    }

    // Method to add a new grifter to the db
    public void addGrifter(Grifter grifter) {
        // concurrent code, start a new thread and insert a grifter into the repo
        new Thread(() -> grifterDao.insert(grifter)).start();
    }

    // reset the database to empty, deletes all the current entries
    public void reset() {
        new Thread(grifterDao::deleteAll).start();
    }

    // method to increment the grift rank of a grifter in the repo
    public void incrementGriftRank(int id) {
        // run in new thread
        new Thread(() -> {
            // get the desired grifter
            Grifter grifter = grifterDao.getGrifter(id);
            // increment the grift rank
            grifter.incrementGriftRank();
            // update the grifter
            grifterDao.update(grifter);
        }).start(); // start the thread
    }

    // method to decrement the grift rank of a grifter in the repo
    public void decrementGriftRank(int id) {
        // run in new thread
        new Thread(() -> {
            // get the desired grifter
            Grifter grifter = grifterDao.getGrifter(id);
            // decrement the grift rank
            grifter.decrementGriftRank();
            // update the grifter
            grifterDao.update(grifter);
        }).start(); // start the thread
    }

    // method to update the grift rank of a grifter in the repo
    public void updateGriftRank(int id, int griftRank) {
        // run in new thread
        new Thread(() -> {
            // get the desired grifter
            Grifter grifter = grifterDao.getGrifter(id);
            // set the grift rank to the passed grift rank
            grifter.setGriftRank(griftRank);
            // update the grifter
            grifterDao.update(grifter);
        }).start(); // start the thread
    }
}