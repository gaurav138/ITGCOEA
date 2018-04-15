package com.example.w10.itgcoea;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import java.util.Calendar;
import java.util.UUID;

public class AdminStudentDoc extends AppCompatActivity implements View.OnClickListener {

    //Declaring views
    private Button buttonChoose;
    private Button buttonUpload;
    private Button buttonF;
    ProgressDialog progressDialog ;
    Button date;
    DatePickerDialog datePickerDialog;
    String value,subject="",year="";

    private EditText editText;

    public String UPLOAD_URL = "http://10.162.11.47/AndroidPdfUpload/upload1.php";


    //Pdf request code
    private int PICK_PDF_REQUEST = 1;

    //storage permission code
    private static final int STORAGE_PERMISSION_CODE = 123;


    //Uri to store the image uri
    private Uri filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_student_doc);

        Intent ii=getIntent();
        value = ii.getStringExtra("value");
        if(value.equals("studentdoc"))
        {
          subject= ii.getStringExtra("subject");
            year=ii.getStringExtra("year");
            UPLOAD_URL="http://10.162.11.47/upload_doc/upload2.php";
        }
        else if(value.equals("student"))
        {
            UPLOAD_URL="http://10.162.11.47/AndroidPdfUpload/upload2.php";
        }

        //Requesting storage permission
        requestStoragePermission();

        //Initializing views
        buttonChoose = (Button) findViewById(R.id.buttonChoose);
        buttonUpload = (Button) findViewById(R.id.buttonUpload);


        editText = (EditText) findViewById(R.id.editTextName);

        //Setting clicklistener
        buttonChoose.setOnClickListener(this);
        buttonUpload.setOnClickListener(this);



        date = (Button) findViewById(R.id.editText);
        // perform click event on edit text
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(AdminStudentDoc.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                date.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        Typeface d=Typeface.createFromAsset(getAssets(), "fonts/description.otf");
        buttonChoose.setTypeface(d);
        buttonUpload.setTypeface(d);
        date.setTypeface(d);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void uploadMultipart() {
        //getting name for the image
        String name = editText.getText().toString().trim();
        String date1=date.getText().toString().trim();

        //getting the actual path of the image
        String path = FilePath.getPath(this, filePath);
        if (path == null) {

            Toast.makeText(this, "Please move your .pdf file to internal storage and retry", Toast.LENGTH_LONG).show();
        } else {
            //Uploading code
            try {
                progressDialog = ProgressDialog.show(AdminStudentDoc.this,"Pdf is uploading...","Please Wait",false,true);
                String uploadId = UUID.randomUUID().toString();

                //Creating a multi part request
                new MultipartUploadRequest(this, uploadId, UPLOAD_URL)
                        .addFileToUpload(path, "pdf") //Adding file
                        .addParameter("name", name) //Adding text parameter to the request
                        .addParameter("date",date1)
                        .addParameter("year",year)
                        .addParameter("subject",subject)
                        .setNotificationConfig(new UploadNotificationConfig())
                        .setMaxRetries(2)
                        .startUpload(); //Starting the upload

                Toast.makeText(this, "file uploaded successfully", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            } catch (Exception exc) {
                progressDialog.dismiss();
                Toast.makeText(this, exc.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }


    //method to show file chooser
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Pdf"), PICK_PDF_REQUEST);
    }

    //handling the image chooser activity result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_PDF_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            Toast.makeText(this, "File " + filePath + " Selected Succssfuly", Toast.LENGTH_LONG).show();
        }
    }


    //Requesting permission
    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }


    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)

    public void onClick(View v) {



        if (v == buttonChoose) {
            showFileChooser();
        }
        if (v == buttonUpload) {
            String name = editText.getText().toString().trim();
            String date1=date.getText().toString().trim();





            if(name.isEmpty())
            {
                Toast.makeText(this, "Please Enter the Name of File", Toast.LENGTH_SHORT).show();
            }

            if(date1.isEmpty())
            {
                Toast.makeText(this, "Please Enter a Date", Toast.LENGTH_SHORT).show();
            }

            else if(filePath==null)
            {
                Toast.makeText(this, "Please Select a File", Toast.LENGTH_SHORT).show();

            }


            else

                uploadMultipart();
        }
    }
}
