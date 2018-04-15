package com.example.w10.itgcoea.ApproveStudent;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

public class ApproveActivity extends AppCompatActivity {
    String year;
    Context context;
    ArrayList<Category2> array_list;
    FavouriteCategoriesJsonParser2 categoryJsonParser;
    String categoriesCsv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve);

        Intent ii=getIntent();
        year = ii.getStringExtra("year");

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
            categoryJsonParser = new FavouriteCategoriesJsonParser2();
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
            final CategoryAdapter2 categoryAdapter2 = new CategoryAdapter2(context, R.layout.row_category2, array_list);
            mListViewBooks.setAdapter(categoryAdapter2);
            Button button = (Button) findViewById(R.id.selectCategoryButton);
            Typeface d=Typeface.createFromAsset(getAssets(), "fonts/description.otf");
            button.setTypeface(d);


            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    categoriesCsv = FavouriteCategoriesJsonParser2.selectedCategories.toString();
                    categoriesCsv = categoriesCsv.substring(1, categoriesCsv.length() - 1);

                    if (categoriesCsv.length() > 0) {
                       // Toast.makeText(context,categoriesCsv,Toast.LENGTH_LONG).show();
                        new asyncTask_insertUpdatefavouriteCategories2().execute();
                    } else {
                        Toast.makeText(context, "Please Select Atleast One Category", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            super.onPostExecute(s);
            dialog.dismiss();
        }

        public class asyncTask_insertUpdatefavouriteCategories2 extends AsyncTask<Void, Void, Void> {

            String response;

            @Override
            protected Void doInBackground(Void... params) {
                response = InsertUpdateFavouriteCategories2.insertUpdateCall(categoriesCsv,year);

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

        FavouriteCategoriesJsonParser2.selectedCategories.clear();

        AlertDialog.Builder builder = new AlertDialog.Builder(ApproveActivity.this);

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
