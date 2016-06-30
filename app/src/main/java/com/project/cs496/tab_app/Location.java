package com.project.cs496.tab_app;

/**
 * Created by q on 2016-07-01.
 */
public class Location {
    private double Latitude;
    private double Longitude;
    private String Country;
    private String City;
    private int Sunrise;
    private int Sunset;

    public void setLatitude(double Latitude){
        this.Latitude = Latitude;
    }
    public double getLatitude(){
        return this.Latitude;
    }

    public void setLongitude(double Latitude){
        this.Longitude = Longitude;
    }
    public double getLongitude(){
        return this.Longitude;
    }

    public void setCountry(String Country){
        this.Country = Country;
    }
      public String getCountry(){
        return this.Country;
    }

    public void setCity(String City){
        this.City = City;
    }
    public String getCity(){
        return this.City;
    }

    public void setSunrise(int Sunrise){
        this.Sunrise = Sunrise;
    }
    public int getSunrise(){
        return this.Sunrise;
    }

    public void setSunset(int Sunset){
        this.Sunset = Sunset;
    }
    public int getSunset(){
        return this.Sunset;
    }





}
