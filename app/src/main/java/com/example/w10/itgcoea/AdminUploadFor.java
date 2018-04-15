package com.example.w10.itgcoea;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;

public class AdminUploadFor extends AppCompatActivity {
    android.support.v7.widget.GridLayout mainGrid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_upload_for);
        mainGrid = (android.support.v7.widget.GridLayout) findViewById(R.id.mainGrid);
        setSingleEvent(mainGrid);


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

                    if(iq==0)
                    {

                        Intent i1=new Intent(AdminUploadFor.this,AdminStudentDoc.class);
                        i1.putExtra("value","admin");
                        startActivity(i1);
                    }
                    else if(iq==1)
                    {
                        Intent i1=new Intent(AdminUploadFor.this,AdminStudentDoc.class);
                        i1.putExtra("value","student");
                        startActivity(i1);
                    }

                }
            });
        }
    }

}
