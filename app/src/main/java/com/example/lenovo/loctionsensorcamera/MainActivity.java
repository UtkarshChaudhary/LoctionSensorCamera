package com.example.lenovo.loctionsensorcamera;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class MainActivity extends AppCompatActivity {
    FusedLocationProviderClient mFusedLocationProviderClient;
    //client is entry point for accessing location

    LocationRequest mLocationRequest;
    //to be passed in locationRequest parmeter for adding parameter of requestLocation like time b/w two locationRequest
    LocationCallback mLocationCallback;
    boolean mRequestingLocationUpdates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // for location use location manager class or google play services

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000).setFastestInterval(500).setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        //setInterval= general time intervel b/w two location interval
        //setFastestInterval=min time interval after which second location will be received
        //setPriority =priority takes for bringing location

        mLocationCallback = new LocationCallback(){
           //here we are overriding func. of LocationCallback class
            @Override
            public void onLocationResult(LocationResult locationResult) {
//                super.onLocationResult(locationResult);
                Location location=locationResult.getLastLocation();
                if(location!=null){
                    Toast.makeText(MainActivity.this,"Lat: " + location.getLatitude() + "Longi " + location.getLongitude(), Toast.LENGTH_LONG).show();
                }
            }
        };



    }
//if we just want location
    public void getLocation(View view) {
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        mFusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
//            @Override
//            public void onSuccess(Location location) {
//                if (location != null) {
//                    Toast.makeText(MainActivity.this, "Lat: " + location.getLatitude() + "Longi " + location.getLongitude(), Toast.LENGTH_LONG).show();
//
//                }
//            }
//        });
        startRequestingUpdates();
   }

    //if we want updates of location

    public void startRequestingUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mFusedLocationProviderClient.requestLocationUpdates(mLocationRequest, mLocationCallback, null);
        mRequestingLocationUpdates=true;

        //null is written if we want result in same thread otherwise we have to mention thread name
   }

    @Override
    protected void onPause() {
        super.onPause();
        if(mRequestingLocationUpdates){
            mFusedLocationProviderClient.removeLocationUpdates(mLocationCallback);
            mRequestingLocationUpdates=false;

        }
    }
}
