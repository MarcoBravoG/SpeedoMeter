package com.example.suneet.speedometer;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.github.anastr.speedviewlib.SpeedView;
import com.github.anastr.speedviewlib.base.Gauge;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by suneet on 14/7/17.
 */

public class DataServices implements LocationListener {

    LocationManager locationManager;
    Update update;
    Location prevlocation;
    RideData rideData;
    double currentLat = 0;
    double currentLong = 0;
    double lastLat = 0;
    double lastLong = 0;
    Gauge gauge;
    Snackbar snackbar;
    Context c;
    Geocoder geocoder;
    String currentLocation="Fetching Details..Please Wait";



    public DataServices(Context c,Gauge gauge) {
        this.c = c;
        this.gauge=gauge;
    }

    public void onRun() {
        locationManager = (LocationManager) c.getSystemService(c.LOCATION_SERVICE);
        boolean check = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        rideData=new RideData();
        update=(Update)c;
        prevlocation=new Location("null");
        geocoder=new Geocoder(c, Locale.ENGLISH);
        boolean check1 = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (!(check && check1)) {
            Toast.makeText(c, "Please turn on Location Services", Toast.LENGTH_SHORT).show();
            Log.e("TAG", "onRun: aaye" );
        } else {
            if (ActivityCompat.checkSelfPermission(c, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(c,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling\
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,  this);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,this);

        }




    }


    @Override
    public void onLocationChanged(Location location) {

        try {
            List<Address> list=geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),5);
           // Log.e("TAG", "onLocationChanged: "+list.get(0).getAddressLine(0)+" \n "+list.get(0).getLocality()+ " \n "+list.get(0).getSubLocality()

            currentLocation=list.get(0).getAddressLine(0)+" "+list.get(0).getLocality()+" "+list.get(0).getSubLocality()+"-"+list.get(0).getPostalCode();

            currentLocation=currentLocation.replace("null","");
            update.updateLocation(currentLocation);

        } catch (IOException e) {
            e.printStackTrace();
        }

        currentLat=location.getLatitude();
        currentLong=location.getLongitude();
        /*if(rideData.isFirst())
        {
            lastLat=currentLat;
            lastLong=currentLong;
            rideData.setFirst(false);
        }
        prevlocation.setLongitude(lastLong);
        prevlocation.setLatitude(lastLat);
        if(location.getAccuracy()<location.distanceTo(prevlocation))
        {
            rideData.distanceTotal(location.distanceTo(prevlocation));
            lastLong=currentLong;
            lastLat=currentLat;
            Log.e("TAG", "onLocationChanged:DISTANCE RIDE "+location.distanceTo(prevlocation));
            Log.e("TAG", "onLocationChanged:DISTANCE RIDE "+location.getLatitude());
        }
        if(location.hasSpeed())
        {
            rideData.setCurrentSpeed(location.getSpeed());


        }*/
        Log.e("TAG", "onLocationChanged: "+location.getSpeed()*3.5 );
        gauge.speedTo((float) (location.getSpeed()*3.5));



    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.e("TAG",provider+ "onStatusChanged: "+status +"Satellited"+extras.get("satellites") );

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(c,"Provider disabled "+provider,Toast.LENGTH_SHORT).show();

    }

}
