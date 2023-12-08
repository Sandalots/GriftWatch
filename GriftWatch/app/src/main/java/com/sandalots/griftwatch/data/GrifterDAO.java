package com.sandalots.griftwatch.data;

// Android Imports
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

// Interface to define the Grifter data access object, makes use of ROOM annotations
@Dao
public interface GrifterDAO {
    // get all the grifters in the grifter table
    @Query("SELECT * FROM Grifter")
    List<Grifter> getAll();

    // get the grifter by given id
    @Query("SELECT * FROM Grifter WHERE id = :id")
    Grifter getById(int id);

    // add a grifter to the grifter table
    @Insert
    void addGrifter(Grifter grifter);

    // insert the grifter, if conflicting primary key (id) replace the id
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Grifter grifter);

    // update the grifter
    @Update
    void update(Grifter grifter);

    // delete all but keep the grifter id 0, 1, 2 (the default grifters in the feed before user additions)
    @Query("DELETE FROM Grifter WHERE id NOT IN (0, 1, 2)")
    void deleteAll();

    // get the grifter by given id
    @Query("SELECT * FROM grifter WHERE id = :id")
    Grifter getGrifter(int id);
}