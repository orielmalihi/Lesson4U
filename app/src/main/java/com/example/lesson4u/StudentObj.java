package com.example.lesson4u;

import android.text.Editable;

import java.util.HashSet;

public class StudentObj {
    private Editable userID;
    private Editable email;
    private Editable firstName;
    private Editable lastName;
    private Editable phoneNum;
    private Editable city;
    private Editable pass;



    public StudentObj( Editable id , Editable mail , Editable fname , Editable lname , Editable city , Editable phone,
                       Editable pass ){
        this.userID = id;
        this.city = city;
        this.email = mail;
        this.firstName = fname;
        this.lastName = lname;
        this.phoneNum = phone;
        this.pass = pass;

    }

    public Editable getUserID() {
        return userID;
    }

    public Editable getEmail() {
        return email;
    }

    public Editable getFirstName() {
        return firstName;
    }

    public Editable getLastName() {
        return lastName;
    }

    public Editable getPhoneNum() {
        return phoneNum;
    }

    public Editable getCity() {
        return city;
    }

    public Editable getPass() {
        return pass;
    }

    public void setUserID(Editable userID) {
        this.userID = userID;
    }

    public void setEmail(Editable email) {
        this.email = email;
    }

    public void setFirstName(Editable firstName) {
        this.firstName = firstName;
    }

    public void setLastName(Editable lastName) {
        this.lastName = lastName;
    }

    public void setPhoneNum(Editable phoneNum) {
        this.phoneNum = phoneNum;
    }

    public void setCity(Editable city) {
        this.city = city;
    }

    public void setPass(Editable pass) {
        this.pass = pass;
    }

}
