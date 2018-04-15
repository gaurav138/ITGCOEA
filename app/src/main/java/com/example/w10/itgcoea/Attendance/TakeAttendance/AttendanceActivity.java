package com.example.w10.itgcoea.Attendance.TakeAttendance;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.w10.itgcoea.R;

import java.util.ArrayList;

public class AttendanceActivity extends AppCompatActivity {
    Context context;
    ArrayList<Category> array_list;
    FavouriteCategoriesJsonParser categoryJsonParser;
    String categoriesCsv;
    String date,year,subject;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        Bundle bundle= getIntent().getExtras();
        date=bundle.getString("DATE");
        year=bundle.getString("YEAR");
        subject=bundle.getString("SUBJECT");
         //Toast.makeText(AttendanceActivity.this,categoriesCsv,Toast.LENGTH_LONG).show();

        // Toast.makeText(AttendanceActivity.this,date+year+subject,Toast.LENGTH_LONG).show();


        context = this;
        new asyncTask_getCategories().execute();
    }

    public class asyncTask_getCategories extends AsyncTask<Void, Void, Void> {
        ProgressDialog dialog = new ProgressDialog(context);

        @Override
        protected void onPreExecute() {
            dialog.setTitle("Please wait...");
            dialog.setMessage("Loading Roll Nos!");
            dialog.show();
            array_list = new ArrayList<>();
            categoryJsonParser = new FavouriteCategoriesJsonParser();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            array_list = categoryJsonParser.getParsedCategories(year);
            return null;
        }

        @Override
        protected void onPostExecute(Void s) {

            ListView mListViewBooks = (ListView) findViewById(R.id.category_listView);
            final CategoryAdapter categoryAdapter = new CategoryAdapter(context, R.layout.row_category, array_list);
            mListViewBooks.setAdapter(categoryAdapter);
            Button button = (Button) findViewById(R.id.selectCategoryButton);
            Typeface d=Typeface.createFromAsset(getAssets(), "fonts/description.otf");
            button.setTypeface(d);


            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    categoriesCsv = FavouriteCategoriesJsonParser.selectedCategories.toString();
                    categoriesCsv = categoriesCsv.substring(1, categoriesCsv.length() - 1);

                    if (categoriesCsv.length() > 0) {
                        //Toast.makeText(context,categoriesCsv,Toast.LENGTH_LONG).show();
                        new asyncTask_insertUpdatefavouriteCategories().execute();
                    } else {
                        Toast.makeText(context, "Please Select Atleast One Category", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            super.onPostExecute(s);
            dialog.dismiss();
        }

        public class asyncTask_insertUpdatefavouriteCategories extends AsyncTask<Void, Void, Void> {

            String response;

            @Override
            protected Void doInBackground(Void... params) {
                response = InsertUpdateFavouriteCategories.insertUpdateCall(categoriesCsv,date,subject);

                return null;
            }

            @Override
            protected void onPostExecute(Void s) {
                Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                super.onPostExecute(s);
            }
        }
    }

    @Override
    public void onBackPressed() {

        FavouriteCategoriesJsonParser.selectedCategories.clear();

        AlertDialog.Builder builder = new AlertDialog.Builder(AttendanceActivity.this);

        builder.setCancelable(false);
        builder.setTitle("Do you want exit ?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                finish();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }
}
