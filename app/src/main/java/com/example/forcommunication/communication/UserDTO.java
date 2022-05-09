package com.example.forcommunication.communication;

public class UserDTO {
    private String UserId;
    private String Email;
    private String Password;
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
