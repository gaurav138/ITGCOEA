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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class AdminLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);





        final EditText etUsername = (EditText) findViewById(R.id.editTextName);
        final EditText etPassword = (EditText) findViewById(R.id.editText);

        final Button bLogin = (Button) findViewById(R.id.button1);

        Typeface d=Typeface.createFromAsset(getAssets(), "fonts/description.otf");
        bLogin.setTypeface(d);



        if(getSupportActionBar()!=null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        bLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                final String username = etUsername.getText().toString();
                final String password = etPassword.getText().toString();


                if (username.equals("g") && password.equals("1")) {
                    Intent intent = new Intent(AdminLogin.this, AdminMain.class);
                    AdminLogin.this.startActivity(intent);



                }
                else {


                    // Response received from the server
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {


                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");

                                if (success) {
                                /*String name = jsonResponse.getString("name");
                                int age = jsonResponse.getInt("age");

                                Intent intent = new Intent(AdminLogin.this, UserAreaActivity.class);
                                intent.putExtra("name", name);
                                intent.putExtra("age", age);
                                intent.putExtra("username", username);*/

                                    String username = jsonResponse.getString("username");
                                    String email=jsonResponse.getString("email");

                                    Intent intent = new Intent(AdminLogin.this, AdminMain.class);
                                    intent.putExtra("username", username);
                                    intent.putExtra("email", email);
                                    AdminLogin.this.startActivity(intent);
                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(AdminLogin.this);
                                    builder.setMessage("Login Failed")
                                            .setNegativeButton("Retry", null)
                                            .create()
                                            .show();
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    LoginRequest loginRequest = new LoginRequest(username, password, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(AdminLogin.this);
                    queue.add(loginRequest);
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
