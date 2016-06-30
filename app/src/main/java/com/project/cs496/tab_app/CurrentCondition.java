package com.project.cs496.tab_app;

/**
 * Created by q on 2016-07-01.
 */
public class CurrentCondition {
    private int WeatherId;
    private String Descr;
    private String Condition;
    private String Icon;
    private int Humidity;
    private int Pressure;


    public int getWeatherId() {
        return WeatherId;
    }

    public void setWeatherId(int weather) {
        WeatherId = weather;
    }

    public String getDescr() {
        return Descr;
    }

    public void setDescr(String descr) {
        Descr = descr;
    }

    public String getCondition() {
        return Condition;
    }

    public void setCondition(String condition) {
        Condition = condition;
    }

    public String getIcon() {
        return Icon;
    }

    public void setIcon(String icon) {
        Icon = icon;
    }

    public int getHumidity() {
        return Humidity;
    }

    public void setHumidity(int humidity) {
        Humidity = humidity;
    }

    public int getPressure() {
        return Pressure;
    }

    public void setPressure(int pressure) {
        Pressure = pressure;
    }
}
