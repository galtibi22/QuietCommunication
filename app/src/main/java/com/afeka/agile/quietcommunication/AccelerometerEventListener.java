
package com.afeka.agile.quietcommunication;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;

public class AccelerometerEventListener implements SensorEventListener{
    private final float SHAKE_THRESHOLD = 200;
    private final int SLEEP_CHAR=2500; //time for wait until start record new char
    private final int SPEED_LEVEL =1200; //maxSpeed< is . else -
    private boolean record;


    private int shakeCounter;
    private long lastUpdate,lastTime;
    private float last_x,last_y,last_z;
    private boolean shake=false;
    private float  maxSpeed;
    private StringBuilder charBuilder;
    private StringBuilder wordBuilder;
    private VibrateDriver vibrateDriver;
   
   
    public AccelerometerEventListener(){
        charBuilder=new StringBuilder();
        wordBuilder=new StringBuilder();
        vibrateDriver=new VibrateDriver();
    }
   
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            getAccelerometer(event);
        }

    }
    float avrageSpeed;
    private void getAccelerometer(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        long curTime = System.currentTimeMillis();

        if ((curTime - lastTime) > 80 && record) {
            long diffTime = (curTime - lastTime);
            float speed = Math.abs(x + y + z - last_x - last_y - last_z) / diffTime * 10000;
            if (speed > SHAKE_THRESHOLD) {
                if (speed>maxSpeed)maxSpeed=speed;
                avrageSpeed+=speed;
                shakeCounter++;
                Log.v("lastUpdate","lastupdate="+lastUpdate);
                shake=true;
            }else {
                if (shake){
                    if (shakeCounter>2) {
                       shakeIt(curTime);
                        maxSpeed=0;
                        shakeCounter=0;
                        lastUpdate = curTime;
                        avrageSpeed=0;
                    }
                    shake=false;
                }
            }
            last_x = x;
            last_y = y;
            last_z = z;
            lastTime=curTime;
        }
    }

    private void shakeIt(long curTime) {
        avrageSpeed=avrageSpeed/shakeCounter;
        char charAppend;
        double vibrate;
        Log.v("getAccelerometer", "shakeCounter is:" + shakeCounter + " and maxSpeed is:"
                + maxSpeed +" avarageSpeed="+avrageSpeed+ "\n##############################################################");
        if (curTime-lastUpdate<SLEEP_CHAR){
            if (maxSpeed> SPEED_LEVEL){
                charAppend='-';vibrate=VibrateDriver.LONG_VIB_LENTH;
            }else{
                charAppend='.';vibrate=VibrateDriver.SHORT_VIB_LENTH;
            }
        }else if(maxSpeed > SPEED_LEVEL){
            flushChar();
            charAppend='-';vibrate=VibrateDriver.LONG_VIB_LENTH;
        }else{
            flushChar();
            charAppend='.';vibrate=VibrateDriver.SHORT_VIB_LENTH;
        }
        vibrateDriver.vibrate(vibrate);
        charBuilder.append(charAppend);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public boolean isRecord() {
        return record;
    }

    public void setRecord(boolean record) {
        this.record = record;
    }

    public StringBuilder getWordBuilder() {
        return wordBuilder;
    }

    public void setWordBuilder(StringBuilder wordBuilder) {
        this.wordBuilder = wordBuilder;
    }

    public void flushChar() {
        Character ch=MorseDic.morseToLetters.get(charBuilder.toString());
        if (ch!=null) {
            wordBuilder.append(ch);
            Log.d("flushChar", "Add " + ch + " to wordBuilder");
        }
        charBuilder=new StringBuilder();

    }
}
