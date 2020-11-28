package com.example.lesson4u;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseStudent {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("students");

    public void writeNewStudent(String id, String mail, String phone, String fname, String lname, String city , String pass){
        StudentObj newStudent = new StudentObj(id , mail , fname , lname , city , phone, pass);
        myRef.child(id).setValue(newStudent);
    }

    public DatabaseReference getStudent(String id){
        return myRef.getRef().child(id);
    }
}
