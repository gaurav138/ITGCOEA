package com.example.w10.itgcoea.Attendance.TakeAttendance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;

import com.example.w10.itgcoea.Attendance.ViewAttendance.getPercentage;
import com.example.w10.itgcoea.R;

public class TakeViewAttendance extends AppCompatActivity {
    String username,email;
    android.support.v7.widget.GridLayout mainGrid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_view_attendance);
        mainGrid = (android.support.v7.widget.GridLayout) findViewById(R.id.mainGrid);
        //setSingleEvent(mainGrid);
        setSingleEvent(mainGrid);
        Intent ii=getIntent();
        username = ii.getStringExtra("username");
        email=ii.getStringExtra("email");


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

                        Intent intent=new Intent(TakeViewAttendance.this,AttendanceFront.class);
                        intent.putExtra("username", username);
                        intent.putExtra("email", email);
                        startActivity(intent);

                    }
                    else if(iq==1)
                    {
                        Intent i1=new Intent(TakeViewAttendance.this,getPercentage.class);
                        i1.putExtra("username", username);
                        i1.putExtra("email", email);
                        i1.putExtra("fors", "f");
                        startActivity(i1);
                    }

                }
            });
        }
    }

}
