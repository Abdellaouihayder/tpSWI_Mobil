package com.example.app_tp1swi.models;

public class User {

    public String fullname,Email,phone,password;

    public User(String fullname, String email, String phone, String password) {
        this.fullname = fullname;
       this.Email = email;
        this.phone = phone;
        this.password = password;
    }
    public User() {
    }
    public String getFullname() {
        return fullname;
    }

    public String getEmail() {
        return Email;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
