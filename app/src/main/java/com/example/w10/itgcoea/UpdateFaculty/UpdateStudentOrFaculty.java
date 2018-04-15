package com.example.w10.itgcoea.UpdateFaculty;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;

import com.example.w10.itgcoea.R;
import com.example.w10.itgcoea.UpdateDeleteStudentData.SelectYear;
import com.example.w10.itgcoea.UpdateDeleteStudentData.ShowActivity;

public class UpdateStudentOrFaculty extends AppCompatActivity {
    android.support.v7.widget.GridLayout mainGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_student_or_faculty);
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

                        Intent i1=new Intent(UpdateStudentOrFaculty.this,SelectYear.class);

                        startActivity(i1);
                    }
                    else if(iq==1)
                    {
                        Intent i1=new Intent(UpdateStudentOrFaculty.this,ShowActivity.class);

                        i1.putExtra("value","upfaculty");
                        startActivity(i1);
                    }

                }
            });
        }
    }

}
