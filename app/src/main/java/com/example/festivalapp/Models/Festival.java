package com.example.festivalapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class Festival implements Serializable {

    @SerializedName("Id")
    @Expose
    private int id;

    @SerializedName("Name")
    @Expose
    private String name;

/*    @SerializedName("StartDate")
    @Expose
    public Date StartDate;

    @SerializedName("EndDate")
    @Expose
    public Date EndDate;*/

    @SerializedName("LocationLatitude")
    @Expose
    public String LocationLatitude;

    @SerializedName("LocationLongitude")
    @Expose
    public String LocationLongitude;

    @SerializedName("Description")
    @Expose
    public String Description;

    @SerializedName("Rating")
    @Expose
    public String Rating;

    @SerializedName("FestivalTypeId")
    @Expose
    public int FestivalTypeId;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLocationLatitude() {
        return LocationLatitude;
    }

    public String getLocationLongitude() {
        return LocationLongitude;
    }

    public String getDescription() {
        return Description;
    }

    public String getRating() {
        return Rating;
    }

    public int getFestivalTypeId() {
        return FestivalTypeId;
    }
}
