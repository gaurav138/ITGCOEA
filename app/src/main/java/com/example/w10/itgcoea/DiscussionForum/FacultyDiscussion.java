package com.example.w10.itgcoea.DiscussionForum;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.w10.itgcoea.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class FacultyDiscussion extends AppCompatActivity {

    Spinner mspinner;
    String year;

    ArrayList<String> SubjectList;
    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> list_of_rooms = new ArrayList<>();
    private String name,room_name,email,username;
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference().getRoot();
    private DatabaseReference root1 = FirebaseDatabase.getInstance().getReference().child("first year");
    private DatabaseReference root2 = FirebaseDatabase.getInstance().getReference().child("second year");
    private DatabaseReference root3 = FirebaseDatabase.getInstance().getReference().child("third year");
    private DatabaseReference root4 = FirebaseDatabase.getInstance().getReference().child("fourth year");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_discussion);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        SubjectList=new ArrayList<>();

        listView = (ListView) findViewById(R.id.listView);

        Intent ii=getIntent();
        username = ii.getStringExtra("username");
        email=ii.getStringExtra("email");
        name=ii.getStringExtra("name");

        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list_of_rooms);
        spinnerSub2();
        listView.setAdapter(arrayAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(getApplicationContext(),Chat_Room.class);
                intent.putExtra("room_name",((TextView)view).getText().toString() );
                intent.putExtra("user_name",name);
                startActivity(intent);
            }
        });



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog();
            }
        });

    }

    public void alertDialog()
    {
        AlertDialog.Builder mbuilder = new AlertDialog.Builder(this);
        mbuilder.setTitle("Select Subject:");
        View mView=getLayoutInflater().inflate(R.layout.dialog_spinner,null);
        mspinner=(Spinner) mView.findViewById(R.id.spinner);

        mspinner.setAdapter(new ArrayAdapter<String>(FacultyDiscussion.this, R.layout.spinner_item, SubjectList));
        mspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                room_name = adapterView.getItemAtPosition(position).toString();
                if(room_name.equals("m3")||room_name.equals("java"))
                {
                    year="second year";
                }
                else if(room_name.equals("operating system design")||room_name.equals("toc"))
                    year="third year";
                else if(room_name.equals("dwdm") || room_name.equals("nass"))
                    year="fourth year";


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {


            }
        });



        mbuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Map<String,Object> map = new HashMap<String, Object>();
                map.put(room_name,"");
                if(year.equals("first year"))
                    root1.updateChildren(map);
                else if(year.equals("second year"))
                    root2.updateChildren(map);
                else if(year.equals("third year"))
                    root3.updateChildren(map);
                else if(year.equals("fourth year"))
                    root4.updateChildren(map);


            }
        });

        mbuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();

            }
        });
        mbuilder.setView(mView);
        AlertDialog dialog=mbuilder.create();
        dialog.show();

    }

    public void spinnerSub2()
    {
        SubjectList.clear();
        String url="http://10.162.11.47/img/f.php";
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{

                    JSONArray jsonArray=new JSONArray(response);

                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject1=jsonArray.getJSONObject(i);
                        String subject=jsonObject1.getString("Subject");
                        SubjectList.add(subject);
                    }

                    //  Toast.makeText(getApplicationContext(),"spinner"+SubjectList+"",Toast.LENGTH_LONG).show();
                    showData();
                }catch (JSONException e){e.printStackTrace();}
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username",username);
                return params;
            }
        }
                ;
        requestQueue.add(stringRequest);
    }


    public void showData()
    {

        // Toast.makeText(FacultyDiscussion.this,"showdata"+SubjectList+"",Toast.LENGTH_LONG).show();

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Set<String> set = new HashSet<String>();
                for (DataSnapshot ds : dataSnapshot.getChildren())
                {
                    for (DataSnapshot ds1 : ds.getChildren())
                    {
                        String name = ds1.getKey();
                        if (SubjectList.contains(name))
                            set.add(name);
                    }

                }

                // Toast.makeText(FacultyDiscussion.this,"set"+set+"",Toast.LENGTH_LONG).show();
                list_of_rooms.clear();
                list_of_rooms.addAll(set);
                // Toast.makeText(FacultyDiscussion.this,"roomlist"+list_of_rooms+"", Toast.LENGTH_LONG).show();


                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });


    }

}
