package com.example.w10.itgcoea;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.w10.itgcoea.navigator.AboutDepartment;
import com.example.w10.itgcoea.navigator.ContactUs;
import com.example.w10.itgcoea.navigator.GallaryActivity;
import com.example.w10.itgcoea.navigator.PlacementActivity;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        Button b1=(Button) findViewById(R.id.button1) ;
        Button b2=(Button) findViewById(R.id.button2) ;
        Button b3=(Button) findViewById(R.id.button3) ;

               b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,AdminLogin.class);
                startActivity(i);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,FacultyLogin.class);
                startActivity(i);
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,StudentLogin.class);
                startActivity(i);
            }
        });

        Typeface d=Typeface.createFromAsset(getAssets(), "fonts/description.otf");
        b1.setTypeface(d);
        b2.setTypeface(d);
        b3.setTypeface(d);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);

        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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
             Intent i1=new Intent(MainActivity.this,GallaryActivity.class);
             startActivity(i1);

        } else if (id == R.id.nav_slideshow) {
             Intent i1=new Intent(MainActivity.this,AboutDepartment.class);
             startActivity(i1);

        }  else if (id == R.id.nav_share) {

             Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
             sharingIntent.setType("text/plain");
             String shareBodyText = "http://play.google.com/itgcoea";
             sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject here");
             sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
             startActivity(Intent.createChooser(sharingIntent, "Shearing Option"));

         } else if (id == R.id.nav_send) {
             Intent i1=new Intent(MainActivity.this,ContactUs.class);
             startActivity(i1);


        }
         else if (id == R.id.nav_place) {
             Intent i1=new Intent(MainActivity.this,PlacementActivity.class);
             startActivity(i1);
         }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
