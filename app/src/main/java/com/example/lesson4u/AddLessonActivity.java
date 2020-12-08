package com.example.lesson4u;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddLessonActivity extends AppCompatActivity {
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private String currentuser;
    private Button bAddLesson;
    private Spinner eTime;
    private EditText eDate;
    private Spinner spLessonSubjects;
    private Spinner spLevel;
    private EditText price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lesson);
        currentuser = auth.getInstance().getCurrentUser().getUid();
        bAddLesson = findViewById(R.id.addLessonBtn);
        eTime = findViewById(R.id.chooseTime);
        eTime.setPrompt("Choose time");
        eDate = findViewById(R.id.LessonDate);
        spLessonSubjects = findViewById(R.id.chooseLessonSubSpinner);
        spLevel = findViewById(R.id.chooseLevelSpinner);
        price = findViewById(R.id.LessonPrice);

        bAddLesson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("lessons");
                LessonObj newLesson = new LessonObj(currentuser, spLessonSubjects.getSelectedItem().toString(), spLevel.getSelectedItem().toString(), Integer.parseInt(price.getText().toString()), eDate.getText().toString(), eTime.getSelectedItem().toString());
                myRef.push().setValue(newLesson).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(AddLessonActivity.this, "Successfully Added Lesson", Toast.LENGTH_SHORT).show();
                            //TODO: Adding refresh func
                            // startActivity(new Intent(getApplicationContext(), AddLessonActivity.class));
                        } else {
                            Toast.makeText(AddLessonActivity.this, "Lesson adding failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
