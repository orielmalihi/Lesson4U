package com.example.lesson4u;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashSet;
import java.util.LinkedList;

public class FirebaseTeacher {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("teachers");

    public void writeNewTeacher(String id, String mail, String phone, String fname, String lname, String city , String pass,
                                LinkedList<Object> subs){
        TeacherObj newTeacher = new TeacherObj(id , mail , fname , lname , city , phone , pass , subs);
        myRef.child(id).setValue(newTeacher);
    }

    public DatabaseReference getTeacher(String id){
        return myRef.getRef().child(id);
    }
}
