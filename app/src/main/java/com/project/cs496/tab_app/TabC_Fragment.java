package com.project.cs496.tab_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class TabC_Fragment extends Fragment{
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private View rootView;
    private SharedPreferences setting;
    private String location;
    private JSONObject jObj;
    private Weather weather;
    private JSONParser jsonParser;

    private TextView cityText;
    private TextView condDescr;
    private TextView temp;
    private TextView hum;
    private TextView press;
    private TextView windSpeed;
    private TextView windDeg;
    private ImageView imgView;
    private WeatherHttpClient weatherHttpClient = new WeatherHttpClient();

    public TabC_Fragment() {

    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static TabC_Fragment newInstance() {
        TabC_Fragment fragment = new TabC_Fragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        rootView = inflater.inflate(R.layout.tab_c_fragment, container, false);

        cityText = (TextView) rootView.findViewById(R.id.cityText);
        condDescr = (TextView) rootView.findViewById(R.id.condDescr);
        temp = (TextView) rootView.findViewById(R.id.temp);
        hum = (TextView) rootView.findViewById(R.id.hum);
        press = (TextView) rootView.findViewById(R.id.press);
        windSpeed = (TextView) rootView.findViewById(R.id.windSpeed);
        windDeg = (TextView) rootView.findViewById(R.id.windDeg);
        imgView = (ImageView) rootView.findViewById(R.id.condIcon);

        jsonParser = new JSONParser();
        setting = getActivity().getSharedPreferences("setting",0);
        location = setting.getString("location",null);

        if(location != null) {
            weather = jsonParser.getData(WeatherHttpClient.getWeatherData("q=" + location));
            weather.iconData = weatherHttpClient.getImage(weather.currentCondition.getIcon());
            cityText.setText(weather.location.getCity());
            condDescr.setText("Clouds("+weather.currentCondition.getDescr()+")");
            temp.setText(String.valueOf(weather.temperature.getTemp()) + (char)0x00B0);
            hum.setText(String.valueOf(weather.currentCondition.getHumidity())+"%");
            press.setText(String.valueOf(weather.currentCondition.getPressure())+"hPa");
            windSpeed.setText(String.valueOf(weather.wind.getSpeed())+"mps");
            windDeg.setText(String.valueOf(weather.wind.getDeg())+"'");
            imgView.setImageBitmap(BitmapFactory.decodeByteArray(weather.iconData,0,weather.iconData.length));

        }



        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab3);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TabC_Fragment.this.getActivity() , SettingActicity.class);
                startActivityForResult(intent,1);
           }
        });


        return rootView;
    }
    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode, resultCode, data);

            location = setting.getString("location", "Daejeon");
            weather = jsonParser.getData(WeatherHttpClient.getWeatherData("q=" + location));
            weather.iconData = weatherHttpClient.getImage(weather.currentCondition.getIcon());
            cityText.setText(weather.location.getCity());
            condDescr.setText("Clouds(" + weather.currentCondition.getDescr() + ")");
            temp.setText(String.valueOf(weather.temperature.getTemp()) + (char) 0x00B0);
            hum.setText(String.valueOf(weather.currentCondition.getHumidity()) + "%");
            press.setText(String.valueOf(weather.currentCondition.getPressure()) + "hPa");
            windSpeed.setText(String.valueOf(weather.wind.getSpeed()) + "mps");
            windDeg.setText(String.valueOf(weather.wind.getDeg()) + "'");
            imgView.setImageBitmap(BitmapFactory.decodeByteArray(weather.iconData, 0, weather.iconData.length));

    }




}