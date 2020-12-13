package com.example.lesson4u;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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
    private String currentUser;
    private Button bSearchLesson;
    private EditText eDate;
    private Spinner beginTime;
    private Spinner endTime;
    private Spinner spLessonSubjects;
    private Spinner spLevel;

    private ProgressBar prog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_for_lessons);
        currentUser = auth.getInstance().getCurrentUser().getUid();
        bSearchLesson = findViewById(R.id.SearchButton);
        eDate = findViewById(R.id.DateSearch);
        beginTime = findViewById(R.id.subject);
        endTime = findViewById(R.id.subject2);
        spLessonSubjects = findViewById(R.id.Subject);
        spLevel = findViewById(R.id.level);
        prog = findViewById(R.id.progressBar);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("lessons");



        final ArrayList<LessonObj> MatchedLessons = new ArrayList<>();
        final ArrayList<String> MatchedLessonIDs = new ArrayList<>();
//        final ArrayAdapter adapter = new ArrayAdapter<LessonObj>(this, R.layout.activity_search_for_lessons , MatchedLessons);
//

        bSearchLesson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int beginst = Integer.parseInt(beginTime.getSelectedItem().toString());
                int endst = Integer.parseInt(endTime.getSelectedItem().toString());
                Log.d(TAG , "beginst =" + beginst + " endst = " + endst);
                myRef.addValueEventListener(new ValueEventListener() {

                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                prog.setVisibility(View.VISIBLE);
                                MatchedLessonIDs.clear();
                                MatchedLessons.clear();
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    LessonObj tempLesson = snapshot.getValue(LessonObj.class);
                                    if (!tempLesson.isScheduled()) {
                                        int SchduledTime = Integer.parseInt(new StringTokenizer(tempLesson.getTime(), ":").nextToken());
                                        if (tempLesson.getSubject().equals(spLessonSubjects.getSelectedItem().toString())) {
                                            if (tempLesson.getLevel().equals(spLevel.getSelectedItem().toString())) {
                                                if (tempLesson.getDate().equals(eDate.getText().toString())) {
                                                    if (beginst <= SchduledTime && SchduledTime <= endst) {
                                                        MatchedLessonIDs.add(snapshot.getKey());
                                                        MatchedLessons.add(tempLesson);
                                                        Log.d(TAG, "key = " + snapshot.getKey());

                                                        //Toast.makeText(SearchForLessonsActivity.this, "Successfully ", Toast.LENGTH_SHORT).show();

                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                 Intent intent = new Intent(getApplicationContext(), LessonResults.class);
                                intent.putExtra("MatchedLessons", MatchedLessons);
                                startActivity(intent);

                                if(MatchedLessons.isEmpty()){
                                    Toast.makeText(SearchForLessonsActivity.this, "No lessons were found, try again. ", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                   Intent intent = new Intent(getApplicationContext(), LessonResults.class);
                                   intent.putParcelableArrayListExtra("MatchedLessons", MatchedLessons);
                                   intent.putExtra("lessonIDs", MatchedLessonIDs);
                                   startActivity(intent);
                                }


                             }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }


                        });


            }


        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        prog.setVisibility(View.INVISIBLE);
    }
}