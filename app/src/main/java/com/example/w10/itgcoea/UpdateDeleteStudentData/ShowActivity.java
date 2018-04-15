package com.example.w10.itgcoea.UpdateDeleteStudentData;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.w10.itgcoea.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowActivity extends AppCompatActivity {

    String year="h";
    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;

    List<StudentList> studentListList;

    RequestQueue rq;
    String request_url = "http://10.162.11.47/update_data/showAll.php",value;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        Intent ii=getIntent();
        value=ii.getStringExtra("value");
        if(value.equals("upstudent"))
        year = ii.getStringExtra("year");
        else
        request_url = "http://10.162.11.47/update_data/showAll2.php";
        rq = Volley.newRequestQueue(this);

        recyclerView = (RecyclerView) findViewById(R.id.recycleViewContainer);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);

        studentListList = new ArrayList<>();

        sendRequest2();

    }

    public void sendRequest2()
    {
        StringRequest Req = new StringRequest(Request.Method.POST,request_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        JSONArray array= null;
                        try {
                            array = new JSONArray(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        for(int i = 0; i < array.length(); i++){

                            StudentList studentList = new StudentList();

                            try {

                                JSONObject jsonObject = array.getJSONObject(i);

                                studentList.setName(jsonObject.getString("name"));
                                studentList.setUsername(jsonObject.getString("username"));
                                studentList.setUser_id(jsonObject.getInt("user_id"));
                                if(value.equals("upstudent"))
                                studentList.setRoll_no(jsonObject.getInt("roll_no"));
                                else
                                {
                                    studentList.setCourses(jsonObject.getString("courses"));
                                    studentList.setQualification(jsonObject.getString("qualification"));
                                }
                                studentList.setEmail(jsonObject.getString("email"));
                                studentList.setPassword(jsonObject.getString("password"));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            studentListList.add(studentList);

                        }

                        if(value.equals("upstudent"))
                        mAdapter = new CustomRecyclerAdapter2(ShowActivity.this, studentListList, year, "stud");
                        else
                            mAdapter = new CustomRecyclerAdapter2(ShowActivity.this, studentListList, "ghd", "fac");

                        recyclerView.setAdapter(mAdapter);
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
                return params;
            }
        };

        rq.add(Req);
    }
    public void onBackPressed(){
        finish();
    }

}