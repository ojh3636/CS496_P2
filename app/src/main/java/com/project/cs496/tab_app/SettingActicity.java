package com.project.cs496.tab_app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.EditText;

/**
 * Created by q on 2016-06-30.
 */
public class SettingActicity extends AppCompatActivity {
    private EditText input_box;
    private Button input_button;
    private CheckBox gps_checkbox;
    private SharedPreferences sf;
    private String sfName = "setting";
    private SharedPreferences.Editor editor;
    private double lattitude;
    private double longitude;
    private boolean save_gps;
    private static final long Min_DISTANCE_CHANGE_FOR_UPDATES = 10;
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.tab_c_setting);


        input_box = (EditText) findViewById(R.id.input_box);
        input_button = (Button) findViewById(R.id.input_button);
        gps_checkbox = (CheckBox) findViewById(R.id.gps_checkbox);

        sf = getSharedPreferences(sfName, 0);
        String load_str = sf.getString("location", "");

        boolean load_gps = sf.getBoolean("is_gps", false);
        gps_checkbox.setChecked(load_gps);

        input_box.setText(load_str);

        input_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor = sf.edit();
                String save_str = input_box.getText().toString();
                editor.putString("location", save_str);
                editor.putBoolean("is_gps", save_gps);
                editor.commit();
                finish();
            }
        });

        gps_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                buttonView.setChecked(isChecked);
                save_gps = isChecked;
            }
        });

    }
}

