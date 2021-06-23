package com.sudarshan.registration;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {
    EditText email,password;
    Button login,register;
    TextInputLayout passshow;
    DataBase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        login=findViewById(R.id.login);
        register=findViewById(R.id.register);
        passshow=findViewById(R.id.passshow);
        db=new DataBase(getApplicationContext());
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getemail=email.getText().toString();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                String getpassword=password.getText().toString();
                if(getemail.equals("")){
                    email.setError("enter your email");
                }
                else if(!email.getText().toString().trim().matches(emailPattern)){
                    email.setError("enter valid email");
                }
                else if(getpassword.equals("")){
                    passshow.setEndIconVisible(false);
                    password.setError("enter your password");
                }
               else {
                    Boolean checkemailpassword=db.checkemailpassword(getemail,getpassword);
                    if (checkemailpassword == true) {
                        Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, HomePage.class);
                        startActivity(intent);
                    }else
                    {
                        Boolean checkemail=db.checkemail(getemail);
                        if (checkemail==true){
                            passshow.setEndIconVisible(false);
                            password.setError("Incorrect password");
                        } else {
                            Toast.makeText(MainActivity.this, "Login Failed ! Please Register first", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, Registration.class);
                            startActivity(intent);
                        }
                    }

                }
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Registration.class);
                startActivity(intent);
            }
        });

    }
}