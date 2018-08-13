package com.app.daniel.expense_tracker_project;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    ImageView animation_iv,mantrack;
    AnimationDrawable walletAnimation;
    Animation fadeinAnimation;
    LinearLayout welcomeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





        //references
        animation_iv = (ImageView) findViewById(R.id.animation_iv);
        mantrack = (ImageView) findViewById(R.id.mantrack);
        welcomeLayout = (LinearLayout) findViewById(R.id.welcomeLayout);



        //set animations
        animation_iv.setBackgroundResource(R.drawable.loading_animation);
        walletAnimation = (AnimationDrawable) animation_iv.getBackground();

        //animation
        final Animation righttobottom = AnimationUtils.loadAnimation(this,R.anim.righttobottom);
        final Animation fade_in = AnimationUtils.loadAnimation(this,R.anim.fade_in);
        final Animation fade_out = AnimationUtils.loadAnimation(this,R.anim.fade_out);
        //set animations
        animation_iv.setAnimation(fade_in);
        mantrack.setAnimation(righttobottom);

        fadeinAnimation = AnimationUtils.loadAnimation(this,R.anim.fade_in);
        walletAnimation.start();


        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {

                //after x time do this

                //welcomeLayout.setAnimation(righttobottom);
                welcomeLayout.setVisibility(View.GONE);//makes the loading gone for the next
                Toast.makeText(MainActivity.this, "moving to the main program", Toast.LENGTH_SHORT).show();


                //move to SystemActivity Intent
                Intent mainIntent = new Intent(MainActivity.this, SystemActivity.class);
                MainActivity.this.startActivity(mainIntent);
                MainActivity.this.finish();





            }
        }, 4000);












    }
}
