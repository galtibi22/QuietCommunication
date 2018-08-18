package com.afeka.agile.quietcommunication;

import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;

public class VibrateDriver {


    private static final int  halfShortVibLength = 150;
    private static final int  shortVibLength = 300;
    private static final int  longVibLength = 600;

    //  . > short, - > long
    public void vibrate(char c){
        int length;
        if(c== '.' || c=='0'){
            length=shortVibLength;
        }else if(c=='-' || c=='1'){
            length=longVibLength;
        }else if(c=='2'){
            length=halfShortVibLength;
        }else return;

        Vibrator vib = (Vibrator) MainActivity.instance.getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vib.vibrate(VibrationEffect.createOneShot(length, VibrationEffect.DEFAULT_AMPLITUDE));
        }else{
            //deprecated in API 26
            vib.vibrate(length);
        }

    }


}
