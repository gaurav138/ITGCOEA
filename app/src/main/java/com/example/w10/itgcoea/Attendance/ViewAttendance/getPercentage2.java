package com.example.w10.itgcoea.Attendance.ViewAttendance;

import android.app.DatePickerDialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.w10.itgcoea.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static com.example.w10.itgcoea.R.id.spinner;

public class getPercentage2 extends AppCompatActivity implements View.OnClickListener {

    Button b1,b2,b3,b4;
    int z=0;
    View view;
    Spinner s3;
    RequestQueue rq;
    String sub="",date1="",date2="",username,year;
    DatePickerDialog datePickerDialog,datePickerDialog2;
    private static final int STORAGE_PERMISSION_REQUEST_CODE = 1;
    ProgressBar progressBar;
    ProgressDialog progressDialog ;
    ArrayList<String> SubjectList;
    PermissionUtils permissionUtils;
    TextInputLayout urlTextInputLayout;
    DownloadManager downloadManager;
    String request_url = "http://10.162.11.47/fetch_pdf/table.php";
    // String request_url = "http://10.162.11.47/fetch_pdf/ex.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_percentage2);
        rq = Volley.newRequestQueue(this);

        s3=(Spinner) findViewById(spinner);
        b1=(Button) findViewById(R.id.button);
        b2=(Button) findViewById(R.id.button2);
        b3=(Button) findViewById(R.id.button3);
        b4=(Button) findViewById(R.id.button4);

        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        b4.setOnClickListener(this);
        Typeface d=Typeface.createFromAsset(getAssets(), "fonts/description.otf");
        b1.setTypeface(d);
        b2.setTypeface(d);
        b3.setTypeface(d);
        b4.setTypeface(d);

        progressBar = (ProgressBar)findViewById(R.id.ProgressBar1);
        permissionUtils = new PermissionUtils();
        SubjectList=new ArrayList<>();

        Intent ii=getIntent();
        String username = ii.getStringExtra("username");
        String email=ii.getStringExtra("email");
        String fors=ii.getStringExtra("fors");
        int roll_no1=ii.getIntExtra("roll_no",-1);
        String roll_no=roll_no1+"";

        if (roll_no.contains("16007")||roll_no.contains("17107"))
            year = "second year";
        else if (roll_no.contains("15007")||roll_no.contains("16107"))
            year = "third year";
        else if (roll_no.contains("14007")||roll_no.contains("15107"))
            year = "fourth year";
        else
            Toast.makeText(getPercentage2.this, "Roll no is not valid", Toast.LENGTH_SHORT).show();


