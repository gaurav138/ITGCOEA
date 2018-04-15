package com.example.w10.itgcoea.navigator;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.w10.itgcoea.R;

public class AboutDepartment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_department);

        TextView dd1=(TextView) findViewById(R.id.dddescription1);
        TextView textView=(TextView) findViewById(R.id.title);
        Typeface tm=Typeface.createFromAsset(getAssets(),"fonts/title_main.ttf");
        textView.setTypeface(tm);
        Typeface d=Typeface.createFromAsset(getAssets(), "fonts/description.otf");
        dd1.setTypeface(d);

    }
}
