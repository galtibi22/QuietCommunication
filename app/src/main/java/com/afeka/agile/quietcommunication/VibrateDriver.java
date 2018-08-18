package com.afeka.agile.quietcommunication;

import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;

public class VibrateDriver {

    private  final long waitTime=300;
    private  final long waitBetweenLetters=600;
    private  final long waitBetweenWords=1200;
    private final long FAT_VIB_LENTH=100;
    private final int SLEEP_FAST_VIB=150;
    public static final int SHORT_VIB_LENTH = 200;
    public static final int LONG_VIB_LENTH = 600;


    //  . > short, - > long
    public void vibrate(double length){
        int len=(int)(length*100);
        if (length>10)
            len=(int)length;
        Vibrator vib = (Vibrator) MainActivity.getInstance().getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vib.vibrate(VibrationEffect.createOneShot(len, VibrationEffect.DEFAULT_AMPLITUDE));
        }else{
            //deprecated in API 26
            vib.vibrate(len);
        }
    }

    public void vibrate(char ch){
        if (ch=='-')
            vibrate(LONG_VIB_LENTH );
        else if (ch=='.')
            vibrate(SHORT_VIB_LENTH);
    }


    public void retunFastVibrate(int numOfVibration) {
        for (int i=0;i<numOfVibration;i++){
            vibrate(FAT_VIB_LENTH);
            try {Thread.sleep(SLEEP_FAST_VIB); } catch (InterruptedException e) {}
        }

    }

    public void TextToVib(String text){
        String letterMorse;
        long expectedTime;
        text=text.toLowerCase().replaceAll("[-+.^:,]","");
        for(int j = 0; j < text.length(); j++)
        {
            if(text.charAt(j)!=' '){
                letterMorse= MorseDic.letterToMorse.get(text.charAt(j));
                for(int i = 0; i < letterMorse.length(); i++)
                {
                    vibrate(letterMorse.charAt(i));

                    expectedTime = System.currentTimeMillis();
                    expectedTime += waitTime;
                    if(letterMorse.charAt(i)=='0' || letterMorse.charAt(i)=='.')
                        expectedTime += SHORT_VIB_LENTH;
                    else if(letterMorse.charAt(i)=='1' || letterMorse.charAt(i)=='-')
                        expectedTime += LONG_VIB_LENTH;
                    while(System.currentTimeMillis() < expectedTime){
                        //Empty Loop
                    }
                }
            }

            expectedTime = System.currentTimeMillis();
            expectedTime += waitBetweenLetters+ LONG_VIB_LENTH;
            if(text.charAt(j)==' ')
                expectedTime +=waitBetweenWords;
            while(System.currentTimeMillis() < expectedTime){
                //Empty Loop
            }
        }
    }
}
