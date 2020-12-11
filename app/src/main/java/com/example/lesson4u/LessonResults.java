package com.example.lesson4u;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class LessonResults extends AppCompatActivity {
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    RecyclerView recyclerView;
    ArrayAdapter<String> arrayAdapter;


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


    }
}