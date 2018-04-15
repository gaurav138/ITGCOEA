package com.example.w10.itgcoea;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class FacultySignup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_signup);

        final EditText etEmail = (EditText) findViewById(R.id.editText6);
        final EditText etName = (EditText) findViewById(R.id.editText1);
        final EditText etUsername = (EditText) findViewById(R.id.editTextName);
        final EditText etPassword = (EditText) findViewById(R.id.editText);

        final EditText etQualification = (EditText) findViewById(R.id.editText3);
       // final EditText etDepartment = (EditText) findViewById(R.id.editText4);
        final EditText etCourses = (EditText) findViewById(R.id.editText5);




        if(getSupportActionBar()!=null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        final Button bRegister = (Button) findViewById(R.id.button2);
        Typeface d=Typeface.createFromAsset(getAssets(), "fonts/description.otf");
        bRegister.setTypeface(d);

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = etName.getText().toString();
                final String username = etUsername.getText().toString();
                final String email = etEmail.getText().toString();
                final String password = etPassword.getText().toString();

                final String qualification = etQualification.getText().toString();
              //  final String department = etDepartment.getText().toString();
                final String courses = etCourses.getText().toString();

                if(username.isEmpty() || name.isEmpty() || password.isEmpty() || email.isEmpty() || qualification.isEmpty()  || courses.isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(FacultySignup.this);
                    builder.setMessage("You can't leave any field empty")
                            .setNegativeButton("Retry", null)
                            .create()
                            .show();
                }
                else if (email.indexOf("@")==-1 || email.indexOf(".com")==-1)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(FacultySignup.this);
                    builder.setMessage("Please enter a valid email")
                            .setNegativeButton("Retry", null)
                            .create()
                            .show();

                }

                else
                {

                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");
                                if (success) {
                                    Toast.makeText(FacultySignup.this,"Registration Successful",Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(FacultySignup.this, AdminMain.class);
                                    FacultySignup.this.startActivity(intent);
                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(FacultySignup.this);
                                    builder.setMessage("User name already exists...!")
                                            .setNegativeButton("Retry", null)
                                            .create()
                                            .show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    RegisterRequest1 registerRequest1 = new RegisterRequest1(name, username, email, password, qualification, courses, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(FacultySignup.this);
                    queue.add(registerRequest1);
                }
            }
        });
        
        
        

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}
