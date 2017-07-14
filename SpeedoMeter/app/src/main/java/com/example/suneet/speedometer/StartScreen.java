package com.example.suneet.speedometer;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import agency.tango.materialintroscreen.MaterialIntroActivity;
import agency.tango.materialintroscreen.MessageButtonBehaviour;
import agency.tango.materialintroscreen.SlideFragment;
import agency.tango.materialintroscreen.SlideFragmentBuilder;


public class StartScreen extends MaterialIntroActivity{



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        addSlide(new SlideFragmentBuilder()
                    .title("SpeedoMeter")
                    .description("Welcome to SpeedoMeter App !! " +"\n"+
                            "Let's Drive Safe and Fast ")
                    .backgroundColor(R.color.colorSignalRed)
                    .buttonsColor(R.color.colorSpeedometer)
                    .image(R.drawable.start_screen_image0)
                    .build(),new MessageButtonBehaviour(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMessage("Lets CheckIn !!");
            }
        },"Next"));

        addSlide(new SlideFragmentBuilder()
                .backgroundColor(R.color.colorSignalYellow)
                .buttonsColor(R.color.colorSpeedometer)

                .description("Our App targets your Phone GPS and Network to provide you the best service \n " +
                        "Kindly Allow all the permissions for better performance")
                .title("Permissions")

                .image(R.drawable.start_screen_image)
                .possiblePermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.READ_CONTACTS})
                //.neededPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.READ_CONTACTS})
                .build(),new MessageButtonBehaviour(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE);
                    shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION);
                    shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION);
                }

            }
        },"Make Sure You Applied All Gears"));
      

        addSlide(new SlideFragmentBuilder()
                    .backgroundColor(R.color.colorSignalGreen)
                    .image(R.drawable.start_screen_image2)
                     .buttonsColor(R.color.colorSpeedometer)
                    .title("Driver Info")
                    .description("The App lets drive you without any hastle by blocking your incoming calls and alerting you " +
                            "if you drive too fast ! This apps also sends your location to your favourite contacts for security purposes")
                    .build(),new MessageButtonBehaviour("Get Set GO !"));





    }
    @Override
    public void onFinish() {
        super.onFinish();
        Intent i=new Intent(this,MainActivity.class);
        startActivity(i);
        Log.e("TAG", "onFinish: ");
    }


}
