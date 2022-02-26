package com.example.magmatest;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class GetCurrentLocation {
    FusedLocationProviderClient client;
    static double latitude=0;
    static double longitude=0;
    Context context;
    Marker marker;
    GoogleMap googleMap;
    LatLng latLng=new LatLng(0,0);
    GetCurrentLocation(Context context,GoogleMap googleMap){
        this.context=context;
        this.googleMap=googleMap;
        marker=new Marker(googleMap);
    }
    void checkPermission(){
        client= LocationServices.getFusedLocationProviderClient(context);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_MEDIA_LOCATION)!= PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context,Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions((Activity) context,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},100);
            return;
        }
    }
    LatLng getLocation(){
        checkPermission();
        client.getLastLocation().addOnSuccessListener((Activity) context, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location!=null){
                    latitude=location.getLatitude();
                    longitude=location.getLongitude();
                    latLng=new LatLng(latitude,longitude);
                    marker.addMarker(latLng,"I\'m Here");
                    //cameraPosition = new CameraPosition.Builder().target(latLng).zoom(15).build();
                    //googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                }
            }
        }).addOnFailureListener((Activity) context, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
        return latLng;
    }
}
