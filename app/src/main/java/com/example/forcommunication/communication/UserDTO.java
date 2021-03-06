package com.example.forcommunication.communication;

import com.google.gson.annotations.SerializedName;

public class UserDTO {
    private String UserId;
    private String Email;
    private String Password;
    private String Name;

    public UserDTO(){

    }
    public UserDTO(String UserId, String Name, String Email, String Password){
        this.UserId=UserId;
        this.Email=Email;
        this.Password=Password;
        this.Name=Name;
    }

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
