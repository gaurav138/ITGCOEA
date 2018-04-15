package com.example.w10.itgcoea.Attendance.TakeAttendance;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.w10.itgcoea.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static com.example.w10.itgcoea.R.id.spinner3;

public class AttendanceFront extends AppCompatActivity {
    Spinner s1,s2,s3;
    String years,sem,sub="",date="",username,email;
    Button b1,b2;
    DatePickerDialog datePickerDialog;
    ArrayList<String> SubjectList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_front);
        SubjectList=new ArrayList<>();

        /*s1=(Spinner) findViewById(spinner);
        s2=(Spinner) findViewById(spinner2);*/
        s3=(Spinner) findViewById(spinner3);
        TextView textView=(TextView) findViewById(R.id.textView7) ;
        b1=(Button) findViewById(R.id.button);
        b2=(Button) findViewById(R.id.button2);

        Typeface d=Typeface.createFromAsset(getAssets(), "fonts/description.otf");
        b1.setTypeface(d);
        b2.setTypeface(d);
        textView.setTypeface(d);

        Intent ii=getIntent();
         username = ii.getStringExtra("username");
         email=ii.getStringExtra("email");



        spinnerSub2();

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(AttendanceFront.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                b1.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);
                                date=b1.getText().toString();

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(date.equals(""))
                    Toast.makeText(AttendanceFront.this,"Please Select Date",Toast.LENGTH_LONG).show();
                else if(sub.equals(""))
                    Toast.makeText(AttendanceFront.this,"Please Select Subject",Toast.LENGTH_LONG).show();

                else  {

                    if(sub.equals("m3")||sub.equals("java"))
                    {
                        years="second year";
                    }
                    else if(sub.equals("operating system design")||sub.equals("toc"))
                        years="third year";
                    else if(sub.equals("dwdm") || sub.equals("nass"))
                        years="fourth year";


                        Intent i = new Intent(AttendanceFront.this, AttendanceActivity.class);
                        i.putExtra("YEAR", years);
                        i.putExtra("DATE", date);
                        i.putExtra("SUBJECT", sub);
                      //  Toast.makeText(getApplicationContext(), sub, Toast.LENGTH_LONG).show();
                        startActivity(i);


                }
            }
        });
    }


    public void spinnerSub2()
    {
        String url="http://10.162.11.47/img/f.php";
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{

                    JSONArray jsonArray=new JSONArray(response);

                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject1=jsonArray.getJSONObject(i);
                        String subject=jsonObject1.getString("Subject");
                        SubjectList.add(subject);
                    }

                    s3.setAdapter(new ArrayAdapter<String>(AttendanceFront.this, R.layout.spinner_item, SubjectList));
                    s3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                            sub = adapterView.getItemAtPosition(position).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {


                        }
                    });

                    //   Toast.makeText(getApplicationContext(),sub,Toast.LENGTH_LONG).show();

                }catch (JSONException e){e.printStackTrace();}
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username",username);
                return params;
            }
        }


                ;
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }
}
