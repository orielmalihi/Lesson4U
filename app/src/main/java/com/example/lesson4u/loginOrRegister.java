package com.example.lesson4u;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

public class loginOrRegister extends AppCompatActivity {
    Button login;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_or_register);
        login = (Button)findViewById(R.id.loginb);
        register = (Button)findViewById(R.id.registrb);

        Intent intent = getIntent();
        String type = intent.getExtras().getString("type");
        Toast.makeText(this, "the user type is "+type, Toast.LENGTH_LONG).show();
    }
}