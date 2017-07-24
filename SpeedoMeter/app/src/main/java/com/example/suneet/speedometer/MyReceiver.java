package com.example.suneet.speedometer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.android.internal.telephony.ITelephony;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MyReceiver extends BroadcastReceiver {

    TelephonyManager telephonyManager;

    com.android.internal.telephony.ITelephony iTelephony;
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
       /* throw new UnsupportedOperationException("Not yet implemented");*/
        Log.e("CALL", "onReceive: " );
        telephonyManager= (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        try {
            Class c=Class.forName(telephonyManager.getClass().getName());
            Method m=c.getDeclaredMethod("getITelephony");
            m.setAccessible(true);
            iTelephony= (ITelephony) m.invoke(telephonyManager);
            iTelephony.endCall();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }
}
