package com.example.w10.itgcoea.UploadViewImage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.w10.itgcoea.AdminStudentDoc;
import com.example.w10.itgcoea.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.w10.itgcoea.R.id.spinner3;

public class ImageOrDoc extends AppCompatActivity {
    Spinner s1, s2, s3;
    String years, sem, sub = "", date = "", username, email;

    android.support.v7.widget.GridLayout mainGrid;

    ArrayList<String> SubjectList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_or_doc);
        SubjectList = new ArrayList<>();
        s3 = (Spinner) findViewById(spinner3);
        mainGrid = (android.support.v7.widget.GridLayout) findViewById(R.id.mainGrid);
       // setSingleEvent(mainGrid);
        setSingleEvent(mainGrid);
        
        Intent ii = getIntent();
        username = ii.getStringExtra("username");
        email = ii.getStringExtra("email");
        spinnerSub2();



    }

    private void setSingleEvent(android.support.v7.widget.GridLayout mainGrid) {
        //Loop all child item of Main Grid
        for (int i = 0; i < mainGrid.getChildCount(); i++) {
            //You can see , all child item is CardView , so we just cast object to CardView
            CardView cardView = (CardView) mainGrid.getChildAt(i);
            final int iq = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(iq==0)
                    {

                        selectYear();
                        Intent i = new Intent(ImageOrDoc.this, UploadImage.class);
                        i.putExtra("year", years);
                        i.putExtra("value","notes");

                        i.putExtra("subject", sub);
                        //  Toast.makeText(getApplicationContext(), sub, Toast.LENGTH_LONG).show();
                        startActivity(i);
                    }
                    else if(iq==1)
                    { selectYear();
                        Intent i = new Intent(ImageOrDoc.this, AdminStudentDoc.class);
                        i.putExtra("year", years);
                        i.putExtra("value","studentdoc");
                        i.putExtra("subject", sub);
                        //  Toast.makeText(getApplicationContext(), sub, Toast.LENGTH_LONG).show();
                        startActivity(i);
                    }

                }
            });
        }
    }

    public void spinnerSub2() {
        String url = "http://10.162.11.47/img/f.php";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONArray jsonArray = new JSONArray(response);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String subject = jsonObject1.getString("Subject");
                        SubjectList.add(subject);
                    }

                    s3.setAdapter(new ArrayAdapter<String>(ImageOrDoc.this, R.layout.spinner_item, SubjectList));
                    s3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                            sub = adapterView.getItemAtPosition(position).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {


                        }
                    });

                    //   Toast.makeText(getApplicationContext(),sub,Toast.LENGTH_LONG).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", username);
                return params;
            }
        };
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }

    public void selectYear() {
        if (sub.equals(""))
            Toast.makeText(ImageOrDoc.this, "Please Select Subject", Toast.LENGTH_LONG).show();

        else {

            if(sub.equals("m3")||sub.equals("java"))
            {
                years="second year";
            }
            else if(sub.equals("operating system design")||sub.equals("toc"))
                years="third year";
            else if(sub.equals("dwdm") || sub.equals("nass"))
                years="fourth year";


        }

    }
}