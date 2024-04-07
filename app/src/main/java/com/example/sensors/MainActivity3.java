package com.example.sensors;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity3 extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor sensor4;
    private View root;
    private float maxValue;
    Context context;
    TextView tv;
    Boolean success;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);


        getSupportActionBar().setTitle("Light Sensor");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tv = findViewById(R.id.tv);
        root = findViewById(R.id.root);



        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor4 = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        sensorManager.registerListener(MainActivity3.this, sensor4, sensorManager.SENSOR_DELAY_NORMAL);
        maxValue = sensor4.getMaximumRange();

    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        tv.setText("Luminosity = " + sensorEvent.values[0] + " lx");
        int newValue=(int)(2550f* sensorEvent.values[0]/maxValue);
        root.setBackgroundColor(Color.rgb(newValue,newValue,newValue));

        context = getApplicationContext();

        if (sensorEvent.values[0] < 40) {
            permission();
            setBrightness(240);
        }
        else if(sensorEvent.values[0]>200){
            permission();
            setBrightness(50);
        }


    }

    private void setBrightness(int brightness) {
        if (brightness < 0) {
            brightness = 0;
        } else if (brightness > 255) {
            brightness = 255;
        }
        ContentResolver contentResolver = getApplicationContext().getContentResolver();
        Settings.System.putInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS, brightness);
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    private void permission() {
        boolean value;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            value = Settings.System.canWrite(getApplicationContext());
            if (value) {
                success = true;
            } else {
                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package:" + getApplicationContext().getPackageName()));
                startActivityForResult(intent, 100);
            }
        }
    }

    protected void onActivityResults(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 100) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Boolean value = Settings.System.canWrite(getApplicationContext());
                if (value) {
                    success = true;
                } else {
                    Toast.makeText(this, "Permission is not granted", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }
}