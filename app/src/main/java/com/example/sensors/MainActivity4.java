package com.example.sensors;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity4 extends AppCompatActivity implements SensorEventListener {

    private Sensor sensor6;
    private SensorManager sensorManager;
    TextView tv12,tv13,tv14,tv15;
    private double magnitudePrevious=0;
    private Integer stepCount=0;
    private Button StartProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        getSupportActionBar().setTitle("Step counter");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        StartProgress = findViewById(R.id.startProgess);
        tv12=findViewById(R.id.tv12);
        tv13=findViewById(R.id.tv13);
        tv14=findViewById(R.id.tv14);
        tv15=findViewById(R.id.tv15);

        StartProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                stepCount=0;
            }
        });


        sensorManager= (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor6= sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(MainActivity4.this,sensor6,sensorManager.SENSOR_DELAY_NORMAL);
    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float X=sensorEvent.values[0];
                tv12.setText("X = " +(float) sensorEvent.values[0] + "m/s"+ getString(R.string.square));
        float Y=sensorEvent.values[1];
                tv13.setText("Y = " +(float) sensorEvent.values[1] + "m/s"+ getString(R.string.square));
        float Z=sensorEvent.values[2];
                tv14.setText("Z = " +(float) sensorEvent.values[2] + "m/s"+ getString(R.string.square));

                double Magnitude=Math.sqrt(X*X + Y*Y + Z*Z);
                double magnitudeDelta=Magnitude-magnitudePrevious;
                magnitudePrevious=Magnitude;

                if(magnitudeDelta>6){
                    stepCount++;
                }
                tv15.setText(stepCount.toString());



    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

}