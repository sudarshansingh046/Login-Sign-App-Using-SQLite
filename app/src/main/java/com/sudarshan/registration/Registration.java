package com.sudarshan.registration;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class Registration extends AppCompatActivity {
    EditText fname,lname,dob,email,password,confirmpassword;
    TextView Byear;
    Button register;
    DataBase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        fname=findViewById(R.id.firstname);
        lname=findViewById(R.id.lastname);
        dob=findViewById(R.id.dob);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        confirmpassword=findViewById(R.id.confirmpassword);
        register=findViewById(R.id.register);
        Byear=findViewById(R.id.year);
        db=new DataBase(getApplicationContext());
        Calendar calendar=Calendar.getInstance();
        final int year=calendar.get(Calendar.YEAR);
        final int month=calendar.get(Calendar.MONTH);
        final int day=calendar.get(Calendar.DAY_OF_MONTH);
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(Registration.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month=month+1;
                        String date= day+"/"+month+"/"+year;
                        dob.setText(date);
                        Byear.setText(String.valueOf(year));
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getfname = fname.getText().toString();
                String getlname = lname.getText().toString();
                String getdob = dob.getText().toString();
                String getemail = email.getText().toString();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                String getpassword = password.getText().toString();
                String getconfirmpassword = confirmpassword.getText().toString();
                if (getfname.equals("")) {
                    fname.setError("enter first name");
                } else if (getlname.equals("")) {
                    lname.setError("enter last name");
                } else if (getdob.equals("")) {
                    Toast.makeText(Registration.this, "Enter Date of Birth", Toast.LENGTH_SHORT).show();
                } else {
                    //byear -> birth year
                    int byear = Integer.parseInt(Byear.getText().toString());
                    if (year - byear > 18) {
                        if (email.getText().toString().trim().matches(emailPattern)){
                        if (getpassword.equals("")) {
                            Toast.makeText(Registration.this, "Password cannot be empty", Toast.LENGTH_SHORT).show();
                        } else {
                                Boolean checkemail = db.checkemail(getemail);
                                if (checkemail == true) {
                                    Toast.makeText(Registration.this, "Email already exist", Toast.LENGTH_SHORT).show();
                                } else {
                                    if (getpassword.equals(getconfirmpassword)) {
                                        db.insertRecord(getfname, getlname, getdob, getemail, getpassword);
                                        Toast.makeText(Registration.this, "Register Successful", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(Registration.this, MainActivity.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(Registration.this, "Password not match", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }else {
                            email.setError("Enter valid email");
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "You are not eligible", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }
}