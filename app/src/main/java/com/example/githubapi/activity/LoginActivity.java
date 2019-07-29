package com.example.githubapi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.githubapi.R;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameET = findViewById(R.id.usernameID);
    }

    public void SignIn(View view) {

        Intent intent = new Intent(this, UserActivity.class);
        intent.putExtra("username",usernameET.getText().toString());
        startActivity(intent);
    }
}
