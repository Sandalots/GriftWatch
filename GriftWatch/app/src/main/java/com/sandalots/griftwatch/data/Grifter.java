package com.sandalots.griftwatch.data;

// Android Imports
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

// Domain class to encapsulate a grifter object.
@Entity
public class Grifter {
    // Set the id of the grifter to be the primary key of grifter objects in the grifter table.
    @PrimaryKey
    public int id;

    // Defines the name of a grifter object
    private String name;

    // Defines the grifts or cons of a grifter object
    private String grifts;

    // defines the country of origin of the grifter
    private String country;

    // defines the ranking of said grifter
    private int GriftRank;

    // Constructor for a Grifter
    public Grifter(int id, String name, String grifts, String country, int GriftRank) {
        this.id = id;
        this.name = name;
        this.grifts = grifts;
        this.country = country;
        this.GriftRank = GriftRank;
    }

    public String getName() {
        return name;
    } // gets the name of a grifter

    public void setName(String name) {
        this.name = name;
    } // sets the name of a grifter

    public String getGrifts() {
        return grifts;
    } // gets the grifts of a grifter

    public void setGrifts(String grifts) {
        this.grifts = grifts;
    } // sets the grifts of a grifter

    public String getCountry() {
        return country;
    } // gets the country of origin of a grifter

    // sets the country of a grifter
    public void setCountry(String country) {
        this.country = country;
    }

    public int getGriftRank() {
        return GriftRank;
    } // gets the grift rank of a grifter

    // sets the grift rank of a grifter
    public void setGriftRank(int griftRank) {
        GriftRank = griftRank;
    }

    // Method to generate a random Id for a new grifter
    public static int generateId() {
        // return the id based on the current time in milliseconds to make it unique
        return Math.abs((int) System.currentTimeMillis());
    }

    public int getId() {
        return id;
    } // gets the id of a grifter

    public void setId(int id) {
        this.id = id;
    } // sets the id of a grifter

    // toString method for a Grifter
    @NonNull
    @Override
    public String toString() {
        return "Grifter{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", grifts='" + grifts + '\'' +
                ", country='" + country + '\'' +
                ", GriftRank=" + GriftRank +
                '}';
    }

    // increment the grift rank
    public void incrementGriftRank() {
        GriftRank++;
    } // increments the grift rank

    // decrement the grift rank
    public void decrementGriftRank() {
        GriftRank--;
    } // decrements the grift rank
}
