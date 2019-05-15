package com.example.userslocation;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    LocationManager location_manager;
    LocationListener location_listner;
    Boolean b=true;



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==1 && grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            Location lastKnownLocation=location_manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            LatLng user_Location = new LatLng(lastKnownLocation.getLatitude(),lastKnownLocation.getLongitude());
            mMap.clear();
            b=false;
            mMap.addMarker(new MarkerOptions().position(user_Location).title("Your Location"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(user_Location,6));
            location_manager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,location_listner);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        location_manager=(LocationManager)this.getSystemService(Context.LOCATION_SERVICE);


        location_listner=new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                LatLng user_Location = new LatLng(location.getLatitude(),location.getLongitude());
                mMap.clear();

                mMap.addMarker(new MarkerOptions().position(user_Location).title("Your Location"));
                 mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(user_Location,6));
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {}

            @Override
            public void onProviderEnabled(String provider) {}

            @Override
            public void onProviderDisabled(String provider) {}
        };

        //if(Build.VERSION.SDK_INT>=23){

            if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            if(b){
                Location lastKnownLocation=location_manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                LatLng user_Location = new LatLng(lastKnownLocation.getLatitude(),lastKnownLocation.getLongitude());
                mMap.clear();

                mMap.addMarker(new MarkerOptions().position(user_Location).title("Your Location"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(user_Location,6));
                b=false;
            }
                location_manager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,location_listner);


            }
            else {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
            }
        }
        /*else
        {
            if(PermissionChecker.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)==PermissionChecker.PERMISSION_GRANTED) {
                location_manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, location_listner);

                Location lastKnownLocation = location_manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                LatLng user_Location = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(user_Location).title("Your Location"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(user_Location, 6));
            }
            else {

            }
        }*/
    //}
}
