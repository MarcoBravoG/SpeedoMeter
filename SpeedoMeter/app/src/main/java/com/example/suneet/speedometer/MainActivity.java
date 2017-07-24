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
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.anastr.speedviewlib.base.Gauge;
import com.github.anastr.speedviewlib.base.Speedometer;
import com.github.anastr.speedviewlib.util.OnSpeedChangeListener;


import java.util.Locale;

public class MainActivity extends Activity implements Update, View.OnClickListener {
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
    //LinearLayout
    TextView currentLocation;

    //Bottom Button Layout
    Button startRide;
    Button stopRide;

    //LocationServices
    LocationManager locationManager;
    Location location;

    DataServices dataServices;
    LocationDataService locationDataService;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i=getIntent();

        if(i.getAction()==Intent.ACTION_CALL)
        {
            Log.e("CALL", "onCreate:CALL " );
        }
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
        currentLocation= (TextView) findViewById(R.id.location);
        startRide= (Button) findViewById(R.id.startRideButton);
        stopRide= (Button) findViewById(R.id.stopRideButton);
        locationManager= (LocationManager) getSystemService(LOCATION_SERVICE);
        if(!(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)))
        {
            Toast.makeText(MainActivity.this,"Turn on Location Services",Toast.LENGTH_LONG).show();
        }



        AssetManager am=this.getAssets();
        Typeface typeface=Typeface.createFromAsset(am,String.format(Locale.US,"fonts/%s","PoiretOne-Regular.ttf"));
        Typeface gaugefont=Typeface.createFromAsset(am,String.format(Locale.US,"fonts/%s","Orbitron-Regular.ttf"));
        appName.setTypeface(typeface);
        gauge.setTextTypeface(gaugefont);
        gauge.setSpeedTextTypeface(gaugefont);
        gauge.setTextSize(24);
        gauge.setSpeedTextSize(32);
        gauge.setUnit("KM/HR");
        gauge.setTrembleDuration(2000);


        startRide.setOnClickListener(this);
        stopRide.setOnClickListener(this);




    }

    @Override
    public void updateLocation(String location) {
        currentLocation.setText(location);

    }

    @Override
    public void updateSpeedGauge(float speed) {
        gauge.realSpeedTo(speed);



    }

    @Override
    public void updateTotalDistance(double distance) {
        totalDistance.setText((int)distance+"");
    }

    @Override
    public void updateAverage(double avg) {
        averageSpeed.setText((int) avg+"");
    }

    @Override
    public void updateTopSpeed(double top) {
        topSpeed.setText((int)top+"");

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.startRideButton)
        {
           /* dataServices = new DataServices(this,gauge);
            dataServices.onRun();*/
           locationDataService=new LocationDataService(this);
            locationDataService.initiate();
            locationDataService.onRun();
            startRide.setVisibility(View.GONE);
            stopRide.setVisibility(View.VISIBLE);
        }
        if (v.getId()==R.id.stopRideButton)
        {
            Log.e("", "onClick: asasas" );
            startRide.setVisibility(View.VISIBLE);
            gauge.stop();
            stopRide.setVisibility(View.GONE);

        }
    }
}
