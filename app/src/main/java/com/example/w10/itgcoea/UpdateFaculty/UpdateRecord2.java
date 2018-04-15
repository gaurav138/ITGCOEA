package com.example.w10.itgcoea.UpdateFaculty;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.w10.itgcoea.R;

import java.util.HashMap;
import java.util.Map;

public class UpdateRecord2 extends AppCompatActivity {
    EditText e1,e2,e3,e4,e5,e6;
    String username,email,password,name,year,qualification,courses;
    int roll_no,user_id;
    String request_url = "http://10.162.11.47/update_data/UpdateRecord2.php";
    RequestQueue rq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_record2);
        rq = Volley.newRequestQueue(this);

        e1=(EditText) findViewById(R.id.editText3);
        e2=(EditText) findViewById(R.id.editText6);
        e3=(EditText) findViewById(R.id.editText7);
        e4=(EditText) findViewById(R.id.editText8);
        e5=(EditText) findViewById(R.id.editText12);
        e6=(EditText) findViewById(R.id.editText14);

        Intent ii=getIntent();
        username = ii.getStringExtra("username");
        email=ii.getStringExtra("email");
        courses=ii.getStringExtra("courses");
        password=ii.getStringExtra("password");
        name=ii.getStringExtra("name");
        user_id=ii.getIntExtra("user_id",-1);
        qualification=ii.getStringExtra("qualification");

        e1.setText(name);
        e2.setText(username);
        e3.setText(password);
        e4.setText(courses);
        e5.setText(email);
        e6.setText(qualification);

        Button button=(Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendRequest1();
            }
        });
    }
    public void sendRequest1()
    {
        StringRequest Req = new StringRequest(Request.Method.POST,request_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(UpdateRecord2.this,response,Toast.LENGTH_LONG).show();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Volley Error: ", String.valueOf(error));

            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();

                params.put("qualification",e6.getText().toString());
                params.put("username",e2.getText().toString());
                params.put("name",e1.getText().toString());
                params.put("email",e5.getText().toString());
                params.put("password",e3.getText().toString());
                params.put("user_id",user_id+"");
                params.put("courses",e4.getText().toString());
                return params;
            }
        };

        rq.add(Req);
    }

    public void onBackPressed(){
        finish();
    }

}
