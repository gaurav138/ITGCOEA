package com.example.w10.itgcoea.UploadViewImage;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.bogdwellers.pinchtozoom.ImageMatrixTouchHandler;
import com.example.w10.itgcoea.R;

public class ViewImage2 extends AppCompatActivity {
    ImageLoader imageLoader;
    View view;
    public NetworkImageView VollyImageView ;
    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image2);

        VollyImageView = (NetworkImageView) findViewById(R.id.VolleyImageView) ;
        imageLoader = ImageAdapter.getInstance(ViewImage2.this).getImageLoader();
        Intent ii=getIntent();
        url=ii.getStringExtra("url");


        imageLoader.get(url,
                ImageLoader.getImageListener(
                        VollyImageView,//Server Image
                        R.mipmap.ic_launcher,//Before loading server image the default showing image.
                        android.R.drawable.ic_dialog_alert //Error image if requested image dose not found on server.
                )
        );

        VollyImageView.setImageUrl(url, imageLoader);
        VollyImageView.setOnTouchListener(new ImageMatrixTouchHandler(ViewImage2.this));


    }
}
