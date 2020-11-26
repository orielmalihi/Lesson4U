package com.example.lesson4u;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    FirebaseAuth auth = FirebaseAuth.getInstance();
    Button loguot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    protected void onStart() {
        super.onStart();
//        System.out.println("***********************"+auth.getUid());
        if (auth.getUid() == null) {
            Intent intent = new Intent(this, LoginOrRegister.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Already logged in", Toast.LENGTH_LONG).show();
            loguot = findViewById(R.id.button3);
            loguot.setOnClickListener(this);
        }
    }


//        // Write a message to the database
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("students/student1");
//        DatabaseReference myRootRef = database.getReference();
//        myRef.setValue("ploni");
//        myRootRef.child("students").child("student1").setValue("oriel");
//        myRootRef.child("students").child("student2").setValue("yahav");
//        myRootRef.child("students").child("student3").setValue("shoval");
//        String s = myRef.getKey();
//
//        Map<String, Object> map = new HashMap<>();
//        map.put("student one", "student1");
//        map.put("student two", "student2");
//        myRootRef.child("students").child("student1").updateChildren(map);
//        myRootRef.child("students").child("student3").removeValue();
//

    @Override
    public void onClick(View v) {
        if(v == loguot){
            auth.signOut();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }
}


