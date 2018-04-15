package com.example.w10.itgcoea.UpdateDeleteStudentData;


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

public class UpdateRecord extends AppCompatActivity {

    EditText e1,e2,e3,e4,e5;
    String username,email,password,name,year;
    int roll_no,user_id;
    String request_url = "http://10.162.11.47/update_data/UpdateRecord.php";
    RequestQueue rq;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_record);
        rq = Volley.newRequestQueue(this);

        e1=(EditText) findViewById(R.id.editText3);
        e2=(EditText) findViewById(R.id.editText6);
        e3=(EditText) findViewById(R.id.editText7);
        e4=(EditText) findViewById(R.id.editText8);
        e5=(EditText) findViewById(R.id.editText12);

        Intent ii=getIntent();
        username = ii.getStringExtra("username");
        email=ii.getStringExtra("email");
        roll_no=ii.getIntExtra("roll_no",-1);
        password=ii.getStringExtra("password");
        name=ii.getStringExtra("name");
        user_id=ii.getIntExtra("user_id",-1);
        year=ii.getStringExtra("year");

        e1.setText(name);
        e2.setText(username);
        e3.setText(password);
        e4.setText(roll_no+"");
        e5.setText(email);

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
                        Toast.makeText(UpdateRecord.this,response,Toast.LENGTH_LONG).show();

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

                params.put("year",year);
                params.put("username",e2.getText().toString());
                params.put("name",e1.getText().toString());
                params.put("email",e5.getText().toString());
                params.put("password",e3.getText().toString());
                params.put("user_id",user_id+"");
                params.put("roll_no",Integer.parseInt(e4.getText().toString())+"");
                return params;
            }
        };

        rq.add(Req);
    }

    public void onBackPressed(){
        finish();
    }


}
