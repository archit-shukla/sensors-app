package com.example.sensors;

import static android.hardware.Sensor.TYPE_ACCELEROMETER;
import static android.hardware.SensorManager.SENSOR_DELAY_FASTEST;
import static android.hardware.SensorManager.SENSOR_DELAY_NORMAL;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private Button button;
    TextView tv2,tv3,tv4,tv5,tv6,tv7,tv8,tv9,tv10,tv11;
    private Sensor sensor_mag;
    private Sensor sensor_gyr;
    private Sensor sensor_grav;
    private Sensor sensor_proxy;
    private SensorManager sensorManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv2=findViewById(R.id.tv2);
        tv3=findViewById(R.id.tv3);
        tv4=findViewById(R.id.tv4);
        tv5=findViewById(R.id.tv5);
        tv6=findViewById(R.id.tv6);
        tv7=findViewById(R.id.tv7);
        tv8=findViewById(R.id.tv8);
        tv9=findViewById(R.id.tv9);
        tv10=findViewById(R.id.tv10);
        tv11=findViewById(R.id.tv11);


        sensorManager= (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor_mag= sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        sensor_gyr= sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        sensor_grav= sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        sensor_proxy= sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        sensorManager.registerListener(MainActivity.this,sensor_mag,SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(MainActivity.this,sensor_gyr,SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(MainActivity.this,sensor_grav,SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(MainActivity.this,sensor_proxy,SENSOR_DELAY_NORMAL);

        button = findViewById(R.id.sources);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSources();
            }
        });

        button=findViewById(R.id.light);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLight();
            }
        });

        button = findViewById(R.id.accel);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAccelerometer();
            }
        });
    }

    private void openAccelerometer() {
        Intent intent4=new Intent(this,MainActivity4.class);
        startActivity(intent4);
    }

    private void openLight() {
            Intent intent4=new Intent(this,MainActivity3.class);
            startActivity(intent4);
        }


    private void openSources() {
        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
    }



    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        if(sensorEvent.sensor.getType()==sensor_mag.getType()) {

            tv3.setText("X = " + (float) sensorEvent.values[0]  + getString(R.string.micro) +"T");
            tv4.setText("Y = " + (float) sensorEvent.values[1]  + getString(R.string.micro)+"T");
            tv5.setText("Z = " + (float) sensorEvent.values[2]  + getString(R.string.micro)+"T");
        }

        if(sensorEvent.sensor.getType()==sensor_gyr.getType()) {

            tv6.setText("X = " + (float) sensorEvent.values[0] + " rad/s");
            tv7.setText("Y = " + (float) sensorEvent.values[1] + " rad/s");
            tv8.setText("Z = " + (float) sensorEvent.values[2] + " rad/s");
        }


        if(sensorEvent.sensor.getType()==sensor_grav.getType()) {
            tv9.setText("X = " + (float) sensorEvent.values[0] + " m/s"+ getString(R.string.square));
            tv10.setText("Y = " + (float) sensorEvent.values[1]+ " m/s"+ getString(R.string.square));
            tv11.setText("Z = " + (float) sensorEvent.values[2]+ " m/s"+ getString(R.string.square));
        }



        if(sensorEvent.sensor.getType()==sensor_proxy.getType()) {
            tv2.setText("X = " + sensorEvent.values[0] + " cm");
            if (sensorEvent.values[0] < sensor_proxy.getMaximumRange()) {
                getWindow().getDecorView().setBackgroundColor(Color.parseColor("#f54275"));
            } else {
                getWindow().getDecorView().setBackgroundColor(Color.parseColor("#42f5ad"));
            }
        }


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
    @Override
    protected void onPause(){
        super.onPause();
        sensorManager.unregisterListener(this);
    }

}