package com.example.app_tp1swi.models;

public class User {

    public String fullname,email,phone,password;

    public User(String fullname, String email, String phone, String password) {
        this.fullname = fullname;
       this.email = email;
        this.phone = phone;
        this.password = password;
    }
    public User() {
    }
    public String getFullname() {
        return fullname;
    }

    public String getEmail() {
        return email;
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
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
