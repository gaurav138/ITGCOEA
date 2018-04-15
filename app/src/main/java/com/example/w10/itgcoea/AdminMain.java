package com.example.w10.itgcoea;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.w10.itgcoea.Admin.NotificationAdmin;
import com.example.w10.itgcoea.ApproveStudent.AddOrApprove;
import com.example.w10.itgcoea.UpdateFaculty.UpdateStudentOrFaculty;
import com.example.w10.itgcoea.UpdateSyllabus.UpdateTimetable;
import com.example.w10.itgcoea.UpdateSyllabus.UpdatetheSyllabus;
import com.example.w10.itgcoea.navigator.AboutDepartment;
import com.example.w10.itgcoea.navigator.ContactUs;
import com.example.w10.itgcoea.navigator.GallaryActivity;
import com.example.w10.itgcoea.navigator.PlacementActivity;


public class AdminMain extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

     android.support.v7.widget.GridLayout mainGrid;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        mainGrid = (android.support.v7.widget.GridLayout) findViewById(R.id.mainGrid);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Set Event
        setSingleEvent(mainGrid);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view1);
        navigationView.setNavigationItemSelectedListener(this);
    }



    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {


            AlertDialog.Builder builder = new AlertDialog.Builder(AdminMain.this);

            builder.setCancelable(false);
            builder.setTitle("Do you want to Sign Out ?");

            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    Intent intent = new Intent(AdminMain.this, MainActivity.class);
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

                    if(iq==1)
                    {

                        Intent i1=new Intent(AdminMain.this,NotificationAdmin.class);
                        startActivity(i1);
                    }
                    else if(iq==3)
                    {

                        Intent i1=new Intent(AdminMain.this,UpdateStudentOrFaculty.class);
                        startActivity(i1);
                    }
                    if(iq==0)
                    {

                        Intent i1=new Intent(AdminMain.this,AddOrApprove.class);
                        startActivity(i1);
                   }
                    else if(iq==2)
                    {
                        Intent i1=new Intent(AdminMain.this,AdminUploadFor.class);
                        startActivity(i1);
                    }
                    else if(iq==4)
                    {
                        Intent i1=new Intent(AdminMain.this,UpdatetheSyllabus.class);
                        startActivity(i1);
                    }
                    else if(iq==5)
                    {
                        Intent i1=new Intent(AdminMain.this,UpdateTimetable.class);
                        startActivity(i1);
                    }

                }
            });
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
        Toast.makeText(AdminMain.this,"gsdv",Toast.LENGTH_LONG).show();
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
            Intent i1=new Intent(AdminMain.this,GallaryActivity.class);
            startActivity(i1);

        } else if (id == R.id.nav_slideshow) {
            Intent i1=new Intent(AdminMain.this,AboutDepartment.class);
            startActivity(i1);

        }  else if (id == R.id.nav_share) {

            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBodyText = "http://play.google.com/itgcoea";
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject here");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
            startActivity(Intent.createChooser(sharingIntent, "Shearing Option"));

        } else if (id == R.id.nav_send) {
            Intent i1=new Intent(AdminMain.this,ContactUs.class);
            startActivity(i1);


        }
        else if (id == R.id.nav_place) {
            Intent i1=new Intent(AdminMain.this,PlacementActivity.class);
            startActivity(i1);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
