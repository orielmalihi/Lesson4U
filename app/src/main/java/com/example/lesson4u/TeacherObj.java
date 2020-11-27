package com.example.lesson4u;

import java.util.HashSet;

public class TeacherObj {
    private String userID;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNum;
    private String city;
    private String pass;
    private HashSet<String> subjects;


    public TeacherObj( String id , String mail , String fname , String lname , String city , String phone,
                       String pass , HashSet<String> subs){
        this.userID = id;
        this.city = city;
        this.email = mail;
        this.firstName = fname;
        this.lastName = lname;
        this.phoneNum = phone;
        this.pass = pass;
        this.subjects = new HashSet<String>();
    }

    public String getUserID() {
        return userID;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public String getCity() {
        return city;
    }

    public String getPass() {
        return pass;
    }

    public HashSet<String> getSubjects() {
        return subjects;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void setSubjects(HashSet<String> subjects) {
        this.subjects = subjects;
    }



}
