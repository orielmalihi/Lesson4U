package com.example.lesson4u;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.lesson4u.LessonObj;
import com.example.lesson4u.MyAdapterForScheduledLessons;

import java.util.ArrayList;

public class ScheduledLessonsActivity extends AppCompatActivity {

    final String TAG = "schedLessons";
    ArrayList<LessonObj> lessons;
    ArrayList<String> lessonIds;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduled_lessons);

        recyclerView = findViewById(R.id.recyclerView);

        Intent intent = getIntent();
        lessons =  intent.getExtras().getParcelableArrayList("lessons");
        lessonIds = intent.getExtras().getStringArrayList("lessonIds");
        Log.d(TAG, "array size " + lessons.size());



        MyAdapterForScheduledLessons myAdapter = new MyAdapterForScheduledLessons(this, lessons, lessonIds);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}