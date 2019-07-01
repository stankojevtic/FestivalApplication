package com.example.festivalapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserUpdate implements Serializable {

    @SerializedName("Username")
    @Expose
    private String Username;

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

    @SerializedName("AboutMe")
    @Expose
    public String AboutMe;

    public UserUpdate(String username,
                String firstname,
                String lastname,
                int age,
                String city,
                String country,
                String address,
                String aboutMe) {
        this.Username = username;
        this.Firstname = firstname;
        this.Lastname = lastname;
        this.City = city;
        this.Country = country;
        this.Age = age;
        this.Address = address;
        this.AboutMe = aboutMe;
    }

    public String getUsername() {
        return Username;
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

    public String getAboutMe() {
        return AboutMe;
    }
}