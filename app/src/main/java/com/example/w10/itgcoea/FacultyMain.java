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
import android.support.v7.widget.GridLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.w10.itgcoea.Admin.NotificationAdmin;
import com.example.w10.itgcoea.Attendance.TakeAttendance.TakeViewAttendance;
import com.example.w10.itgcoea.DiscussionForum.FacultyDiscussion;
import com.example.w10.itgcoea.UploadViewImage.ImageOrDoc;
import com.example.w10.itgcoea.navigator.AboutDepartment;
import com.example.w10.itgcoea.navigator.ContactUs;
import com.example.w10.itgcoea.navigator.GallaryActivity;
import com.example.w10.itgcoea.navigator.PlacementActivity;

public class FacultyMain extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //GridLayout mainGrid;
    GridLayout mainGrid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_main);
        Intent ii = getIntent();
        String username = ii.getStringExtra("username");
        String email = ii.getStringExtra("email");
       // mainGrid = (GridLayout) findViewById(R.id.mainGrid);
        mainGrid=(GridLayout) findViewById(R.id.mainGrid);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Set Event
      //  setSingleEvent(mainGrid);
        setSingleEvent(mainGrid);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    private void setSingleEvent(GridLayout mainGrid) {
        //Loop all child item of Main Grid
        for (int i = 0; i < mainGrid.getChildCount(); i++) {
            //You can see , all child item is CardView , so we just cast object to CardView
            CardView cardView = (CardView) mainGrid.getChildAt(i);
            final int iq = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent ii = getIntent();
                    String username = ii.getStringExtra("username");
                    String email = ii.getStringExtra("email");
                    String name = ii.getStringExtra("name");

                    if (iq == 0) {
                        Intent i1 = new Intent(FacultyMain.this, TakeViewAttendance.class);
                        i1.putExtra("username", username);
                        i1.putExtra("email", email);
                        startActivity(i1);

                    }
                    if(iq==1)
                    {

                        Intent i1=new Intent(FacultyMain.this,NotificationAdmin.class);
                        startActivity(i1);
                    }
                    else if (iq == 5) {
                        Intent i1 = new Intent(FacultyMain.this, StudentNotices.class);
                        i1.putExtra("username", username);
                        i1.putExtra("email", email);
                        i1.putExtra("value","student");
                        startActivity(i1);
                    } else if (iq == 4) {
                        Intent i1 = new Intent(FacultyMain.this, AdminStudentDoc.class);
                        i1.putExtra("value", "faculty");
                        startActivity(i1);
                    } else if (iq == 2) {
                        Intent i1 = new Intent(FacultyMain.this, ImageOrDoc.class);
                        i1.putExtra("username", username);
                        i1.putExtra("email", email);
                        startActivity(i1);
                    }
                    if (iq == 3) {
                        Intent i1 = new Intent(FacultyMain.this, FacultyDiscussion.class);
                        i1.putExtra("username", username);
                        i1.putExtra("email", email);
                        i1.putExtra("name", name);
                        startActivity(i1);

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

            AlertDialog.Builder builder = new AlertDialog.Builder(FacultyMain.this);

            builder.setCancelable(false);
            builder.setTitle("Do you want to Sign Out ?");

            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    Intent intent = new Intent(FacultyMain.this, MainActivity.class);
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
            Intent i1=new Intent(FacultyMain.this,GallaryActivity.class);
            startActivity(i1);

        } else if (id == R.id.nav_slideshow) {
            Intent i1=new Intent(FacultyMain.this,AboutDepartment.class);
            startActivity(i1);

        }  else if (id == R.id.nav_share) {

            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBodyText = "http://play.google.com/itgcoea";
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject here");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
            startActivity(Intent.createChooser(sharingIntent, "Shearing Option"));

        } else if (id == R.id.nav_send) {
            Intent i1=new Intent(FacultyMain.this,ContactUs.class);
            startActivity(i1);


        }
        else if (id == R.id.nav_place) {
            Intent i1=new Intent(FacultyMain.this,PlacementActivity.class);
            startActivity(i1);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
