package com.example.lesson4u;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button studentb;
    Button teacherb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        studentb = (Button)findViewById(R.id.studentButton);
        teacherb = findViewById(R.id.teacherButton);
        studentb.setOnClickListener(this);
        teacherb.setOnClickListener(this);

//        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("students/student1");
        DatabaseReference myRootRef = database.getReference();
//        myRef.setValue("oriel malihi");
//        myRootRef.child("students").child("student").setValue("oriel");
//        myRootRef.child("students").child("student2").setValue("yahav");
//        myRootRef.child("students").child("student3").setValue("shoval");
//        String s = myRef.getKey();
//
//        Map<String, Object> map = new HashMap<>();
//        map.put("student one", "student1");
//        map.put("student two", "student2");
//        myRootRef.child("students").updateChildren(map);
//        myRootRef.child("students").child("student3").removeValue();


    }

    @Override
    public void onClick(View v) {
        if(v == studentb){
            Intent intent = new Intent(this, loginOrRegister.class);
            intent.putExtra("type", "student");
            startActivity(intent);
        } else if(v == teacherb){
            Intent intent = new Intent(this, loginOrRegister.class);
            intent.putExtra("type", "teacher");
            startActivity(intent);
        }
    }
}