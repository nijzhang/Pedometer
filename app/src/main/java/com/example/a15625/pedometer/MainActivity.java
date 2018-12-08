package com.example.a15625.pedometer;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.BarModel;
import org.eazegraph.lib.models.PieModel;

public class MainActivity extends AppCompatActivity {

    BarChart mbarchar;
    PieChart mpiechart;

    TextView total_steps_tv,yes_steps_tv;

    private int counter = 0;

    SensorManager sensorManager;
    Sensor sensor_step_dector,sensor_step_counter;

    private SensorEventListener listener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            Toast.makeText(MainActivity.this,"hello world",Toast.LENGTH_SHORT).show();
          if(event.sensor.getType() == Sensor.TYPE_STEP_COUNTER)
          {
                total_steps_tv.setText("Total steps :"+event.values[0]);
          }

          if(event.sensor.getType() == Sensor.TYPE_STEP_DETECTOR)
          {
              if(event.values[0] == 1.0)
              {
                    counter++;
                    yes_steps_tv.setText("Current  steps :"+counter);
              }
          }


        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Init();

        registerSensor();

        creatBarchart();

        creatPiechart();
    }

    private void registerSensor() {

        sensorManager.registerListener(listener,sensor_step_counter,SensorManager.SENSOR_DELAY_FASTEST);
        sensorManager.registerListener(listener,sensor_step_dector,SensorManager.SENSOR_DELAY_FASTEST);
    }

    private void creatPiechart() {

        mpiechart.addPieSlice(new PieModel("Freetime", 15, Color.parseColor("#FE6DA8")));
        mpiechart.addPieSlice(new PieModel("Eating", 9, Color.parseColor("#FED70E")));



        mpiechart.startAnimation();
    }

    private void creatBarchart() {
        mbarchar.addBar(new BarModel("Sun",1235,0xFF123456));
        mbarchar.addBar(new BarModel("Mon",1421,0xFF123456));
        mbarchar.addBar(new BarModel("Tue",141,0xFF123456));
        mbarchar.addBar(new BarModel("Wed",121,0xFF123456));
        mbarchar.addBar(new BarModel("Thu",421,0xFF123456));
        mbarchar.addBar(new BarModel("Fri",121,0xFF123456));
        mbarchar.addBar(new BarModel("Sat",4219,0xFF123456));

        mbarchar.startAnimation();
    }

    private void Init() {
        mbarchar = findViewById(R.id.xmlbarchar);
        mpiechart = findViewById(R.id.xmlPichar);

        total_steps_tv = findViewById(R.id.xml_total_textv);
        yes_steps_tv = findViewById(R.id.xml_yest_textv);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        sensor_step_dector = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);

        sensor_step_counter = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sensorManager.unregisterListener(listener);
    }
}
