package com.project.cs496.tab_app;

/**
 * Created by q on 2016-07-01.
 */
public class Weather {
    public Location location;
    public CurrentCondition currentCondition = new CurrentCondition();
    public Temperature temperature = new Temperature();
    public Wind wind = new Wind();
    public Clouds clouds = new Clouds();
    public byte[] iconData;


}
