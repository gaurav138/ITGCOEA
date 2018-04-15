package com.example.w10.itgcoea.UpdateDeleteStudentData;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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

public class UpdateDelete extends AppCompatActivity {

    TextView t1,t2,t3,t4,t5;
    Button b1,b2;
    RequestQueue rq,rq2;
    String username,email,password,name,year;
    int roll_no,user_id;

    String request_url = "http://10.162.11.47/update_data/DeleteRecord.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete);
        rq = Volley.newRequestQueue(this);
        rq2 = Volley.newRequestQueue(this);

        Intent ii=getIntent();
        username = ii.getStringExtra("username");
        email=ii.getStringExtra("email");
        roll_no=ii.getIntExtra("roll_no",-1);
        password=ii.getStringExtra("password");
        name=ii.getStringExtra("name");
        user_id=ii.getIntExtra("user_id",-1);
        year=ii.getStringExtra("year");




        t1=(TextView) findViewById(R.id.textView3);
        t2=(TextView) findViewById(R.id.textView6);
        t3=(TextView) findViewById(R.id.textView7);
        t4=(TextView) findViewById(R.id.textView8);
        t5=(TextView) findViewById(R.id.textView12);

        t1.setText(name);
        t2.setText(username);
        t3.setText(password);
        t4.setText(roll_no+"");
        t5.setText(email);

        b1=(Button) findViewById(R.id.button);
        b2=(Button) findViewById(R.id.button2);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(UpdateDelete.this,UpdateRecord.class);
                intent.putExtra("username",username);
                intent.putExtra("name",name);
                intent.putExtra("password",password);
                intent.putExtra("roll_no",roll_no);
                intent.putExtra("user_id",user_id);
                intent.putExtra("email",email);
                intent.putExtra("year",year);
                startActivity(intent);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            sendRequest2();
        }
    });
}

    public void sendRequest2()
    {
        StringRequest Req = new StringRequest(Request.Method.POST,request_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(UpdateDelete.this,response,Toast.LENGTH_LONG).show();

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
                params.put("user_id",user_id+"");
                return params;
            }
        };

        rq2.add(Req);
    }
    public void onBackPressed(){
        finish();
    }


}
