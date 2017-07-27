package com.example.suneet.speedometer;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.github.anastr.speedviewlib.base.Gauge;



import java.util.Locale;
import java.util.zip.Inflater;

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
    EditText speedThrottle;
    DataServices dataServices;
    LocationDataService locationDataService;
    BroadcastReceiver receiver;
    LottieAnimationView lottieAnimationView;
    IntentFilter intentFilter;
    boolean flag;
    int throttle;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);

        Intent i=getIntent();
        receiver=new MyReceiver();

        intentFilter = new IntentFilter(TELEPHONY_SERVICE);
        intentFilter.addAction(TelephonyManager.ACTION_PHONE_STATE_CHANGED);



        if(i.getAction()==Intent.ACTION_CALL)
        {
            Log.e("CALL", "onCreate:CALL " );
        }
        setContentView(R.layout.activity_main);
        //RideData rideData=new RideData();

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

        lottieAnimationView= (LottieAnimationView) findViewById(R.id.animation_view);
        locationManager= (LocationManager) getSystemService(LOCATION_SERVICE);
        if(!(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)))
        {
            Snackbar.make(findViewById(R.id.externalLayout),"Turn on Location Service",Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.WHITE)
                    .setAction("Turn on", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent locationIntent=new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(locationIntent);
                        }
                    })
                    .show();

        }
        else
            Toast.makeText(MainActivity.this,"Let's Drive! Please Click Button",Toast.LENGTH_LONG).show();

        AssetManager am=this.getAssets();
        Typeface typeface=Typeface.createFromAsset(am,String.format(Locale.US,"fonts/%s","PoiretOne-Regular.ttf"));
        Typeface gaugefont=Typeface.createFromAsset(am,String.format(Locale.US,"fonts/%s","Orbitron-Regular.ttf"));
        appName.setTypeface(typeface);
        totalDistance.setTypeface(gaugefont);
        topSpeed.setTypeface(gaugefont);
        averageSpeed.setTypeface(gaugefont);
        currentLocation.setTypeface(typeface);
        gauge.setTextTypeface(gaugefont);
        gauge.setSpeedTextTypeface(gaugefont);
        gauge.setTextSize(24);
        gauge.setSpeedTextSize(32);
        gauge.setUnit("KM/HR");
        gauge.setTrembleDuration(2000);


        startRide.setOnClickListener(this);
        stopRide.setOnClickListener(this);
        menuIcon.setOnClickListener(this);




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
    protected void onStart() {
        super.onStart();
        this.registerReceiver(receiver,intentFilter);
    }

    /*@Override
    protected void onPause() {
        super.onPause();
        if(receiver!=null) {
            unregisterReceiver(receiver);
        }

    }*/

    @Override
    protected void onStop() {
        super.onStop();
        if(receiver!=null && flag==true) {
          unregisterReceiver(receiver);
            
        }

    }
    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.startRideButton)
        {
            if((checkGps())) {
           /* dataServices = new DataServices(this,gauge);
            dataServices.onRun();*/
                locationDataService = new LocationDataService(this);
                locationDataService.initiate();
                locationDataService.onRun();
                startRide.setVisibility(View.GONE);
                stopRide.setVisibility(View.VISIBLE);
                flag=true;
               // lottieAnimationView.setAnimation("success.json");
                //lottieAnimationView.playAnimation();
            }
        }
        if (v.getId()==R.id.stopRideButton)
        {
            Log.e("", "onClick: asasas" );
            startRide.setVisibility(View.VISIBLE);
            gauge.stop();
            stopRide.setVisibility(View.GONE);
            locationDataService.onStop();
            if(receiver!=null) {
                unregisterReceiver(receiver);
                flag=false;
            }



        }
        if(v.getId()==R.id.menuIcon) {

            View alert= LayoutInflater.from(this).inflate(R.layout.alert_dialog,null);
            speedThrottle= (EditText) alert.findViewById(R.id.speedThrottle);
            final AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle("SpeedoMeter Throttle")
                    .setIcon(R.drawable.app_icon100)

                    .setCancelable(false)
                    .setMessage("Enter the Speed above which you wanna drive without any hastle !!")
                    .setView(alert)
                    .setNeutralButton("Info", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                              AlertDialog.Builder builder1=new AlertDialog.Builder(MainActivity.this);
                              builder1.setIcon(R.drawable.info_icon)
                                      .setTitle("Info")
                                      .setMessage("This App lets you drive hassle free without any calls and other disturbances." +
                                              "\nThe driver enters the throttle speed in KM/HR and if they drive beyond this " +
                                              "speed the calls will be disconnected automatically. \n Hope you like the concept !!")
                                      .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                          @Override
                                          public void onClick(DialogInterface dialog, int which) {

                                          }
                                      });
                            AlertDialog dialog1=builder1.create();
                            dialog1.show();

                        }
                    })
                   .setPositiveButton("Set Throttle", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialog, int which) {
                           try {
                               throttle = Integer.parseInt(speedThrottle.getText().toString());
                               Toast.makeText(MainActivity.this, "Throttle Set to "+throttle, Toast.LENGTH_SHORT).show();
                           }
                           catch (Exception e)
                           {
                               speedThrottle.setHint("Enter suitable speed");
                               Toast.makeText(MainActivity.this,"Enter suitable throttle",Toast.LENGTH_SHORT).show();
                           }
                       }
                   })
                    .setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                        }
                    });
            AlertDialog dialog=builder.create();
            dialog.show();


        }
    }
    public boolean checkGps()
    {
        if(!(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)))
        {
            Toast.makeText(this,"Turn on Location Services",Toast.LENGTH_SHORT).show();
            return false;

        }
        else
            return true;
    }
}
