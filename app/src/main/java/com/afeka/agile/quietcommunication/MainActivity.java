package com.afeka.agile.quietcommunication;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnKeyListener {

    private static SensorManager sensorService;
    public static boolean record;
    public static AppCompatActivity instance;

    private TextView textView;
    private EditText editText;
    private Sensor sensor;
    private VolumButtonListener volumButtonListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorService = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorService.registerListener(new AccelerometerEventListener() ,sensorService.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
        sensor = sensorService.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        instance=this;
        textView=(TextView)findViewById(R.id.textView);
        editText=(EditText) findViewById(R.id.editText);
        MorseDic.init();
        volumButtonListener=new VolumButtonListener();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_VOLUME_DOWN)){
            if (record){
                String word=AccelerometerEventListener.wordBuilder.toString();
                Toast.makeText(getBaseContext(),"Print new word" +word ,
                        Toast.LENGTH_SHORT).show();
               if (word!=null)
                    textView.setText(textView.getText()+" "+word);
                AccelerometerEventListener.wordBuilder=new StringBuilder();

            }


        }
        if ((keyCode == KeyEvent.KEYCODE_VOLUME_UP)) {
            record = !record;
            if (record)textView.setText("");
            Toast.makeText(getBaseContext(),"Record is" +record,
                    Toast.LENGTH_SHORT).show();
        }
        return true;
    }


    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        return false;
    }
}
