package com.example.lesson4u;

import android.text.Editable;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseStudent {

    public static void writeNewStudent(Editable id, Editable mail, Editable phone, Editable fname, Editable lname, Editable city , Editable pass){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("students");
        StudentObj newStudent = new StudentObj(id , mail , fname , lname , city , phone, pass);
        myRef.child(String.valueOf(id)).setValue(newStudent);
    }

    public DatabaseReference getStudent(String id){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("students");
        return myRef.getRef().child(id);
    }
}
