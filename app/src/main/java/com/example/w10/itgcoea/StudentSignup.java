package com.example.w10.itgcoea;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class StudentSignup extends AppCompatActivity {
    String year="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_signup);

        final EditText etEmail = (EditText) findViewById(R.id.editText6);
        final EditText etName = (EditText) findViewById(R.id.editText1);
        final EditText etUsername = (EditText) findViewById(R.id.editTextName);
        final EditText etPassword = (EditText) findViewById(R.id.editText);

        final EditText etRoll = (EditText) findViewById(R.id.editText3);
        //final EditText etBranch = (EditText) findViewById(R.id.editText4);
        //final EditText etYear = (EditText) findViewById(R.id.editText5);

        Spinner year1 = (Spinner)findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter_year = ArrayAdapter.createFromResource(this, R.array.year1, R.layout.spinner_item);
        adapter_year.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        year1.setAdapter(adapter_year);

        year1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                year = adapterView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                year = "second year";
            }
        });








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



                final int roll = Integer.parseInt(etRoll.getText().toString());
               //final String branch = etBranch.getText().toString();
                //final String year = etYear.getText().toString();

                if(username.isEmpty() || name.isEmpty() || password.isEmpty() || email.isEmpty() || (etRoll.getText().toString()).isEmpty() || year.isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(StudentSignup.this);
                    builder.setMessage("You can't leave any field empty")
                            .setNegativeButton("Retry", null)
                            .create()
                            .show();
                }
                else if (email.indexOf("@")==-1 || email.indexOf(".com")==-1)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(StudentSignup.this);
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
                                    Toast.makeText(StudentSignup.this,"Registration Successful",Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(StudentSignup.this, StudentLogin.class);
                                    StudentSignup.this.startActivity(intent);
                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(StudentSignup.this);
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

                    RegisterRequest registerRequest = new RegisterRequest(name, username, password, roll, year, email, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(StudentSignup.this);
                    queue.add(registerRequest);
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
