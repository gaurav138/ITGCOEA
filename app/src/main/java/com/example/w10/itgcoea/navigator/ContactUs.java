package com.example.w10.itgcoea.navigator;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.w10.itgcoea.R;

public class ContactUs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        TextView t1=(TextView) findViewById(R.id.title1);
        TextView t2=(TextView) findViewById(R.id.title2);
        TextView t3=(TextView) findViewById(R.id.title3);
        TextView t4=(TextView) findViewById(R.id.title4);
        TextView t5=(TextView) findViewById(R.id.title5);
        TextView t6=(TextView) findViewById(R.id.title6);
        TextView t7=(TextView) findViewById(R.id.title7);
        TextView t8=(TextView) findViewById(R.id.title8);
        TextView t9=(TextView) findViewById(R.id.title9);

        TextView d1=(TextView) findViewById(R.id.description1);
        TextView d2=(TextView) findViewById(R.id.description2);
        TextView d3=(TextView) findViewById(R.id.description3);
        TextView d4=(TextView) findViewById(R.id.description4);
        TextView d5=(TextView) findViewById(R.id.description5);
        TextView d6=(TextView) findViewById(R.id.description6);
        TextView d7=(TextView) findViewById(R.id.description7);
        TextView d8=(TextView) findViewById(R.id.description8);
        TextView d9=(TextView) findViewById(R.id.description9);

        Typeface t=Typeface.createFromAsset(getAssets(), "fonts/title.otf");
        Typeface d=Typeface.createFromAsset(getAssets(), "fonts/description.otf");


        t1.setTypeface(t);
        t2.setTypeface(t);
        t3.setTypeface(t);
        t4.setTypeface(t);
        t5.setTypeface(t);
        t6.setTypeface(t);
        t7.setTypeface(t);
        t8.setTypeface(t);
        t9.setTypeface(t);

        /*d1.setTypeface(d);
        d2.setTypeface(d);
        d3.setTypeface(d);
        d4.setTypeface(d);
        d5.setTypeface(d);
        d6.setTypeface(d);
        d7.setTypeface(d);
        d8.setTypeface(d);
        d9.setTypeface(d);*/

    }
}
