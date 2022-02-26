package com.example.magmatest;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Marker {
    GoogleMap googleMap;
    MarkerOptions markerOptions;
    Marker(GoogleMap googleMap){
        this.googleMap=googleMap;
        markerOptions=new MarkerOptions();
    }
     void addMarker(LatLng latlng, String title) {
        markerOptions.position(latlng);
        markerOptions.title(title);
        googleMap.addMarker(markerOptions);
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng,10));
    }
}