        if(year.equals("second year"))
            spinnerSub2();
        else if(year.equals("third year"))
            spinnerSub3();
        else if(year.equals("fourth year"))
            spinnerSub4();
   /*     ArrayAdapter<CharSequence> adapter_year = ArrayAdapter.createFromResource(this, R.array.sub1, R.layout.spinner_item);
        adapter_year.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter_year);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                sub = adapterView.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                sub = "java";
            }
        });*/

    }


    @Override
    public void onClick(View view) {
        Intent ii=getIntent();
        String username = ii.getStringExtra("username");
        String email=ii.getStringExtra("email");
        String fors=ii.getStringExtra("fors");



        switch (view.getId())
        {
            case R.id.button:

                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(getPercentage2.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                b1.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);
                                date1=b1.getText().toString();

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

                break;
            case R.id.button2:
                final Calendar c2 = Calendar.getInstance();
                int mYear2 = c2.get(Calendar.YEAR); // current year
                int mMonth2 = c2.get(Calendar.MONTH); // current month
                int mDay2 = c2.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog2 = new DatePickerDialog(getPercentage2.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                b2.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);
                                date2=b2.getText().toString();

                            }
                        }, mYear2, mMonth2, mDay2);
                datePickerDialog2.show();
                break;
            case R.id.button3:
                if(date1.equals("")||date2.equals("")) {
                    Toast.makeText(getPercentage2.this,"Please fill all the required field",Toast.LENGTH_LONG).show();

                }
                else if(date1.equals(date2))
                {
                    Toast.makeText(getPercentage2.this,"Both the dates are same",Toast.LENGTH_LONG).show();

                }
                else {



                    if(fors.equals("s"))
                    {
                        int roll_no=ii.getIntExtra("roll_no",-1);

                        Intent i = new Intent(getPercentage2.this,StudentView.class);
                        i.putExtra("roll_no", roll_no);
                        i.putExtra("DATE1", date1);
                        i.putExtra("DATE2", date2);
                        i.putExtra("SUBJECT", sub);
                        startActivity(i);
                    }
                    else
                    {
                        Intent i = new Intent(getPercentage2.this,FacultyView.class);

                        i.putExtra("DATE1", date1);
                        i.putExtra("DATE2", date2);
                        i.putExtra("SUBJECT", sub);
                        startActivity(i);

                    }



                }
                break;
            case R.id.button4:
                if(date1.equals("")||date2.equals("")) {
                    Toast.makeText(getPercentage2.this,"Please fill all the required field",Toast.LENGTH_LONG).show();

                }
                else if(date1.equals(date2))
                {
                    Toast.makeText(getPercentage2.this,"Both the dates are same",Toast.LENGTH_LONG).show();

                }
                else {

                    generatePdf();
                   /* if (z == 1) {


                        if (permissionUtils.checkPermission(getPercentage2.this, STORAGE_PERMISSION_REQUEST_CODE, view)) {
                            if (urlEditText.length() > 0) {
                                try {
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(urlEditText)));
                                } catch (Exception e) {
                                    e.getStackTrace();
                                }
                            }

                        }
                    }
                    else
                        ;//Toast.makeText(getPercentage2.this,"Unable to generate pdf",Toast.LENGTH_LONG).show();
                */
                }
        }

    }

    public void viewPdf()
    {
        String urlEditText="http://10.162.11.47/fetch_pdf/doc.pdf";

        if (permissionUtils.checkPermission(getPercentage2.this, STORAGE_PERMISSION_REQUEST_CODE, view)) {
            if (urlEditText.length() > 0) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(urlEditText)));
                } catch (Exception e) {
                    e.getStackTrace();
                }
            }

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case STORAGE_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Snackbar.make(urlTextInputLayout, "Permission Granted, Now you can write storage.", Snackbar.LENGTH_LONG).show();
                } else {
                    //Snackbar.make(urlTextInputLayout, "Permission Denied, You cannot access storage.", Snackbar.LENGTH_LONG).show();
                }
                break;
        }
    }

    private void generatePdf() {
       /* progressBar.setVisibility(View.VISIBLE);
        Toast.makeText(getPercentage2.this,"Processing....Please Wait..",Toast.LENGTH_LONG).show();
*/
        progressDialog = ProgressDialog.show(getPercentage2.this,"Pdf Report generating...","Please Wait",false,true);
        StringRequest Req = new StringRequest(Request.Method.POST,request_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // progressBar.setVisibility(View.GONE);
                        progressDialog.dismiss();
                        Toast.makeText(getPercentage2.this,response,Toast.LENGTH_LONG).show();
                        z=1;
                        viewPdf1();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //progressBar.setVisibility(View.GONE);
                progressDialog.dismiss();
                Toast.makeText(getPercentage2.this,String.valueOf(error),Toast.LENGTH_LONG).show();


            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();

                params.put("year",year);
                params.put("startdate", date1);
                params.put("enddate", date2);
                params.put("subject", sub);
                return params;
            }
        };

        Req.setRetryPolicy(new DefaultRetryPolicy( 50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        rq.add(Req);
    }

    private void viewPdf1() {
        Toast.makeText(getPercentage2.this,"Report is downloading....",Toast.LENGTH_LONG).show();

        downloadManager = (DownloadManager)getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse("http://10.162.11.47/fetch_pdf/doc.pdf");
        // Uri uri = Uri.parse("http://10.162.11.47/fetch_pdf/abc.csv");
        DownloadManager.Request request= new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        Long reference = downloadManager.enqueue(request);

    }

public void spinnerSub2()
{
    ArrayAdapter<CharSequence> adapter_year = ArrayAdapter.createFromResource(this, R.array.sub22, R.layout.spinner_item);
    adapter_year.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

    s3.setAdapter(adapter_year);

    s3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
            sub = adapterView.getItemAtPosition(position).toString();
        }
        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    });

}

public void spinnerSub3()
{
    ArrayAdapter<CharSequence> adapter_year = ArrayAdapter.createFromResource(this, R.array.sub33, R.layout.spinner_item);
    adapter_year.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

    s3.setAdapter(adapter_year);

    s3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
            sub = adapterView.getItemAtPosition(position).toString();
        }
        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    });

}

public void spinnerSub4()
{
    ArrayAdapter<CharSequence> adapter_year = ArrayAdapter.createFromResource(this, R.array.sub44, R.layout.spinner_item);
    adapter_year.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

    s3.setAdapter(adapter_year);

    s3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
            sub = adapterView.getItemAtPosition(position).toString();
        }
        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    });

}

}