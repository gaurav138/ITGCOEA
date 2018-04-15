package com.example.w10.itgcoea;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class StudentNotices extends AppCompatActivity {

    ListView listView;

    ProgressDialog progressDialog;
    public String PDF_FETCH_URL = "http://10.162.11.47/AndroidPdfUpload/getPdfs1.php";
    String year="",value;

    //an array to hold the different pdf objects
    ArrayList<Pdf> pdfList= new ArrayList<Pdf>();

    //pdf adapter

    PdfAdapter pdfAdapter;


    Button buttonFetch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_notices);

        listView = (ListView) findViewById(R.id.listView);

        //initializing buttonFetch
       /* buttonFetch = (Button) findViewById(R.id.buttonFetchPdf);
        buttonFetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPdfs();
            }
        });*/
        Intent ii=getIntent();
        value = ii.getStringExtra("value");
        if(value.equals("studentdoc"))
        {

            year=ii.getStringExtra("year");
            PDF_FETCH_URL="http://10.162.11.47/upload_doc/getPdfs2.php";
        }
        else if(value.equals("student"))
        {

            PDF_FETCH_URL="http://10.162.11.47/AndroidPdfUpload/getPdfs3.php";
        }
        getPdfs();
        //initializing progressDialog





        //setting listView on item click listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Pdf pdf = (Pdf) parent.getItemAtPosition(position);
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse(pdf.getUrl()));
                startActivity(intent);

            }
        });

    }

    private void getPdfs() {
        progressDialog = new ProgressDialog(this);

        progressDialog.setMessage("Fetching Pdfs... Please Wait");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, PDF_FETCH_URL,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        progressDialog.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response);
                            Toast.makeText(StudentNotices.this,obj.getString("message"), Toast.LENGTH_SHORT).show();

                            JSONArray jsonArray = obj.getJSONArray("pdfs");

                            for(int i=0;i<jsonArray.length();i++){

                                //Declaring a json object corresponding to every pdf object in our json Array
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                //Declaring a Pdf object to add it to the ArrayList  pdfList
                                Pdf pdf  = new Pdf();
                                String pdfName = jsonObject.getString("name");
                                String pdfDate = jsonObject.getString("date");

                                String pdfUrl = jsonObject.getString("url");
                                pdf.setName(pdfName);
                                pdf.setDate(pdfDate);
                                pdf.setUrl(pdfUrl);
                                pdfList.add(pdf);

                            }

                            pdfAdapter=new PdfAdapter(StudentNotices.this,R.layout.list_layout, pdfList);

                            listView.setAdapter(pdfAdapter);

                            pdfAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();

                params.put("year",year);
                return params;
            }
        };

        RequestQueue request = Volley.newRequestQueue(this);
        request.add(stringRequest);

    }

}
