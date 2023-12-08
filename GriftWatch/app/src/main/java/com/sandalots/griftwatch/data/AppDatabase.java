package com.sandalots.griftwatch.data;

// Android Imports
import androidx.room.Database;
import androidx.room.RoomDatabase;

// An abstract class to define a Room app database

// Create the database to use Grifter objects as the entities
@Database(entities = {Grifter.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    // Define the Grifter data access object to access the Grifter table in the room database
    public abstract GrifterDAO grifterDao();
}