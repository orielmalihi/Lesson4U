package com.example.lesson4u;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ListView;

import java.util.ArrayList;

import java.util.ArrayList;

public class LessonResults extends AppCompatActivity {
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    RecyclerView recyclerView;
    ArrayAdapter<String> arrayAdapter;


    //private ListView listView;
    final String TAG = "lessonResults";
    ArrayList<LessonObj> MatchedLessons;
    ArrayList<String> lessonIDs;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_results);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("lessons");
        ArrayList<String> MatchedLessons = (ArrayList<String>) getIntent().getSerializableExtra("MatchedLessons");
        recyclerView = findViewById(R.id.recycle);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        //recyclerView.setAdapter();



        recyclerView = findViewById(R.id.recyclerView);

        Intent intent = getIntent();
        MatchedLessons =  intent.getExtras().getParcelableArrayList("MatchedLessons");
        lessonIDs = intent.getExtras().getStringArrayList("lessonIDs");
        Log.d(TAG, "array size " + MatchedLessons.size());

        MyAdapter myAdapter = new MyAdapter(this, MatchedLessons, lessonIDs);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

}