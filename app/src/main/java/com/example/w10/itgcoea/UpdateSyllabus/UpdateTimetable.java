package com.example.w10.itgcoea.UpdateSyllabus;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.w10.itgcoea.R;
import com.example.w10.itgcoea.UploadViewImage.DataAdapter;
import com.example.w10.itgcoea.UploadViewImage.RecyclerViewAdapter;
import com.example.w10.itgcoea.UploadViewImage.UploadImage;
import com.example.w10.itgcoea.UploadViewImage.ViewImage2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class UpdateTimetable extends AppCompatActivity {

    List<DataAdapter> ListOfdataAdapter;

    RecyclerView recyclerView;
    RequestQueue rq;
    String HTTP_JSON_URL = "http://10.162.11.47/LoadImageText/ImageJsonData2.php";

    String Image_Name_JSON = "image_name";
    String username,email,roll_no;

    String Image_URL_JSON = "image_path";

    JsonArrayRequest RequestOfJSonArray ;

    RequestQueue requestQueue ;

    View view ;
    Button button;

    int RecyclerViewItemPosition ;

    RecyclerView.LayoutManager layoutManagerOfrecyclerView;

    RecyclerView.Adapter recyclerViewadapter;

    ArrayList<String> ImageTitleNameArrayListForClick;
    ArrayList<String> ImageUrlArrayListForClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_update_timetable);

        ImageTitleNameArrayListForClick = new ArrayList<>();
        ImageUrlArrayListForClick= new ArrayList<>();
        ListOfdataAdapter = new ArrayList<>();
        rq = Volley.newRequestQueue(this);
        button=(Button) findViewById(R.id.button9);

        Typeface d=Typeface.createFromAsset(getAssets(), "fonts/description.otf");
        button.setTypeface(d);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview1);

        recyclerView.setHasFixedSize(true);

        layoutManagerOfrecyclerView = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManagerOfrecyclerView);

        sendRequest2();

        // Implementing Click Listener on RecyclerView.
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

            GestureDetector gestureDetector = new GestureDetector(UpdateTimetable.this, new GestureDetector.SimpleOnGestureListener() {

                @Override public boolean onSingleTapUp(MotionEvent motionEvent) {

                    return true;
                }

            });
            @Override
            public boolean onInterceptTouchEvent(RecyclerView Recyclerview, MotionEvent motionEvent) {

                view = Recyclerview.findChildViewUnder(motionEvent.getX(), motionEvent.getY());

                if(view != null && gestureDetector.onTouchEvent(motionEvent)) {

                    //Getting RecyclerView Clicked Item value.
                    RecyclerViewItemPosition = Recyclerview.getChildAdapterPosition(view);
                    Intent i = new Intent(UpdateTimetable.this,ViewImage2.class);

                    i.putExtra("url", ImageUrlArrayListForClick.get(RecyclerViewItemPosition));

                    startActivity(i);

                    // Showing RecyclerView Clicked Item value using Toast.
                    Toast.makeText(UpdateTimetable.this, ImageTitleNameArrayListForClick.get(RecyclerViewItemPosition), Toast.LENGTH_SHORT).show();
                }

                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView Recyclerview, MotionEvent motionEvent) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1=new Intent(UpdateTimetable.this,UploadImage.class);
                i1.putExtra("value","timetable");
                startActivity(i1);
            }
        });

    }

    public void JSON_HTTP_CALL(){

        RequestOfJSonArray = new JsonArrayRequest(HTTP_JSON_URL,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        ParseJSonResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        requestQueue = Volley.newRequestQueue(UpdateTimetable.this);

        requestQueue.add(RequestOfJSonArray);
    }

    public void sendRequest2()
    {
        StringRequest Req = new StringRequest(Request.Method.POST,HTTP_JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        JSONArray array= null;
                        try {
                            array = new JSONArray(response);
                            ParseJSonResponse(array);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

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

                params.put("year","hd");
                return params;
            }
        };

        rq.add(Req);
    }
    public void onBackPressed(){
        finish();
    }


    public void ParseJSonResponse(JSONArray array){

        for(int i = 0; i<array.length(); i++) {

            DataAdapter GetDataAdapter2 = new DataAdapter();

            JSONObject json = null;
            try {

                json = array.getJSONObject(i);

                GetDataAdapter2.setImageTitle(json.getString(Image_Name_JSON));
                GetDataAdapter2.setImageDate(json.getString("date"));


                // Adding image title name in array to display on RecyclerView click event.
                ImageTitleNameArrayListForClick.add(json.getString(Image_Name_JSON));
                ImageUrlArrayListForClick.add(json.getString(Image_URL_JSON));
                GetDataAdapter2.setImageUrl(json.getString(Image_URL_JSON));


            } catch (JSONException e) {

                e.printStackTrace();
            }
            ListOfdataAdapter.add(GetDataAdapter2);
        }

        recyclerViewadapter = new RecyclerViewAdapter(ListOfdataAdapter, this);

        recyclerView.setAdapter(recyclerViewadapter);
    }
}