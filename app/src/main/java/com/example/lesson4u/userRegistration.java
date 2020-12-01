package com.example.lesson4u;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

public class userRegistration extends AppCompatActivity implements View.OnClickListener{
    FirebaseAuth auth = FirebaseAuth.getInstance();
    EditText First_name;
    EditText Last_name;
    EditText email;
    EditText address;
    EditText Phone_number;
    EditText userID;
    EditText Password;
    Button registrationB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);

        First_name = findViewById(R.id.First_name);
        Last_name = findViewById(R.id.Last_name);
        email = findViewById(R.id.email);
        address = findViewById(R.id.address);
        Phone_number = findViewById(R.id.Phone_number);
        Password = findViewById(R.id.Password);
        userID = findViewById(R.id.userID);
        registrationB = findViewById(R.id.registration_button);

        registrationB.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        FirebaseStudent.writeNewStudent(userID.getText() , email.getText() , Phone_number.getText() , First_name.getText(),
                Last_name.getText() , address.getText() , Password.getText());
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}