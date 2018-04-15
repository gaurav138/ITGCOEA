package com.example.w10.itgcoea.UpdateDeleteStudentData;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.w10.itgcoea.R;

import static com.example.w10.itgcoea.R.array.years;
import static com.example.w10.itgcoea.R.id.spinner;

public class SelectYear extends AppCompatActivity {

    Spinner s1;
    String year;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_year);

        s1=(Spinner) findViewById(spinner);
        TextView textView=(TextView) findViewById(R.id.textView5);
        ArrayAdapter<CharSequence> adapter_year = ArrayAdapter.createFromResource(this, years, R.layout.spinner_item);
        adapter_year.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s1.setAdapter(adapter_year);

        s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                year = adapterView.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                year = "second year";

            }
        });



        button=(Button) findViewById(R.id.button6);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SelectYear.this, ShowActivity.class);
                i.putExtra("year", year);
                i.putExtra("value","upstudent");
                startActivity(i);
            }
        });

        Typeface d=Typeface.createFromAsset(getAssets(), "fonts/description.otf");
        button.setTypeface(d);
        textView.setTypeface(d);

    }
}
