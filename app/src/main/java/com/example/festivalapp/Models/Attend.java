package com.example.festivalapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Attend implements Serializable {
    @SerializedName("Id")
    @Expose
    private int id;

    @SerializedName("Username")
    @Expose
    private String username;

    public Attend(int id, String username)
    {
        this.id = id;
        this.username = username;

    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
