package com.example.forcommunication.communication;

import com.google.gson.annotations.SerializedName;

public class UserDTO {
    @SerializedName("UserId")
    private String UserId;
    @SerializedName("Email")
    private String Email;
    @SerializedName("Password")
    private String Password;
    @SerializedName("Name")
    private String Name;

    public String getUserId(){
        return UserId;
    }
    public String getEmail() {
        return Email;
    }
    public String getName() {
        return Name;
    }
    public String getPassword() {
        return Password;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }
    public void setEmail(String email) {
        Email = email;
    }
    public void setName(String name) {
        Name = name;
    }
    public void setPassword(String password) {
        Password = password;
    }
}
