package com.example.festivalapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Festival implements Serializable {

    @SerializedName("Id")
    @Expose
    public int id;

    @SerializedName("Name")
    @Expose
    public String name;

    @SerializedName("StartDate")
    @Expose
    public String StartDate;

    @SerializedName("EndDate")
    @Expose
    public String EndDate;

    @SerializedName("LocationLatitude")
    @Expose
    public String LocationLatitude;

    @SerializedName("LocationLongitude")
    @Expose
    public String LocationLongitude;

    @SerializedName("Description")
    @Expose
    public String Description;

    @SerializedName("Address")
    @Expose
    public String Address;

    @SerializedName("Rating")
    @Expose
    public String Rating;

    @SerializedName("Image")
    @Expose
    public String Image;

    @SerializedName("TimeStart")
    @Expose
    public String TimeStart;

    @SerializedName("FestivalTypeId")
    @Expose
    public int FestivalTypeId;

    @SerializedName("NumberOfAttendees")
    @Expose
    public int AttendeesNumber;

    @SerializedName("UserAttendings")
    @Expose
    public List<User> UserAttendings;

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

    public String getAddress() { return Address; }

    public String getImage() { return Image; }

    public String getTimeStart() { return TimeStart; }

    public String getStartDate() { return StartDate; }

    public String getEndDate() { return EndDate; }

    public int getAttendeesNumber() { return AttendeesNumber; }

    public List<User> getUserAttendings() { return UserAttendings; }
}
