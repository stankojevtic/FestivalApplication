package com.example.festivalapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ChangePassword implements Serializable {

    @SerializedName("Username")
    @Expose
    public String Username;

    @SerializedName("OldPassword")
    @Expose
    public String OldPassword;

    @SerializedName("NewPassword")
    @Expose
    public String NewPassword;

    @SerializedName("ConfirmPassword")
    @Expose
    public String ConfirmPassword;


    public ChangePassword(String username,
                          String oldPassword,
                          String newPassword,
                          String confirmPassword) {
        this.Username = username;
        this.OldPassword = oldPassword;
        this.NewPassword = newPassword;
        this.ConfirmPassword = confirmPassword;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public void setOldPassword(String oldPassword) {
        OldPassword = oldPassword;
    }

    public void setNewPassword(String newPassword) {
        NewPassword = newPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        ConfirmPassword = confirmPassword;
    }

    public String getUsername() {
        return Username;
    }

    public String getOldPassword() {
        return OldPassword;
    }

    public String getNewPassword() {
        return NewPassword;
    }

    public String getConfirmPassword() {
        return ConfirmPassword;
    }
}