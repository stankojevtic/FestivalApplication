package com.example.festivalapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User implements Serializable {
    @SerializedName("Id")
    @Expose
    private int id;

    @SerializedName("Username")
    @Expose
    private String Username;

    @SerializedName("Email")
    @Expose
    public String Email;

    @SerializedName("Firstname")
    @Expose
    public String Firstname;

    @SerializedName("Lastname")
    @Expose
    public String Lastname;

    @SerializedName("Age")
    @Expose
    public int Age;

    @SerializedName("City")
    @Expose
    public String City;

    @SerializedName("Country")
    @Expose
    public String Country;

    @SerializedName("Address")
    @Expose
    public String Address;

    @SerializedName("Role")
    @Expose
    public String Role;

    @SerializedName("Image")
    @Expose
    public String Image;

    @SerializedName("AboutMe")
    @Expose
    public String AboutMe;

    public int getId() {
        return id;
    }

    public String getUsername() {
        return Username;
    }

    public String getEmail() {
        return Email;
    }

    public String getFirstname() {
        return Firstname;
    }

    public String getLastname() {
        return Lastname;
    }

    public int getAge() {
        return Age;
    }

    public String getCity() {
        return City;
    }

    public String getCountry() {
        return Country;
    }

    public String getAddress() {
        return Address;
    }

    public String getRole() {
        return Role;
    }

    public String getImage() {
        return Image;
    }

    public String getAboutMe() {
        return AboutMe;
    }
}
