package com.example.w10.itgcoea.ApproveStudent;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.w10.itgcoea.AdminMain;
import com.example.w10.itgcoea.FacultySignup;
import com.example.w10.itgcoea.R;

public class AddOrApprove extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_or_approve);

        Button b1 = (Button) findViewById(R.id.button7);
        Button b2 = (Button) findViewById(R.id.button11);
        Typeface d=Typeface.createFromAsset(getAssets(), "fonts/description.otf");
        b1.setTypeface(d);
        b2.setTypeface(d);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1=new Intent(AddOrApprove.this,ApproveMain.class);
                startActivity(i1);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1=new Intent(AddOrApprove.this,FacultySignup.class);
                startActivity(i1);
            }
        });


    }
}
