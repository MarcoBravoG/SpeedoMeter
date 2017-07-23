package com.example.suneet.speedometer;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.anastr.speedviewlib.base.Gauge;
import com.github.anastr.speedviewlib.base.Speedometer;
import com.github.anastr.speedviewlib.util.OnSpeedChangeListener;


import java.util.Locale;

public class MainActivity extends Activity implements Update{
    //menu layout
    LinearLayout menuLayout;
    TextView appName;
    ImageView menuIcon;
    ImageView notification;
    //Gauge Layout
    Gauge gauge;
    //Features Layout
    TextView totalDistance;
    //Relative Layout
    TextView averageSpeed;
    TextView topSpeed;
    //Bottom Button Layout
    Button startRide;
    Button stopRide;

    //LocationServices
    LocationManager locationManager;
    Location location;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RideData rideData=new RideData();


        menuLayout = (LinearLayout) findViewById(R.id.menuLayout);
        appName= (TextView) findViewById(R.id.appName);
        menuIcon= (ImageView) findViewById(R.id.menuIcon);
        notification= (ImageView) findViewById(R.id.notificationIcon);
        gauge = (Gauge) findViewById(R.id.speedometer);
        totalDistance= (TextView) findViewById(R.id.totalDistance);
        averageSpeed= (TextView) findViewById(R.id.averageSpeed);
        topSpeed= (TextView) findViewById(R.id.topSpeed);
        startRide= (Button) findViewById(R.id.startRideButton);
        stopRide= (Button) findViewById(R.id.stopRideButton);

        AssetManager am=this.getAssets();
        Typeface typeface=Typeface.createFromAsset(am,String.format(Locale.US,"fonts/%s","PoiretOne-Regular.ttf"));
        Typeface gaugefont=Typeface.createFromAsset(am,String.format(Locale.US,"fonts/%s","Orbitron-Regular.ttf"));
        appName.setTypeface(typeface);
        gauge.setTextTypeface(gaugefont);
        gauge.setSpeedTextTypeface(gaugefont);
        gauge.setTextSize(24);
        gauge.setSpeedTextSize(32);
        gauge.setUnit("KM/HR");


        final DataServices dataServices=new DataServices(this,gauge);
        dataServices.onRun();



    }
}
