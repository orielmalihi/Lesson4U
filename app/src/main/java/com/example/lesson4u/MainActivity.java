package com.example.lesson4u;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener ,SensorEventListener {
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private SensorManager sensorManager;
    private Sensor tempSensor;
    private Boolean isTempAvailable;
    final String TAG = "MainActivity";
    SharedPreferences sp;
   // Button logout;
    Button profile;
    Button dynamic;
    Button scheduledLessons;
    TextView welcome;
    TextView temp;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);


//        Log.d(TAG, "uid is " + auth.getUid() + ", uid is "+auth.getCurrentUser().getUid());

        Log.d(TAG, "uid is " + auth.getUid());
        if (auth.getUid() == null) {
            Intent intent = new Intent(this, LoginOrRegister.class);
            startActivity(intent);
            finish();
        } else {
            refreshPS();
            Toast.makeText(this, "Already logged in", Toast.LENGTH_LONG).show();

            welcome = findViewById(R.id.textView);
            temp = findViewById(R.id.Temperature);
            profile = findViewById(R.id.profilebt);
            dynamic = findViewById(R.id.dinamicBt);
            scheduledLessons = findViewById(R.id.scheduledLessonsBt);

            profile.setOnClickListener(this);
            dynamic.setOnClickListener(this);
            scheduledLessons.setOnClickListener(this);
            sp = getSharedPreferences("user_details", 0);
            type = sp.getString("type", null);
            Log.d(TAG, "type is " + type);

        }
        if (sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) != null){
           tempSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
           isTempAvailable = true;
        }
        else{
            temp.setText("Temperature sensor is not available ");
            isTempAvailable = false;
        }
    }



    @Override
    public void onClick(View v) {
        if (v == profile) {
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
        } else if (v == dynamic) {
            if (type!=null && type.equals("student")) {
                Intent intent = new Intent(this, SearchForLessonsActivity.class);
                startActivity(intent);

            } else if (type!=null && type.equals("teacher")) {
                Intent intent = new Intent(this, AddLessonActivity.class);
                startActivity(intent);

            }
        } else if (v == scheduledLessons) {
            String currUserId = auth.getCurrentUser().getUid();
            ArrayList<String> lessonIds = new ArrayList<>();
            ArrayList<LessonObj> lessons = new ArrayList<>();

            DatabaseReference lessonsRef;
            lessonsRef = database.getReference(type+"s").child(currUserId).child("lessons");
            Log.d("scheduling", type);

            lessonsRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if (lessonsRef != null) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            LessonObj temp = ds.getValue(LessonObj.class);
                            Log.d("scheduling", temp.getSubject());
                            lessons.add(temp);
                            lessonIds.add(ds.getKey());
                        }

                        Intent intent = new Intent(getApplicationContext(), ScheduledLessonsActivity.class);
                        Log.d("scheduling", "lessons scheduled (ids): " + lessonIds.size());
                        Log.d("scheduling", "lessons scheduled (objects): " + lessons.size());
                        intent.putStringArrayListExtra("lessonIds", lessonIds);
                        intent.putParcelableArrayListExtra("lessons", lessons);
                        startActivity(intent);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });



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
                        welcome.setText("Welcome " + s.getFirstName());
                        dynamic.setText("search for lessons");

                        Log.d(TAG, "refreshPS:dataSnapshot first name = "+s.getFirstName());
                    } else if(type.equals("teachers")){
                        type = "teacher";
                        TeacherObj t = dataSnapshot.child(auth.getUid()).getValue(TeacherObj.class);
                        addInfoToTheSharedPreferencesFile("type", type);
                        Log.d(TAG, "refreshPS:dataSnapshot type = "+type);
                        addInfoToTheSharedPreferencesFile("fname", t.getFirstName());
                        welcome.setText("Welcome " + t.getFirstName());
                        dynamic.setText("add lesson");
                        Log.d(TAG, "refreshPS:dataSnapshot first name = "+t.getFirstName());
                    }
                }
                if (type!=null && type.equals("student")) {
                    dynamic.setText("search for lessons");
                    Log.d("type: ", type);
                }
                else if (type != null && type.equals("teacher")) {
                    dynamic.setText("add lesson");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "refreshPS:onCancelled", databaseError.toException());

            }

        };


//        Log.d(TAG, "userRef is " + userRef.getKey() + ", userRef2 is " + userRef2.getKey());

        studentsRef.addValueEventListener(listener);
        teachersRef.addValueEventListener(listener);


    }
    private void addInfoToTheSharedPreferencesFile(String key, String value){
        sp = getApplicationContext().getSharedPreferences("user_details", 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.apply();
    }


    public void NavigateFacebook(View view) {
        Log.w(TAG, "NavigateFacebook");
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/groups/676057579757401"));
        startActivity(browserIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.MainPage:
                Toast.makeText(this, "Home page", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.Logout:
                Toast.makeText(this, "Disconnecting", Toast.LENGTH_SHORT).show();
                auth.signOut();
                Intent intent1 = new Intent(getApplicationContext(), LoginOrRegister.class);
                startActivity(intent1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        temp.setText(event.values[0] + " °C" );
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected  void  onResume() {
        super.onResume();
        if (isTempAvailable){
            sensorManager.registerListener(this, tempSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isTempAvailable){
            sensorManager.unregisterListener(this);
        }
    }
}


