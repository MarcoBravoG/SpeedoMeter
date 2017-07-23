package com.example.suneet.speedometer;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

/**
 * Created by suneet on 24/7/17.
 */

public class LocationDataService implements GoogleApiClient.OnConnectionFailedListener,
                                            GoogleApiClient.ConnectionCallbacks,LocationListener{

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

    RideData rideData;
    Update update;

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
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient,locationRequest, (com.google.android.gms.location.LocationListener) this);

    }

    @Override
    public void onConnectionSuspended(int i) {
        
    }


    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
