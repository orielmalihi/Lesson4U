package com.example.lesson4u;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashSet;

public class FirebaseTeacher {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("teachers");

    public void writeNewTeacher(String id, String mail, String phone, String fname, String lname, String city , String pass,
                                HashSet<String> subs){
        TeacherObj newTeacher = new TeacherObj(id , mail , fname , lname , city , phone , pass , subs);
        myRef.child(id).setValue(newTeacher);
    }

    public DatabaseReference getTeacher(String id){
        return myRef.getRef().child(id);
    }
}
