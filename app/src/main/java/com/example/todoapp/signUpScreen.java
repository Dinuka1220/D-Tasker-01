package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class signUpScreen extends AppCompatActivity {

    TextView login;
    EditText userName,password,confirmPassword,email;
    Button create;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_screen);

        login=findViewById(R.id.textlogin);
        userName=findViewById(R.id.editUserName);
        password=findViewById(R.id.editTextpw);
        confirmPassword=findViewById(R.id.editTextconfirmpw);
        email=findViewById(R.id.editTextemail);
        create=findViewById(R.id.btnCreate);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(signUpScreen.this,loginScreen.class);
                startActivity(intent);
                finish();
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = userName.getText().toString();
                String pass = password.getText().toString();
                String confirmPass = confirmPassword.getText().toString();
                String mail = email.getText().toString();

                if (user.isEmpty() || pass.isEmpty() || confirmPass.isEmpty() || mail.isEmpty()) {
                    Toast.makeText(signUpScreen.this, "All fields are required", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!pass.equals(confirmPass)) {
                    Toast.makeText(signUpScreen.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Save user data to SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Username", user);
                editor.putString("Password", pass);
                editor.putString("Email", mail);
                editor.apply();

                Toast.makeText(signUpScreen.this, "User registered successfully", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(signUpScreen.this, loginScreen.class);
                startActivity(intent);
                finish();
            }

        });
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(signUpScreen.this,loginScreen.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }
}