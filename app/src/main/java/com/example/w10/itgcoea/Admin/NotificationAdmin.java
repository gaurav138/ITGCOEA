package com.example.w10.itgcoea.Admin;

import android.app.DatePickerDialog;
import android.graphics.Typeface;
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

public class NotificationAdmin extends AppCompatActivity {
    DatePickerDialog datePickerDialog;
    String title="",message="";
    RequestQueue rq,rq2;
    String request_url = "http://10.162.11.47/fcmtest1/send_notification.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_admin);
        rq = Volley.newRequestQueue(this);
        rq2 = Volley.newRequestQueue(this);
        final EditText editText=(EditText) findViewById(R.id.editText2);
        final EditText editText2=(EditText) findViewById(R.id.editText4);

        Button b2=(Button) findViewById(R.id.button8);
        Typeface d=Typeface.createFromAsset(getAssets(), "fonts/description.otf");
        b2.setTypeface(d);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                message=editText2.getText().toString();
                title=editText.getText().toString();

                if(message.equals("")||title.equals(""))
                Toast.makeText(NotificationAdmin.this,"Select Parameters",Toast.LENGTH_LONG).show();
                else
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
                        Toast.makeText(NotificationAdmin.this,response,Toast.LENGTH_LONG).show();

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


                params.put("title",title);
                params.put("message",message);
                return params;
            }
        };

        rq2.add(Req);
    }
}

