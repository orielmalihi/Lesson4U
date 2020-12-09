package com.example.lesson4u;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LessonDetailsActivity extends AppCompatActivity {

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    DatabaseReference myRef;
    DatabaseReference studRef;
    DatabaseReference lessonRef;
    TextView teacherName, price, time, subject, level;
    Button bChoose;
    LessonObj lesson;
    String lessonID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_details);

        teacherName = findViewById(R.id.tvTeacherName);
        price = findViewById(R.id.pp);
        time = findViewById(R.id.tvTime);
        subject = findViewById(R.id.tvSubject);
        level = findViewById(R.id.tvLevel);
        bChoose = findViewById(R.id.bChoose);

        lesson = getIntent().getParcelableExtra("lesson");
        lessonID = getIntent().getStringExtra("id");

        myRef = database.getReference("teachers").child(lesson.getTeacherUID());
        studRef = FirebaseStudent.getStudent(auth.getCurrentUser().getUid());
        lessonRef = database.getReference("lessons").child(lessonID);


        price.setText("Price per hour: " + lesson.getPrice() + "₪");
        time.setText("Time: " + lesson.getTime());
        subject.setText("Subject: " + lesson.getSubject());
        level.setText("Level: " + lesson.getLevel());

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                TeacherObj t = dataSnapshot.getValue(TeacherObj.class);
                String name = t.getFirstName();
                teacherName.setText("Teacher: " + name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        bChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                studRef.child("lessons").push().setValue(lessonID).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LessonDetailsActivity.this, "Lesson scheduled SUCCCCCESSFULLY", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(LessonDetailsActivity.this, "Loser", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                lesson.setScheduled(true);
                lessonRef.setValue(lesson);
                Intent intent = new Intent(LessonDetailsActivity.this, MainActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }
}