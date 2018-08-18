
package com.afeka.agile.quietcommunication;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;

public class AccelerometerEventListener implements SensorEventListener{
    private final float SHAKE_THRESHOLD = 200;
    private final int SLEEP_CHAR=2500; //time for wait until start record new char
    private final int SPEED_LEVEL =1200; //maxSpeed< is . else -

    private int shakeCounter;
    private long lastUpdate,lastTime;
    private float last_x,last_y,last_z;
    private boolean shake=false;
    private float  maxSpeed;
    private StringBuilder charBuilder=new StringBuilder();
    public static StringBuilder wordBuilder=new StringBuilder();
    private VibrateDriver vibrateDriver=new VibrateDriver();
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

        if ((curTime - lastTime) > 80 && MainActivity.record) {
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

        Log.v("getAccelerometer", "shakeCounter is:" + shakeCounter + " and maxSpeed is:"
                + maxSpeed +" avarageSpeed="+avrageSpeed+ "\n##############################################################");
        if (curTime-lastUpdate<SLEEP_CHAR && maxSpeed> SPEED_LEVEL){
            Log.d("translate","line");
            charBuilder.append('-');
            vibrateDriver.vibrate('1');
        }else if(curTime-lastUpdate<SLEEP_CHAR && maxSpeed< SPEED_LEVEL) {
            Log.d("translate","dout");
            charBuilder.append('.');
            vibrateDriver.vibrate('2');

        }else if(maxSpeed< SPEED_LEVEL){
            newChar('.');
            vibrateDriver.vibrate('2');
            Log.d("translate","new Char, dout");
        }else{
            newChar('-');
            Log.d("translate","new Char, line");
            vibrateDriver.vibrate('1');
        }
    }

    private void newChar(char i) {
        Character ch=MorseDic.morseToLetters.get(charBuilder.toString());
        if (ch!=null)
            wordBuilder.append(ch);
        charBuilder=new StringBuilder(""+i);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

}
