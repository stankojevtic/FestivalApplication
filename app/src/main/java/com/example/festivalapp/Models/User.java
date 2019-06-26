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

    @SerializedName("Password")
    @Expose
    public String Password;

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

    public User(String username,
                    String email,
                    String password,
                    String firstname,
                    String lastname,
                    String city,
                    String country,
                    int age,
                    String address)
    {
        this.Username = username;
        this.Email = email;
        this.Password = password;
        this.Firstname = firstname;
        this.Lastname = lastname;
        this.City = city;
        this.Country= country;
        this.Age = age;
        this.Address = address;
        this.Role = "User";
        this.AboutMe = "";
    }

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
