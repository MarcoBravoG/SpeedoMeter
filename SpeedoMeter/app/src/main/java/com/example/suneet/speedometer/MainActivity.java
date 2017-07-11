package com.example.suneet.speedometer;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.github.anastr.speedviewlib.base.Gauge;

import java.util.Locale;

public class MainActivity extends Activity {
    TextView appName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AssetManager am=this.getAssets();
        appName= (TextView) findViewById(R.id.appName);
        Typeface typeface=Typeface.createFromAsset(am,String.format(Locale.US,"fonts/%s","PoiretOne-Regular.ttf"));
        appName.setTypeface(typeface);
        Gauge gauge= (Gauge) findViewById(R.id.speedometer);
        gauge.setTextTypeface(typeface);
        gauge.setSpeedTextTypeface(typeface);
        gauge.setTextSize(24);
        gauge.setSpeedTextSize(32);
        gauge.setUnit("KM/HR");
        



    }
}
