package com.example.lesson4u;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class LessonResults extends AppCompatActivity implements View.OnClickListener {

    private ListView listView;
    private customAdapter ca;
    final String TAG = "lessonResults";
    Button refresh;

    private ArrayList<String> matchedLessons;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_results);
        listView = findViewById(R.id.listView);
        refresh = findViewById(R.id.button5);
        refresh.setOnClickListener(this);
        Intent intent = getIntent();
        matchedLessons = intent.getExtras().getStringArrayList("lessons");

        Log.d(TAG, "matched lesson is empty: "+matchedLessons.isEmpty() );

        ca = new customAdapter(matchedLessons, LessonResults.this);
        listView.setAdapter(ca);


    }

    public void onClick(View v){
        if(v == refresh){
            if(ca.isRefreshed()){
                Toast.makeText(LessonResults.this, "refreshed Successfully", Toast.LENGTH_LONG).show();
                listView.setAdapter(null);
                listView.setAdapter(ca);
            } else{
                Toast.makeText(LessonResults.this, "refreshed Failed", Toast.LENGTH_LONG).show();
            }
        }
    }

}