package com.example.jotit_test.Models;

public class Users {
    String userName , mail , password, userid;

    public Users(String userName, String mail, String password, String userid) {
        this.userName = userName;
        this.mail = mail;
        this.password = password;
        this.userid = userid;
    }

    public Users(){}

    //Signup constructor
    public Users(String userName, String mail, String password)
    {
        this.userName = userName;
        this.mail = mail;
        this.password = password;

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
