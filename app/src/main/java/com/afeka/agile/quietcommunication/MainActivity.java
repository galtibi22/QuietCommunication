package com.afeka.agile.quietcommunication;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnKeyListener {

    private static SensorManager sensorService;
    private static AppCompatActivity instance;

    private TextView textView;
    private EditText editText;
    private Button translate;

    private Sensor sensor;

    private AccelerometerEventListener acceleromListener;
    private VibrateDriver vibrateDriver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        acceleromListener =new AccelerometerEventListener();
        vibrateDriver=new VibrateDriver();
        sensorService = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorService.registerListener(acceleromListener,sensorService.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
        sensor = sensorService.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        instance=this;
        viewHandler();
        MorseDic.init();

    }

    private void viewHandler() {
        textView=(TextView)findViewById(R.id.textView);
        editText=(EditText) findViewById(R.id.editText);
        translate = (Button) findViewById(R.id.translate);
        translate.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                String text=editText.getText().toString();
                if (!text.isEmpty()) {
                    vibrateDriver.TextToVib(text);
                    editText.setText("");
                }
            }

        });
        editText.setOnClickListener(new EditText.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text=getString(R.string.translate);
                if (editText.getText().toString().equals(text))
                    editText.setText("");
            }
        });

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN && acceleromListener.isRecord())
            volumDown();
        else if ((keyCode == KeyEvent.KEYCODE_VOLUME_UP))
            volumeUp();
        return true;
    }

    private void volumeUp() {
        acceleromListener.setRecord(!acceleromListener.isRecord());
        if (acceleromListener.isRecord()){
            textView.setText("");
            vibrateDriver.retunFastVibrate(4);
        }
        Toast.makeText(getBaseContext(),"Record is " +acceleromListener.isRecord(),
                Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        return false;
    }

    public void volumDown(){
        acceleromListener.flushChar();
        String word=acceleromListener.getWordBuilder().toString();
        Toast.makeText(getBaseContext(),"Print new word " +word ,
                Toast.LENGTH_SHORT).show();
        if (word!=null)
            textView.setText(textView.getText()+" "+word);
        acceleromListener.setWordBuilder(new StringBuilder());
        vibrateDriver.retunFastVibrate(2);
    }


    public static AppCompatActivity getInstance() {
        return instance;
    }


}
