package com.example.suneet.speedometer;

import android.content.Context;
import android.icu.util.TimeUnit;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by suneet on 24/7/17.
 */

public class LocationDataService implements GoogleApiClient.OnConnectionFailedListener,
                                            GoogleApiClient.ConnectionCallbacks, com.google.android.gms.location.LocationListener{

    Context c;

    public LocationDataService(Context c) {
        this.c = c;
    }

    private FusedLocationProviderApi fusedLocationProviderApi;
    GoogleApiClient googleApiClient;
    ProgressBar progressBar;
    LocationRequest locationRequest;
    Location currentLocation;
    Location startLocation;
    Location endLocation;
    Geocoder geocoder;
    double speed=0;
    double distance=0;
    long startTime;
    long endTime;
    long timeDiff;
    double avgSpeed;
    RideData rideData;
    Update update;
    String locationText="";



    void initiate()
    {
        fusedLocationProviderApi= LocationServices.FusedLocationApi;
        googleApiClient=new GoogleApiClient.Builder(c)
                        .addApi(LocationServices.API)
                        .addOnConnectionFailedListener(this)
                        .addConnectionCallbacks(this)
                        .build();
        progressBar=new ProgressBar(c);
        progressBar.setIndeterminate(true);
        rideData=new RideData();

        startTime=System.currentTimeMillis();
        update=(Update)c;
    }
    void onRun()
    {
        googleApiClient.connect();
    }
    void onStop()
    {
        googleApiClient.disconnect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {


    }

    @Override
    public void onConnected(Bundle bundle) {
        locationRequest=LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);
        geocoder=new Geocoder(c, Locale.ENGLISH);

        Log.e("", "onConnected: " );
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient,locationRequest,this );

    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(c, "Connection Suspended", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onLocationChanged(Location location) {
        currentLocation=location;
        endTime=System.currentTimeMillis();
        timeDiff=endTime-startTime;
        timeDiff= java.util.concurrent.TimeUnit.MILLISECONDS.toMinutes(timeDiff);
        try {
            List<Address> list=geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),5);
            locationText=list.get(0).getAddressLine(0)+" "+list.get(0).getLocality()+" "+list.get(0).getSubLocality()+"-"+list.get(0).getPostalCode();
            locationText=locationText.replace("null","");
            update.updateLocation(locationText
            );

        } catch (IOException e) {
            e.printStackTrace();
        }


        if(startLocation==null)
        {
            startLocation= location;
            endLocation=location;
        }
        else {
            endLocation = location;
        }
        speed=(location.getSpeed())*(3.6);
        distance=distance+((startLocation.distanceTo(location)));
        distance=distance/1000;

        avgSpeed=distance/timeDiff;

        rideData.setCurrentSpeed(location.getSpeed());
        rideData.setAvgSpeed(avgSpeed);
        rideData.setCurrentSpeed(speed);
        rideData.distanceTotal(distance);
        update.updateAverage(avgSpeed);
        update.updateSpeedGauge((float) speed);
        update.updateTotalDistance(distance);
        update.updateSpeedGauge((float) speed);
        update.updateTopSpeed(rideData.getMaxSpeed());


        Log.e("HELLO", "Time Difference "+timeDiff );
        Log.e("TAG", "onLocationChanged: "+distance );
    }


}
