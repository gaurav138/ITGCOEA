package com.example.w10.itgcoea;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.Toast;

import com.example.w10.itgcoea.UploadViewImage.ViewImage;

public class ViewImageOrDoc extends ActivityGroup {
    String username,email,roll_no,year="";
    TabHost tabHost;
    TabHost.TabSpec spec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image_or_doc);

        Intent ii=getIntent();
        username = ii.getStringExtra("username");
        email=ii.getStringExtra("email");
        roll_no=ii.getStringExtra("roll_no");

        if (roll_no.contains("16007")||roll_no.contains("17107"))
            year = "second year";
        else if (roll_no.contains("15007")||roll_no.contains("16107"))
            year = "third year";
        else if (roll_no.contains("14007")||roll_no.contains("15107"))
            year = "fourth year";
        else
            Toast.makeText(ViewImageOrDoc.this,"Roll no is not valid",Toast.LENGTH_SHORT).show();

        tabHost= (TabHost) findViewById(R.id.tabhost);
        tabHost.setup(getLocalActivityManager());


        spec=tabHost.newTabSpec("one");
        spec.setIndicator("Images");
        Intent i1=new Intent(ViewImageOrDoc.this,ViewImage.class);
        i1.putExtra("year",year);

        spec.setContent(i1);
        tabHost.addTab(spec);

        spec=tabHost.newTabSpec("two");
        spec.setIndicator("Pdfs");
        Intent i2=new Intent(ViewImageOrDoc.this,StudentNotices.class);
        i2.putExtra("value","studentdoc");
        i2.putExtra("year",year);
        spec.setContent(i2);
        tabHost.addTab(spec);




    }

}
