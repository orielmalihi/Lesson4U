package com.example.lesson4u;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;

public class LessonResults extends AppCompatActivity {

    private ListView listView;
    final String TAG = "lessonResults";

    private ArrayList<String> matchedLessons;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_results);
        listView = findViewById(R.id.listView);

        Intent intent = getIntent();
        matchedLessons = intent.getExtras().getStringArrayList("lessons");

        Log.d(TAG, "matched lesson is empty: "+matchedLessons.isEmpty() );
        listView.setAdapter(new customAdapter(matchedLessons, this) );
    }
}