package com.example.lesson4u;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

public class SearchForLessonsActivity extends AppCompatActivity {

    private FirebaseAuth auth = FirebaseAuth.getInstance();
    final String TAG = "SearchLessonsActivity";
    private String currentuser;
    private Button bSearchLesson;
    private EditText eDate;
    private EditText beginTime;
    private EditText endTime;
    private Spinner spLessonSubjects;
    private Spinner spLevel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_for_lessons);
        currentuser = auth.getInstance().getCurrentUser().getUid();
        bSearchLesson = findViewById(R.id.SearchButton);
        eDate = findViewById(R.id.DateSearch);
        beginTime = findViewById(R.id.FromTime);
        endTime = findViewById(R.id.ToTime);
        spLessonSubjects = findViewById(R.id.Subject);
        spLevel = findViewById(R.id.level);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("lessons");


        final ArrayList<String> MatchedLessons = new ArrayList<>();
//        final ArrayAdapter adapter = new ArrayAdapter<LessonObj>(this, R.layout.activity_search_for_lessons , MatchedLessons);
//

        bSearchLesson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int beginst = Integer.parseInt(new StringTokenizer(beginTime.getText().toString(), ":").nextToken());
                int endst = Integer.parseInt(new StringTokenizer(endTime.getText().toString(), ":").nextToken());
                Log.d(TAG , "beginst =" + beginst + " endst = " + endst);
                myRef.addValueEventListener(new ValueEventListener() {

                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    LessonObj tempLesson = snapshot.getValue(LessonObj.class);
                                    if (!tempLesson.isScheduled()) {
                                        int SchduledTime = Integer.parseInt(new StringTokenizer(tempLesson.getTime(), ":").nextToken());
                                        if (tempLesson.getSubject().equals(spLessonSubjects.getSelectedItem().toString())) {
                                            if (tempLesson.getLevel().equals(spLevel.getSelectedItem().toString())) {
                                                if (tempLesson.getDate().equals(eDate.getText().toString())) {
                                                    if (beginst <= SchduledTime && SchduledTime <= endst) {
                                                        MatchedLessons.add(snapshot.getKey());
                                                        Log.d(TAG, "key = " + snapshot.getKey());
                                                        Toast.makeText(SearchForLessonsActivity.this, "Successfully ", Toast.LENGTH_SHORT).show();

                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                Intent intent = new Intent(getApplicationContext(), LessonResults.class);
                                intent.putExtra("MatchedLessons", MatchedLessons);
                                startActivity(intent);
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }

                        });
              //  myRef.addValueEventListener(listener);


            }


        });

    }
}