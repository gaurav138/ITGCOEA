package com.example.w10.itgcoea;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.w10.itgcoea.Admin.DBNotification;
import com.example.w10.itgcoea.Admin.Notifylist;
import com.example.w10.itgcoea.Admin.SQLiteDbHandler;
import com.example.w10.itgcoea.Attendance.ViewAttendance.getPercentage2;
import com.example.w10.itgcoea.DiscussionForum.StudentDiscussion;
import com.example.w10.itgcoea.UpdateSyllabus.ViewSyllabus;
import com.example.w10.itgcoea.UpdateSyllabus.ViewTimetable;
import com.example.w10.itgcoea.navigator.AboutDepartment;
import com.example.w10.itgcoea.navigator.ContactUs;
import com.example.w10.itgcoea.navigator.GallaryActivity;
import com.example.w10.itgcoea.navigator.PlacementActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentMain extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    String request_url = "http://10.162.11.47/update_data/getNotify.php";
    android.support.v7.widget.GridLayout mainGrid;
    RequestQueue rq,rq2;
    String date,message,title;
    List<Notifylist> notifylistList;
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_main);
        mainGrid = (android.support.v7.widget.GridLayout) findViewById(R.id.mainGrid);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       /* try {
            showNotification();
        } catch (SQLException e) {
            e.printStackTrace();
        }*/


        rq = Volley.newRequestQueue(this);
        rq2 = Volley.newRequestQueue(this);
        //Set Event
        notifylistList = new ArrayList<>();
        setSingleEvent(mainGrid);

            Intent ii=getIntent();
             username = ii.getStringExtra("username");
            String email=ii.getStringExtra("email");
            String name=ii.getStringExtra("name");
            int roll_no=ii.getIntExtra("roll_no",-1);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

      //  sendRequest2();

        getToken();

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
                    Intent ii=getIntent();
                    String username = ii.getStringExtra("username");
                    String email=ii.getStringExtra("email");
                    String name=ii.getStringExtra("name");
                    int roll_no=ii.getIntExtra("roll_no",-1);

                    if(iq==2)
                    {
                        Intent i1=new Intent(StudentMain.this,StudentNotices.class);
                        i1.putExtra("value","asdfg");
                        startActivity(i1);

                    }
                    else if(iq==3)
                    {
                        Intent io=new Intent(StudentMain.this,ViewImageOrDoc.class);
                        io.putExtra("username", username);
                        io.putExtra("email", email);
                        io.putExtra("roll_no", roll_no+"");
                        startActivity(io);

                    }
                    else if(iq==0)
                    {
                        Intent io=new Intent(StudentMain.this,getPercentage2.class);
                        io.putExtra("username", username);
                        io.putExtra("email", email);
                        io.putExtra("fors", "s");
                        io.putExtra("roll_no", roll_no);
                        startActivity(io);

                    }
                    else if(iq==1)
                    {
                        Intent io=new Intent(StudentMain.this,StudentDiscussion.class);
                        io.putExtra("username", username);
                        io.putExtra("email", email);
                        io.putExtra("name", name);
                        io.putExtra("roll_no", roll_no+"");
                        startActivity(io);

                    }
                    else if(iq==4)
                    {
                        Intent io=new Intent(StudentMain.this,ViewSyllabus.class);

                        startActivity(io);

                    }
                    else if(iq==5)
                    {
                        Intent io=new Intent(StudentMain.this,ViewTimetable.class);

                        startActivity(io);

                    }
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            AlertDialog.Builder builder = new AlertDialog.Builder(StudentMain.this);

            builder.setCancelable(false);
            builder.setTitle("Do you want to Sign Out ?");

            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    Intent intent = new Intent(StudentMain.this, MainActivity.class);
                    intent.putExtra("finish", true); // if you are checking for this in your other Activities
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                            Intent.FLAG_ACTIVITY_CLEAR_TASK |
                            Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
            });
            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();

                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();

        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_gallery) {
            Intent i1=new Intent(StudentMain.this,GallaryActivity.class);
            startActivity(i1);

        } else if (id == R.id.nav_slideshow) {
            Intent i1=new Intent(StudentMain.this,AboutDepartment.class);
            startActivity(i1);

        }  else if (id == R.id.nav_share) {

            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBodyText = "http://play.google.com/itgcoea";
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject here");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
            startActivity(Intent.createChooser(sharingIntent, "Shearing Option"));

        } else if (id == R.id.nav_send) {
            Intent i1=new Intent(StudentMain.this,ContactUs.class);
            startActivity(i1);


        }
        else if (id == R.id.nav_place) {
            Intent i1=new Intent(StudentMain.this,PlacementActivity.class);
            startActivity(i1);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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

                        SQLiteDbHandler db = new SQLiteDbHandler(getApplicationContext());
                        try {
                            db.open();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        for(int i = 0; i < array.length(); i++){

                            Notifylist notifylist = new Notifylist();

                            try {

                                JSONObject jsonObject = array.getJSONObject(i);
                                Log.d("value :", String.valueOf(jsonObject));

                                DBNotification dbNotification=new DBNotification();
                                dbNotification.title=jsonObject.getString("title");
                                dbNotification.date=jsonObject.getString("date");
                                dbNotification.message=jsonObject.getString("message");
                               // dbNotification.id=jsonObject.getInt("int");

                                db.createEventEntry(dbNotification);

                                DBNotification d[] = db.getContactData();
                                String s="";
                                for (int j = 0;j<d.length;j++){
                                    s=s+d[j].title+" "+d[j].message+ " "+d[j].date+" "+d[j].status+"\n";

                                }
                              //  Toast.makeText(StudentMain.this,s,Toast.LENGTH_LONG).show();


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                            //notifylistList.add(notifylist);

                        } db.close();



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

                params.put("year","");

                return params;
            }
        };

        rq2.add(Req);
    }

    public void showNotification() throws SQLException {

        SQLiteDbHandler db = new SQLiteDbHandler(getApplicationContext());
        db.open();
        DBNotification d[] = db.getContactData();
        String title = d[d.length-1].title;
        String msg = d[d.length-1].message;
        db.close();

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.logo1)
                        .setContentTitle(title)
                        .setContentText(msg);

        Intent notificationIntent = new Intent(this, StudentMain.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());

    }


    public void getToken()
    {
        String app_server_url = "http://10.162.11.47/fcmtest1/fcm_insert.php";
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.FCM_PREF), Context.MODE_PRIVATE);
        final String token = sharedPreferences.getString(getString(R.string.FCM_TOKEN), "");
        //Toast.makeText(StudentMain.this,token+"y",Toast.LENGTH_LONG).show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, app_server_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("fcm_token", token);
                params.put("user_name",username);
                //params.put("email",em);
                return params;
            }
        };
        MySingleton.getmInstance(StudentMain.this).addToRequestque(stringRequest);
        //Toast.makeText(StudentMain.this,"You have Registered Successfully...",Toast.LENGTH_LONG).show();
    }
}

