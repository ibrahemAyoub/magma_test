package com.example.magmatest;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class City {
    private String name;
    private double latitude;
    private double longitude;
    ArrayList<String> cityName;
    ArrayList<City> cities;
    public City(){
        cityName=new ArrayList<String>();
        cities=new ArrayList<City>();
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    void getCityName(){
        for (int i=0;i<cities.size();i++) {
            cityName.add(cities.get(i).getName());
        }
    }
    void addCities(){
        City city1=new City();
        city1.setName("Germanos - Pastry");
        city1.setLatitude(33.85217073479985);
        city1.setLongitude(35.895525763213946);
        cities.add(city1);
        City city2=new City();
        city2.setName("Malak el Tawook");
        city2.setLatitude(33.85217073479985);
        city2.setLongitude(35.89477838111461);
        cities.add(city2);
        City city3=new City();
        city3.setName("Collège Oriental");
        city3.setLatitude(33.85334017189446);
        city3.setLongitude(35.89438946093824);
        cities.add(city3);
        getCityName();
    }
}
