package com.example.lesson4u;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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
    final String TAG = "MainActivity";
    SharedPreferences sp;
    Button loguot;
    Button refresh;
    Button profile;
    Button dinamic;
    Button scheduledLessons;
    TextView welcome;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
//    protected void onReStart(){
//        super.onRestart();
//        this.onStart();
//    }
//
//    protected void onResume(){
//        super.onResume();
//        this.onStart();
//    }


    protected void onStart() {
        super.onStart();
        Log.d(TAG, "uid is " + auth.getUid());
        if (auth.getUid() == null) {
            Intent intent = new Intent(this, LoginOrRegister.class);
            startActivity(intent);
            finish();
        } else {
            refreshPS();

//            Toast.makeText(this, "Already logged in", Toast.LENGTH_LONG).show();
            loguot = findViewById(R.id.button3);
            refresh = findViewById(R.id.button4);
            welcome = findViewById(R.id.textView);
            profile = findViewById(R.id.profilebt);
            dinamic = findViewById(R.id.dinamicBt);
            scheduledLessons = findViewById(R.id.scheduledLessonsBt);
            loguot.setOnClickListener(this);
            refresh.setOnClickListener(this);
            profile.setOnClickListener(this);
            dinamic.setOnClickListener(this);
            scheduledLessons.setOnClickListener(this);
            sp = getSharedPreferences("user_details", 0);
            type = sp.getString("type", null);
            Log.d(TAG, "type is " + type);
            if (type.equals("student")) {
                dinamic.setText("search for lessons");
            } else if (type.equals("teacher")) {
                dinamic.setText("add lesson");
            }
            String fname = sp.getString("fname", null);
            welcome.setText("Welcome " + fname);
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
        if (v == loguot) {
            auth.signOut();
            Intent intent = new Intent(this, LoginOrRegister.class);
            startActivity(intent);
            finish();
        } else if (v == profile) {
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
        } else if (v == dinamic) {
            if (type.equals("student")) {
                Intent intent = new Intent(this, SearchForLessonsActivity.class);
                startActivity(intent);
                finish();
            } else if (type.equals("teacher")) {
                Intent intent = new Intent(this, AddLessonActivity.class);
                startActivity(intent);
                finish();
            }
        } else if (v == scheduledLessons) {
            Intent intent = new Intent(this, ScheduledLessonsActivity.class);
            startActivity(intent);
        } else if(v == refresh){
            this.onStart();
        }
    }

    private void refreshPS() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference studentsRef = database.getReference("students");
        DatabaseReference teachersRef = database.getReference("teachers");

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                Log.d(TAG, "refreshPS:dataChange");
                Log.d(TAG, "refreshPS:dataSnapshot getKey = "+dataSnapshot.getKey());
                long count = dataSnapshot.child(auth.getUid()).getChildrenCount();
                Log.d(TAG, "refreshPS:dataSnapshot count = "+count);
                if(count>0){
                    type = dataSnapshot.getKey();
                    if(type.equals("students")){
                        type = "student";
                        StudentObj s = dataSnapshot.child(auth.getUid()).getValue(StudentObj.class);
                        addInfoToTheSharedPreferencesFile("type", type);
                        Log.d(TAG, "refreshPS:dataSnapshot type = "+type);
                        addInfoToTheSharedPreferencesFile("fname", s.getFirstName());
                        Log.d(TAG, "refreshPS:dataSnapshot first name = "+s.getFirstName());
                    } else if(type.equals("teachers")){
                        type = "teacher";
                        TeacherObj t = dataSnapshot.child(auth.getUid()).getValue(TeacherObj.class);
                        addInfoToTheSharedPreferencesFile("type", type);
                        Log.d(TAG, "refreshPS:dataSnapshot type = "+type);
                        addInfoToTheSharedPreferencesFile("fname", t.getFirstName());
                        Log.d(TAG, "refreshPS:dataSnapshot first name = "+t.getFirstName());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.d(TAG, "refreshPS:onCancelled", databaseError.toException());

            }

        };
        studentsRef.addValueEventListener(listener);
        teachersRef.addValueEventListener(listener);
//        Log.d(TAG, "userRef is " + userRef.getKey() + ", userRef2 is " + userRef2.getKey());
    }
    private void addInfoToTheSharedPreferencesFile(String key, String value){
        sp = getApplicationContext().getSharedPreferences("user_details", 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.apply();
    }
}


