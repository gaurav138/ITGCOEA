package com.example.w10.itgcoea;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class WelcomeActivity extends AppCompatActivity {

    ImageView imageView;
    private  int color;
    private Paint paint;
    private Rect rect;
    private RectF rectF;
    private Bitmap imageOut;
    private Canvas canvas;
    private  float roundPx;

    ImageView image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        imageView=(ImageView)findViewById(R.id.imageView);



        TextView t1= (TextView) findViewById(R.id.textView4);
        TextView t2= (TextView) findViewById(R.id.textView5);

        Typeface myCustomFonts=Typeface.createFromAsset(getAssets(),"fonts/it.ttf");
        t1.setTypeface(myCustomFonts);

        Typeface myCustomFonts2=Typeface.createFromAsset(getAssets(),"fonts/z.ttf");
        t2.setTypeface(myCustomFonts2);

        Animation animation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.welcome_animation);
        imageView.setAnimation(animation);

        Animation animation1= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.my);
        t1.setAnimation(animation1);
        Animation animation2= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.my);
        t2.setAnimation(animation2);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {


            }

            @Override
            public void onAnimationEnd(Animation animation) {
                finish();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });



    }






}
