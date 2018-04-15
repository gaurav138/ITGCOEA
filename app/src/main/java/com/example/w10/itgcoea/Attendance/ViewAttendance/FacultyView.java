package com.example.w10.itgcoea.Attendance.ViewAttendance;

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

public class FacultyView extends AppCompatActivity {

    String startdate,enddate,subject;
    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;

    List<PersonUtils> personUtilsList;

    RequestQueue rq;

    // String request_url = "http://10.162.11.47/recyclerview/info.php"
    String request_url = "http://10.162.11.47/att/viewAttendance.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_view);

        Bundle bundle= getIntent().getExtras();
        startdate=bundle.getString("DATE1");
        enddate=bundle.getString("DATE2");
        subject=bundle.getString("SUBJECT");


        rq = Volley.newRequestQueue(this);

        recyclerView = (RecyclerView) findViewById(R.id.recycleViewContainer);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);

        personUtilsList = new ArrayList<>();

        sendRequest2();

    }

    public void sendRequest2()
    {
        StringRequest Req = new StringRequest(Request.Method.POST,request_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       // Toast.makeText(FacultyView.this,response,Toast.LENGTH_LONG).show();
                        JSONArray array= null;
                        try {
                            array = new JSONArray(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        for(int i = 0; i < array.length(); i++){

                            PersonUtils personUtils = new PersonUtils();

                            try {

                                JSONObject jsonObject = array.getJSONObject(i);

                                personUtils.setPersonFirstName(jsonObject.getString("Rollno"));
                                //   personUtils.setPersonLastName(jsonObject.getString("lastname"));
                                personUtils.setJobProfile(jsonObject.getString("Percentage"));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            personUtilsList.add(personUtils);

                        }

                        mAdapter = new CustomRecyclerAdapter(FacultyView.this, personUtilsList);

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

                params.put("startdate", startdate);
                params.put("enddate",enddate);
                params.put("tablename",subject);
              //  params.put("roll_no","14007004");
                //params.put("table","info");
                return params;
            }
        };

        rq.add(Req);
    }

}