package com.example.lesson4u;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    FirebaseAuth auth = FirebaseAuth.getInstance();
    EditText mailb;
    EditText passb;
    Button loginb;
    RadioGroup radioGroup;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mailb = findViewById(R.id.editTextTextEmailAddress2);
        passb = findViewById(R.id.editTextTextPassword2);
        loginb = findViewById(R.id.loginb3);
        radioGroup = findViewById(R.id.radiogroup);

        loginb.setOnClickListener(this);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // find which radio button is selected
                if(checkedId == R.id.radioButtonStudent) {
                    type = "student";
                } else if(checkedId == R.id.radioButtonTeacher) {
                    type = "teacher";
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == loginb) {
            auth.createUserWithEmailAndPassword(mailb.getText().toString(), passb.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                public void onComplete(Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(RegisterActivity.this, "Successfully Registered", Toast.LENGTH_LONG).show();
                        if(type.equals("student")){
                            Intent intent = new Intent(RegisterActivity.this, studentRegistration.class);
                            startActivity(intent);
                            finish();
                        } else if(type.equals("teacher")){
                            Intent intent = new Intent(RegisterActivity.this, teacherRegistration.class);
                            startActivity(intent);
                            finish();
                        }else {
                            Toast.makeText(RegisterActivity.this, "You must choose teacher/student", Toast.LENGTH_LONG).show();
                        }

                    } else {
                        Toast.makeText(RegisterActivity.this, "Registration Failed", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
}