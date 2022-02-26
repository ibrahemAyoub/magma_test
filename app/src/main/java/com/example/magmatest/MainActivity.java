package com.example.magmatest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.magmatest.nearbyplace.GetNearbyPlaces;
import com.example.magmatest.placedirections.FetchPath;
import com.example.magmatest.placedirections.TaskLoadedCallback;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, AdapterView.OnItemClickListener, GoogleMap.OnMarkerClickListener, TaskLoadedCallback {
    GoogleMap googleMap;
    SupportMapFragment supportMapFragment;
    AutoCompleteTextView searchView;
    LinearLayout linearLayout;
    Button button;
    ImageView imageView;
    TextView textView;
    City city;
    GetCurrentLocation currentLocation;
    double latitude;
    double longitude;
    LatLng myLocation;
    LatLng destination;
    Marker marker;
    private Polyline currentPolyline;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView=findViewById(R.id.foodbtn);
        searchView = findViewById(R.id.serach_location);
        linearLayout=findViewById(R.id.layout_direction);
        button=findViewById(R.id.get_direction);
        textView=findViewById(R.id.city_direction);
        searchView.setThreshold(1);
        city=new City();
        city.addCities();
        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);
        supportMapFragment.getMapAsync(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.select_dialog_singlechoice,city.cityName);
        searchView.setAdapter(adapter);
        searchView.setDropDownBackgroundResource(R.color.autocompletet_background_color);
        searchView.setOnItemClickListener(this);
    }
    void addCityMark(String name){
        for (int i=0;i<city.cities.size();i++) {
            if(name.toLowerCase().equals(city.cities.get(i).getName().toLowerCase())) {
                LatLng latLng = new LatLng(city.cities.get(i).getLatitude(),city.cities.get(i).getLongitude());
                marker.addMarker(latLng,city.cities.get(i).getName());
            }

        }
    }
    void getRestaurantNearby(double latitude,double longitude)
    {
        Object dataTransfer[] = new Object[2];
        GetNearbyPlaces getNearbyPlacesData = new GetNearbyPlaces();
        String url="https://maps.googleapis.com/maps/api/place/nearbysearch/json"+
                "?location="+latitude+","+longitude+"&radius=5000&type=restaurant&sensor=true&key=AIzaSyAiAir1uMz3NwJDd9vjIhqeEuTUgw2S7VM";
        googleMap.clear();
        dataTransfer[0] = googleMap;
        dataTransfer[1] = url;
        getNearbyPlacesData.execute(dataTransfer);

    }

    public void showNearby(View view) {
        getRestaurantNearby(GetCurrentLocation.latitude,GetCurrentLocation.longitude);
    }

    @Override
    public void onMapReady(GoogleMap map) {
            googleMap=map;
            map.setOnMarkerClickListener(this);
            map.setTrafficEnabled(true);
            marker=new Marker(map);
            currentLocation=new GetCurrentLocation(MainActivity.this,map);
            myLocation=currentLocation.getLocation();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        {
            String selection = (String)adapterView.getItemAtPosition(i);
            addCityMark(selection);
        }
    }
    private String getUrl(LatLng origin, LatLng dest) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        //Set value enable the sensor
        String sensor="sensor=false";
        // Mode
        String mode = "mode=driving";
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&"+sensor+"&" +mode;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.map_key);
        return url;
    }

    public void getDirections(View view) {
        new FetchPath(MainActivity.this).execute(getUrl(new LatLng(GetCurrentLocation.latitude,GetCurrentLocation.longitude),destination));
    }

    @Override
    public boolean onMarkerClick(com.google.android.gms.maps.model.Marker marker) {
        linearLayout.setVisibility(View.VISIBLE);
        textView.setText(marker.getTitle());
        destination=marker.getPosition();
        return false;
    }

    @Override
    public void onTaskDone(Object... values) {
        if (currentPolyline != null)
            currentPolyline.remove();
        currentPolyline = googleMap.addPolyline((PolylineOptions) values[0]);
    }
}